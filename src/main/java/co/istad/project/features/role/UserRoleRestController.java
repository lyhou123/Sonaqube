package co.istad.project.features.role;

import co.istad.project.domain.role.EnumRole;
import co.istad.project.domain.role.Role;
import co.istad.project.respone.BaseRestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class UserRoleRestController {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;



    @PostMapping("/create")
    public BaseRestResponse<Object> createNewUserRole(@RequestParam String roleName){
        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .data(
                        roleMapper.mapFromRoleToRoleResponseDto(
                                roleRepository.save(Role.builder()
                                        .uuid(UUID.randomUUID().toString())
                                        .roleName(EnumRole.valueOf("ROLE_"+roleName.toUpperCase()))
                                        .build())
                        )
                )
                .message("User has been registered successfully")
                .build();
    }



    @GetMapping("")
    public BaseRestResponse<Object> getAllUserRoles(){
        return BaseRestResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .data(
                       roleMapper.mapFromListOfRoleToListOfRoleResponseDto(
                               roleRepository.findAll()
                       )
                )
                .message("User has been registered successfully")
                .build();
    }
}
