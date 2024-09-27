package co.istad.project.features.auth;


import co.istad.project.features.auth.dto.AuthRequest;
import co.istad.project.features.auth.dto.RefresTokenRequest;
import co.istad.project.features.user.UserService;
import co.istad.project.features.user.dto.UserRegisterDto;
import co.istad.project.respone.BaseRestResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@SecurityRequirements(value = {})
public class AuthRestController {

    private final AuthServiceImpl authService;

    private final UserService userService;

    @PostMapping("/login")
    public BaseRestResponse<Object> login(@RequestBody AuthRequest authRequest)
    {
       return BaseRestResponse
               .builder()
               .status(HttpStatus.OK.value())
               .data(authService.login(authRequest))
               .message("Login successfully")
               .build();
    }

   @PostMapping("/register")
    public BaseRestResponse<Object> signup(@RequestBody UserRegisterDto userRegisterDto)
    {
        return BaseRestResponse
                .builder()
                .status(HttpStatus.OK.value())
                .data(authService.createUser(userRegisterDto))
                .message("User created successfully")
                .build();
    }

    @PostMapping("/refresh")
    public BaseRestResponse<Object> refreshToken(@RequestBody RefresTokenRequest refresTokenRequest)
    {
      return BaseRestResponse
              .builder()
              .status(HttpStatus.OK.value())
              .data(authService.refreshToken(refresTokenRequest))
              .message("Token refreshed successfully")
              .build();
    }

}
