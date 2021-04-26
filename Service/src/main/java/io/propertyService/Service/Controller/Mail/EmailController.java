package io.propertyService.Service.Controller.Mail;

import io.propertyService.Service.Service.Interface.UserService;
import io.propertyService.Service.emailTool.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailController {
    /**
     * @Method: sendAuthCode
     */
    private final Sender sender;
    private final UserService userService;

    @GetMapping("/api/v1/email/send/authCode/{emailAddr}")
    public ResponseEntity sendAuthCode(@PathVariable String emailAddr) {

        boolean result = true;

        //스크립트에서 보낸 메일을 받을 사용자 이메일 주소
        List<String> to = new ArrayList<>();
        to.add(emailAddr);


        String title = emailAddr + " 님의 회원가입 인증번호입니다.";
        String content = userService.getAuthCode();

        log.info(title + ":" + content + ":" + to);
        sender.send(title, content, to);

        JSONObject obj = new JSONObject();
        obj.put("result", result);
        obj.put("email", emailAddr);
        obj.put("content", content);

        return ResponseEntity.ok(obj);
    }

}
