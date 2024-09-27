package co.istad.project.features.auth;


import co.istad.project.domain.User;
import co.istad.project.domain.role.EnumRole;
import co.istad.project.domain.role.Role;
import co.istad.project.features.auth.dto.AuthRequest;
import co.istad.project.features.auth.dto.AuthRespone;
import co.istad.project.features.auth.dto.RefresTokenRequest;
import co.istad.project.features.role.RoleRepository;
import co.istad.project.features.user.UserMapper;
import co.istad.project.features.user.dto.ResponseUserDto;
import co.istad.project.features.user.dto.UserRegisterDto;
import co.istad.project.repo.UserRepository;
import co.istad.project.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final TokenGenerator tokenGenerator;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    public AuthRespone login(AuthRequest authRequest) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
        );
        return tokenGenerator.generateTokens(authentication);
    }

    public AuthRespone refreshToken(RefresTokenRequest refresTokenRequest)
    {
       Authentication authentication = jwtAuthenticationProvider.authenticate(
               new BearerTokenAuthenticationToken(refresTokenRequest.refreshToken())
       );
       return tokenGenerator.generateTokens(authentication);
    }


    public ResponseUserDto createUser(UserRegisterDto userRegisterDto){
        if(userRepository.existsByEmail(userRegisterDto.email())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with email " + userRegisterDto.email() + " already existed");
        }
        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setName(userRegisterDto.userName());
        user.setEmail(userRegisterDto.email());
        user.setIsDeleted(true);
        user.setIsVerified(false);
        user.setRegisteredDate(LocalDateTime.now());
//        set role for user
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByRoleName(EnumRole.ROLE_USER).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role is not found.")));
        user.setRoles(roles);
        // set password for users
        user.setPassword(passwordEncoder.encode(userRegisterDto.password()));

        userRepository.save(user);

        return userMapper.mapFromUserToUserResponseDto(user);
    }
}
