package co.istad.project.security;

import co.istad.project.repo.UserRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Setter
@Getter
@Data
@RequiredArgsConstructor
@Component
public class JwtToUserConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private final UserRepository userRepository;

    @Override
    public JwtAuthenticationToken convert(Jwt source) {
        var User = userRepository.findUsersByEmail(source.getSubject()).orElseThrow(()->

                new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        UserDetail userDetail = new UserDetail();

        userDetail.setUser(User);

        return new JwtAuthenticationToken(source, userDetail.getAuthorities());
    }

//    @Override
//    public UsernamePasswordAuthenticationToken convert(Jwt source) {
//        User user = userRepository.findByEmail(source.getSubject()).orElseThrow(()->
//                new IllegalArgumentException("User not found"));
//
//        UserDetail userDetail = new UserDetail();
//        userDetail.setUser(user);
//
//         userDetail.getAuthorities().forEach(
//                 authority -> System.out.println(authority.getAuthority())
//         );
//
//        return new UsernamePasswordAuthenticationToken(
//                userDetail,
//                "",
//                userDetail.getAuthorities());
//    }
}
