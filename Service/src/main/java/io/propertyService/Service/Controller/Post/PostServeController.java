package io.propertyService.Service.Controller.Post;

import io.propertyService.Service.Domain.IsEntity.Account;
import io.propertyService.Service.Repository.Interface.LikeRepository;
import io.propertyService.Service.Service.Interface.PostService;
import io.propertyService.Service.Service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PostServeController {
    /**
     * @Author: 지영석
     * @Method: getUserLike, setLike, deleteLike
     */
    private final PostService postService;
    private final UserService userService;
    private final LikeRepository likeRepository;


    @GetMapping("/api/v1/like/get/{postId}/{email}")
    public JSONObject getUserLike(@PathVariable Long postId,
                                  @PathVariable String email) {

        JSONObject jsonObject = new JSONObject();
        Account user = userService.findOneUserByUserEmail(email);

        if (likeRepository.findByUserIdAndPostId(user.getId(), postId) != null) {
            jsonObject.put("Message", "already clicked");
            return jsonObject;
        } else {
            jsonObject.put("Message", "non clicked");
            return jsonObject;
        }
    }


    @ResponseBody
    @GetMapping("/api/v1/like/create/{postId}/{email}")
    public JSONObject setUpLike(@PathVariable Long postId,
                                @PathVariable String email) {

        Account user = userService.findOneUserByUserEmail(email);
        postService.createLike(user.getId(), postId);

        JSONObject jsonObject = new JSONObject();
        try {
            Long likeCnt = postService.likeCount(postId);
            jsonObject.put("like_cnt", likeCnt);
            jsonObject.put("Message", "set like complete!");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            String eMessage = e.getMessage();
            jsonObject.put("Message", eMessage);
        }

        return jsonObject;
    }


    @ResponseBody
    @GetMapping("/api/v1/like/delete/{postId}/{email}")
    public JSONObject deleteLike(@PathVariable Long postId,
                                 @PathVariable String email) {

        Account user = userService.findOneUserByUserEmail(email);
        postService.deleteLike(user.getId(), postId);

        JSONObject jsonObject = new JSONObject();
        try {
            Long likeCnt = postService.likeCount(postId);
            jsonObject.put("like_cnt", likeCnt);
            jsonObject.put("Message", "delete like complete!");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            String eMessage = e.getMessage();
            jsonObject.put("Message", eMessage);
        }

        return jsonObject;
    }
}
