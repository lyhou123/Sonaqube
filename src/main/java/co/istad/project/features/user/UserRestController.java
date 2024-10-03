package co.istad.project.features.user;

import co.istad.project.features.user.dto.UpdateUserDto;
import co.istad.project.respone.BaseRestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceImpl userService;


    @GetMapping("/find")
    public BaseRestResponse<Object> findUserByUuid(@RequestParam String userUuid) {

        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .data(userService.getUserById(userUuid))
                .message("User has been found successfully.")
                .build();
    }



    @GetMapping("")
    public BaseRestResponse<Object> getAllUsers() {

        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .data(userService.getAllUsers())
                .message("Users found successfully.")
                .build();
    }



    @PutMapping("/{userUuid}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPER_ADMIN')")
    public BaseRestResponse<Object> updateUserByUuid(@PathVariable String userUuid, @RequestBody UpdateUserDto updateUserDto) {

        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .data(userService.updateUser(userUuid, updateUserDto))
                .message("Users has been updated successfully.")
                .build();
    }

    @DeleteMapping("/{userUuid}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPER_ADMIN')")
    public BaseRestResponse<Object> deleteUserByUuid(@PathVariable String userUuid) {

        userService.deleteUser(userUuid);

        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("User has been deleted successfully.")
                .build();

    }
}
