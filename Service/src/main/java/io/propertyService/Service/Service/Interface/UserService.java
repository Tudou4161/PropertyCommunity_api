package io.propertyService.Service.Service.Interface;

import io.propertyService.Service.Domain.Dto.UserDto;
import io.propertyService.Service.Domain.IsEntity.Account;

import java.util.List;

public interface UserService {

    void join(UserDto userDto);

    void deleteUser(Long userId);

    Account findOneUserById(Long userId);

    Account findOneUserByUserEmail(String username);

    List<UserDto> findUsers();

    boolean isExistUser(String email);

    String getAuthCode();
}
