package co.istad.project.features.user;


import co.istad.project.features.user.dto.ResponseUserDto;
import co.istad.project.features.user.dto.UpdateUserDto;

import java.util.List;

public interface UserService {

    /**
     * Get user by id
     * @return ResponseUserDto
     * @param uuid
     * author: lyhou
     */
    ResponseUserDto getUserById(String uuid);

    /**
     * Update user
     * @return ResponseUserDto
     * @param uuid, updateUserDto
     * author: lyhou
     */
    ResponseUserDto updateUser(String uuid,UpdateUserDto updateUserDto);

    /**
     * Delete user
     * @param uuid
     * author: lyhou
     */
    void deleteUser(String uuid);

    /**
     * Get all users
     * @return List<ResponseUserDto>
     * author: lyhou
     *
     */
    List<ResponseUserDto> getAllUsers();

}
