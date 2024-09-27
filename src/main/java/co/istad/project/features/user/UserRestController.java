//package co.istad.project.features.user;
//
//import co.istad.project.features.user.dto.UpdateUserDto;
//import co.istad.project.respone.BaseRestResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//
//@RestController
//@RequestMapping("/api/v1/users")
//@RequiredArgsConstructor
//public class UserRestController {
//
//    private final UserService userService;
//
//
//    @GetMapping("/find")
//    public BaseRestResponse<Object> findUserByUuid(@RequestParam String userUuid) {
//        return BaseRestResponse
//                .builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.OK.value())
//                .data(userService.getUserByUuid(userUuid))
//                .message("User has been found successfully.")
//                .build();
//    }
//
//
//
//    @GetMapping("")
//    public BaseRestResponse<Object> getAllUsers() {
//        return BaseRestResponse
//                .builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.OK.value())
//                .data(userService.getAllUsers())
//                .message("Users found successfully.")
//                .build();
//    }
//
//
//
//    @PutMapping("/{userUuid}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPER_ADMIN')")
//    public BaseRestResponse<Object> updateUserByUuid(@PathVariable String userUuid, @RequestBody UpdateUserDto updateUserDto) throws Exception {
//        return BaseRestResponse
//                .builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.OK.value())
//                .data(userService.updateUserByUuid(userUuid, updateUserDto))
//                .message("Users has been updated successfully.")
//                .build();
//    }
//}
