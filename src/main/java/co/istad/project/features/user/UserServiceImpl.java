package co.istad.project.features.user;

import co.istad.project.domain.User;
import co.istad.project.features.user.dto.ResponseUserDto;
import co.istad.project.features.user.dto.UpdateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public ResponseUserDto getUserById(String uuid) {

        User findUser = userRepository.findUserByUuid(uuid);

        if(findUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with uuid: "+uuid);
        }

        return userMapper.mapFromUserToUserResponseDto(findUser);
    }

    @Override
    public ResponseUserDto updateUser(String uuid, UpdateUserDto updateUserDto) {

        User findUser = userRepository.findUserByUuid(uuid);

        if(findUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with uuid: "+uuid);
        }

        var newUsers = userMapper.mapFromUpdateUserDtoToUser(updateUserDto);

        return userMapper.mapFromUserToUserResponseDto(userRepository.save(newUsers));
    }

    @Override
    public void deleteUser(String uuid) {

        User findUser = userRepository.findUserByUuid(uuid);

        if(findUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with uuid: "+uuid);
        }

        userRepository.delete(findUser);
    }

    @Override
    public List<ResponseUserDto> getAllUsers() {

        List<User> userList = userRepository.findAll();

        if(userList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User is empty");
        }

        return userMapper.mapFromListOfUserToListOfUserDto(userList);
    }
}
