package co.istad.project.security;

import co.istad.project.domain.User;
import co.istad.project.repo.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final UserRepository userRepository;


    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {

        User user = userRepository.findUsersByEmail(source.getSubject()).orElseThrow(()-> new BadCredentialsException("Invalid Token"));

        CustomUserDetails userDetail = new CustomUserDetails();

        userDetail.setUser(user);

        System.out.println("User Authorities are" + userDetail.getAuthorities());
        userDetail.getAuthorities().forEach(
                authority -> {
                    System.out.println("Here is the authority get from the jwt"+authority.getAuthority());
                }
        );

        return new UsernamePasswordAuthenticationToken(userDetail,"",userDetail.getAuthorities());
    }
}
