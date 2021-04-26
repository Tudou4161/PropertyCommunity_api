package io.propertyService.Service.Controller.Post;

import io.propertyService.Service.Domain.Dto.PostGeoInfoDto;
import io.propertyService.Service.Domain.Dto.PostSearchCondition;
import io.propertyService.Service.Domain.IsEntity.Post;
import io.propertyService.Service.Domain.NotEntity.Address;
import io.propertyService.Service.Service.Interface.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    /**
     * @author: 지영석
     * @Method: getPostsAll(검색조건에 맞게 데이터 가져오기), getPostDetail(자세한 게시글 정보 가져오기)
     *          createPost(게시글 생성), updatePost(게시글 수정),
     *          deletePost(게시글 삭제)
     */

    private final PostService postService;


    @GetMapping("/api/v1/posts")
    public ResponseEntity getPostsAll(PostSearchCondition condition) {
        List<PostGeoInfoDto> posts = postService.findPostsBySearchCondition(condition);
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/api/v1/posts/detail/{postId}")
    public ResponseEntity getPostDetail(@PathVariable Long postId) {
        PostGeoInfoDto getPost = postService.findPost(postId);

        return ResponseEntity.ok(getPost);
    }


    @PostMapping("/api/v1/posts/create")
    public ResponseEntity createPost(@RequestBody @Valid PostRequestDto postDto,
                                     BindingResult err) {

        if (err.hasErrors()) {
            return ResponseEntity.badRequest().body(err.getFieldError().getDefaultMessage());
        }

        String[] addressArr = postDto.getRoadAddr().split(" ");

        Post post = Post.builder()
                .author(postDto.getAuthor())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .address(new Address(postDto.getRoadAddr(), addressArr[0], addressArr[1],
                        addressArr[2] + " " + addressArr[3], postDto.getSubAddress()))
                .category(postDto.getCategory())
                .transactionType(postDto.getTransactionType())
                .grade(postDto.getGrade())
                .findCnt(0l)
                .build();

        postService.savePost(post);
        postService.createPropertyInfo(post.getId(),
                postDto.getLatitude(), postDto.getLongitude());

        return ResponseEntity.ok("success!");
    }

    @PutMapping("/api/v1/posts/update/{postId}")
    public ResponseEntity updatePost(@PathVariable Long postId,
                                     @RequestBody @Valid PostRequestDto postDto,
                                     BindingResult err) {

        if (err.hasErrors()) {
            return ResponseEntity.badRequest().body(err.getFieldError().getDefaultMessage());
        }

        String[] addressArr = postDto.getRoadAddr().split(" ");

        Post post = Post.builder()
                .id(postId)
                .author(postDto.getAuthor())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .address(new Address(postDto.getRoadAddr(), addressArr[0], addressArr[1],
                        addressArr[2] + " " + addressArr[3], postDto.getSubAddress()))
                .category(postDto.getCategory())
                .transactionType(postDto.getTransactionType())
                .grade(postDto.getGrade())
                .findCnt(0l)
                .build();

        postService.savePost(post);

        postService.updatePropertyInfo(post.getId(),
                postDto.getLatitude(), postDto.getLongitude());

        return ResponseEntity.ok("update success!");
    }



    @GetMapping("/api/v1/posts/delete/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseEntity.ok("delete success!");
    }


    @Data
    static class PostRequestDto {
        private String author;
        private String title;
        private String content;
        private String roadAddr;
        private String subAddress;
        private double latitude;
        private double longitude;
        private String category;
        private String transactionType;
        private int grade;
    }
}
