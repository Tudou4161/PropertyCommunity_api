package io.propertyService.Service.Controller.UserAdmin;

import io.propertyService.Service.Domain.Dto.UserDto;
import io.propertyService.Service.Domain.IsEntity.Account;
import io.propertyService.Service.Service.Interface.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@RequiredArgsConstructor
public class UserController {
    /**
     * @회원가입 처리 및 회원수정 컨트롤러
     * @Method: joinUser(회원가입 -> @param : UserDto),
     *          getUserInfo(유저정보 조회 -> @param : user email),
     *          updatePassword(유저 비밀번호 변경처리하기 -> @param: updatForm)
     */

    private final UserService userService;

    @PostMapping("/api/v1/user/join")
    public MessageResponse joinUser(@RequestBody @Valid UserDto userDto, BindingResult err) {

        if (err.hasErrors()) {
            return new MessageResponse(err.getFieldError().getDefaultMessage());
        }

        userService.join(userDto);

        return new MessageResponse("success!");
    }


    @GetMapping("/api/v1/user/{email}")
    public UserResponse getUserInfo(@PathVariable String email){

        Account account = userService.findOneUserByUserEmail(email);

        UserResponse response = UserResponse.builder()
                .name(account.getUsername())
                .phoneNum(account.getPhoneNum())
                .password(account.getPassword())
                .message("success!")
                .email(account.getEmail())
                .build();

        return response;
    }

    @PutMapping("/api/v1/user/update/password/{email}")
    public ResponseEntity updatePassword(@PathVariable String email,
                                         @RequestBody @Valid PasswordUpdateForm passwordUpdateForm,
                                         BindingResult err) {

        if (err.hasErrors()) {
            return ResponseEntity.badRequest().body(err.getFieldError().getDefaultMessage());
        }

        boolean flag = userService.isExistUser(email);
        if (flag == false) {
            return ResponseEntity.badRequest().body(new MessageResponse("이메일을 다시 확인해주세요!"));
        }

        Account account = userService.findOneUserByUserEmail(email);

        UserDto userDto = UserDto.builder()
                .id(account.getId())
                .name(account.getUsername())
                .password(passwordUpdateForm.getPassword())
                .email(account.getEmail())
                .phoneNum(account.getPhoneNum())
                .auth(account.getRole())
                .build();

        userService.join(userDto);

        return ResponseEntity.ok(new MessageResponse("success!"));
    }


    @Data
    static class UserResponse {
        private String email;
        private String password;
        private String name;
        private String phoneNum;
        private String message;

        @Builder
        UserResponse(String email, String password, String name,
                     String phoneNum, String message) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.phoneNum = phoneNum;
            this.message = message;
        }
    }

    @Data
    @AllArgsConstructor
    static class MessageResponse {
        private String message;
    }

    @Data
    static class PasswordUpdateForm {
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6-12자 이내로 입력해주세요.")
        private String password;
    }
}
