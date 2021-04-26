package io.propertyService.Service.Service.Impl;

import io.propertyService.Service.Domain.Dto.UserDto;
import io.propertyService.Service.Domain.IsEntity.Account;
import io.propertyService.Service.Repository.Interface.UserRepository;
import io.propertyService.Service.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void join(UserDto userDto) {
        //비밀번호 암호화 정책 포함시켜야함!

        Account account = Account.builder()
                .id(userDto.getId())
                .username(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getAuth())
                .phoneNum(userDto.getPhoneNum())
                .build();

        userRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.delete(userId);
    }

    @Override
    public Account findOneUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Account findOneUserByUserEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Override
    public List<UserDto> findUsers() {
        List<Account> accounts = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (Account account : accounts) {
            UserDto userDto = UserDto.builder()
                    .name(account.getUsername())
                    .password(account.getPassword())
                    .email(account.getEmail())
                    .auth(account.getRole())
                    .build();

            userDtos.add(userDto);
        }

        return userDtos;
    }

    @Override
    public boolean isExistUser(String email) {
        List<Account> findAccount = userRepository.findByUserEmail2(email);

        if (findAccount.size() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public String getAuthCode() {
        Random random = new Random();  //난수 생성을 위한 랜덤 클래스
        String key="";  //인증번호

        //입력 키를 위한 코드
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }
        int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
        key+=numIndex;

        return key;
    }
}
