package co.istad.project.features.auth;

import co.istad.project.domain.User;
import co.istad.project.domain.role.EnumRole;
import co.istad.project.domain.role.Role;
import co.istad.project.features.auth.dto.AuthRequest;
import co.istad.project.features.auth.dto.AuthResponse;
import co.istad.project.features.auth.dto.RefreshTokenRequest;
import co.istad.project.features.role.RoleRepository;
import co.istad.project.features.user.UserMapper;
import co.istad.project.features.user.dto.ResponseUserDto;
import co.istad.project.features.user.dto.UserRegisterDto;
import co.istad.project.features.user.UserRepository;
import co.istad.project.security.TokenGenerator;
import co.istad.project.utils.EmailUtil;
import co.istad.project.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenGenerator tokenGenerator;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final OtpUtil otpUtil;

    private final EmailUtil emailUtil;
    public AuthResponse login(AuthRequest authRequest) {

        User user = userRepository.findUsersByEmail(authRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!user.getIsActive()) {

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not verified");
        }

        Authentication authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
        );
        return tokenGenerator.generateTokens(authentication);
    }



    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refresTokenRequest)
    {
       Authentication authentication = jwtAuthenticationProvider.authenticate(
               new BearerTokenAuthenticationToken(refresTokenRequest.refreshToken())
       );
       return tokenGenerator.generateTokens(authentication);
    }



    @Override
    public ResponseUserDto createUser(UserRegisterDto userRegisterDto){

        String otp = otpUtil.generateOtp();

        try {
            emailUtil.sendOtpEmail(userRegisterDto.email(), otp);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to send otp please try again");
        }

        if(userRepository.existsByEmail(userRegisterDto.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"User with email " + userRegisterDto.email() + " already existed");
        }

        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setName(userRegisterDto.userName());
        user.setEmail(userRegisterDto.email());
        user.setIsDeleted(true);
        user.setIsVerified(false);
        user.setIsDeleted(false);

        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsEnabled(false);


        user.setRegisteredDate(LocalDateTime.now());
//        set role for user
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByRoleName(EnumRole.ROLE_USER).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role is not found.")));
        user.setRoles(roles);
        // set password for users
        user.setIsActive(false);
        user.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());

        userRepository.save(user);

        return userMapper.mapFromUserToUserResponseDto(user);
    }

    @Override
    public String verifyAccount(String email, String otp) {

        User user = userRepository.findUsersByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with this email: " + email));

        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (2 * 60)) {

//            set user as verified
            user.setIsActive(true);

            userRepository.save(user);

            return "OTP verified you can login";
        }

        return "Please regenerate otp and try again";
    }

    @Override
    public String resendOtp(String email) {

        User user = userRepository.findUsersByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with this email: " + email));

        String otp = otpUtil.generateOtp();

        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to send otp please try again");
        }

        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());

        userRepository.save(user);

        return "Email sent... please verify account within 2 minutes";
    }
}
