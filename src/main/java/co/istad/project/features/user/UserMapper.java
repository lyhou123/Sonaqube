package co.istad.project.features.user;

import co.istad.project.domain.User;
import co.istad.project.features.user.dto.ResponseUserDto;
import co.istad.project.features.user.dto.UpdateUserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResponseUserDto mapFromUserToUserResponseDto(User user);
    List<ResponseUserDto> mapFromListOfUserToListOfUserDto(List<User> userList);

    User mapFromUpdateUserDtoToUser(UpdateUserDto userDto);

}
