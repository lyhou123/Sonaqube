package co.istad.project.features.role;

import co.istad.project.domain.role.Role;
import co.istad.project.features.role.dto.RoleResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface RoleMapper {
    RoleResponseDto mapFromRoleToRoleResponseDto(Role role);
    List<RoleResponseDto> mapFromListOfRoleToListOfRoleResponseDto(List<Role> roleList);
}
