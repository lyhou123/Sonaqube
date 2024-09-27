package co.istad.project.security;


import co.istad.project.features.auth.dto.AuthRespone;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenGenerator {
    JwtEncoder jwtRefreshTokenEncoder;
    JwtEncoder  jwtAccessTokenEncoder;
     public TokenGenerator(@Qualifier("jwtRefreshTokenEncoder")JwtEncoder jwtRefreshTokenEncoder, JwtEncoder jwtAccessTokenEncoder) {
         this.jwtAccessTokenEncoder = jwtAccessTokenEncoder;
         this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;

     }

    private String createAccessToken(Authentication authentication)
    {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userDetail.getUsername())
                .issuer("mobile banking cstad")
                .expiresAt(now.plus(3, ChronoUnit.HOURS))
                .id(String.valueOf(userDetail.getUser().getId())) //get user id from token to use in the future
                .issuedAt(now)
                .build();

        return jwtAccessTokenEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    //expressed after 7 days
    private String createRefreshToken(Authentication authentication)
    {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(userDetail.getUsername())
                .issuer("mobile banking cstad")
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .issuedAt(now)
                .build();
        return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }


    public AuthRespone generateTokens(Authentication authentication)
    {
        if(!(authentication.getPrincipal() instanceof UserDetail userDetail))
        {
            throw new BadCredentialsException("Principal is not an instance of UserDetail");
        }

        String refreshToken;

        if(authentication.getCredentials() instanceof Jwt jwt)
        {
            Instant now =Instant.now();
            Instant expireAt = ((Jwt) authentication.getCredentials()).getExpiresAt();
            Duration duration = Duration.between(now,expireAt);
            long dayUntilExpired = duration.toDays();

            if(dayUntilExpired<7)
            {
                refreshToken = createRefreshToken(authentication);
            }
            else
            {
                refreshToken = jwt.getTokenValue();
            }
        }else{
            refreshToken = createRefreshToken(authentication);

        }

     return AuthRespone.
             builder()
             .refreshToken(refreshToken)
             .accessToken(createAccessToken(authentication))
             .userId(String.valueOf(userDetail.getUser().getId()))
             .build();

    }

}
