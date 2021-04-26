package io.propertyService.Service;

import io.propertyService.Service.Domain.Dto.UserDto;
import io.propertyService.Service.Domain.IsEntity.Post;
import io.propertyService.Service.Domain.NotEntity.Address;
import io.propertyService.Service.Domain.NotEntity.Category;
import io.propertyService.Service.Domain.NotEntity.TransactionType;
import io.propertyService.Service.Service.Interface.PostService;
import io.propertyService.Service.Service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;


@Component
@RequiredArgsConstructor
public class initDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initData();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final UserService userService;
        private final PostService postService;

        public void initData() {

            UserDto udt = UserDto.builder()
                    .name("지영석")
                    .password("1111")
                    .phoneNum("01041614096")
                    .email("cef5787@naver.com")
                    .auth("ROLE_USER")
                    .build();

            UserDto user2 = UserDto.builder()
                    .name("지영석")
                    .email("xcvef5787@naver.com")
                    .password("asd123456")
                    .auth("ROLE_USER")
                    .phoneNum("01041614096")
                    .build();

            UserDto user3 = UserDto.builder()
                    .name("지영석")
                    .email("xcvef6687@naver.com")
                    .password("asd123456")
                    .auth("ROLE_USER")
                    .phoneNum("0101221122")
                    .build();

            UserDto user4 = UserDto.builder()
                    .name("지영석")
                    .email("xxcvjxo87@naver.com")
                    .password("asd123456")
                    .auth("ROLE_USER")
                    .phoneNum("11111111111")
                    .build();

            userService.join(udt);
            userService.join(user2);
            userService.join(user3);
            userService.join(user4);

            //게시글 데이터 3개 저장
            Post post = Post.builder()
                    .title("개같네요")
                    .author(udt.getEmail())
                    .content("진짜 여기서 거래하지 마세요")
                    .grade(1)
                    .address(new Address("서울특별시", "서울", "신림동",
                            "11-7", "신림뉴씨엘"))
                    .transactionType("공인중개사")
                    .category("계약조항위반")
                    .findCnt(0l)
                    .build();

            Post post2 = Post.builder()
                    .title("개같네요2")
                    .author(user2.getEmail())
                    .content("진짜 여기서 거래하지 마세요2")
                    .grade(3)
                    .address(new Address("서울특별시", "서울", "도봉구",
                            "11-7", "도봉해성부동산"))
                    .transactionType("공인중개사")
                    .category("전세사기")
                    .findCnt(0l)
                    .build();

            Post post3 = Post.builder()
                    .title("개같네요3")
                    .author("asdasdsad")
                    .content("진짜 여기서 거래하지 마세요3")
                    .grade(2)
                    .address(new Address("원주시", "강원", "원주시",
                            "11-7", "원주영석부동산"))
                    .transactionType("공인중개사")
                    .category("전세사기")
                    .findCnt(0l)
                    .build();

            Post post4 = Post.builder()
                    .title("개같네요3")
                    .author("asd")
                    .content("진짜 여기서 거래하지 마세요3")
                    .grade(4)
                    .address(new Address("원주시", "강원", "원주시",
                            "11-7", "원주영석부동산"))
                    .transactionType("공인중개사")
                    .category("전세사기")
                    .findCnt(0l)
                    .build();

            Post post5 = Post.builder()
                    .title("개같네요3")
                    .author("asd")
                    .content("진짜 여기서 거래하지 마세요3")
                    .grade(4)
                    .address(new Address("원주시", "강원", "원주시",
                            "11-7", "원주영석부동산"))
                    .transactionType("공인중개사")
                    .category("계약조항위반")
                    .findCnt(0l)
                    .build();


            postService.savePost(post);
            postService.savePost(post2);
            postService.savePost(post3);
            postService.savePost(post4);
            postService.savePost(post5);

            postService.createPropertyInfo(post.getId(), 37.3980854357918d, 127.027871939898d);
            postService.createPropertyInfo(post2.getId(), 37.4980854357918d, 127.127871939898d);
            postService.createPropertyInfo(post3.getId(), 37.4980854357918d, 127.227871939898d);
            postService.createPropertyInfo(post4.getId(), 37.4980854357918d, 127.227871939898d);
            postService.createPropertyInfo(post5.getId(), 37.4980854357918d, 127.227871939898d);

                        //한 게시글에 여러 명이 좋아요를 누를 수 있다.
            postService.createLike(userService.findOneUserById(1l).getId(), post.getId());
            postService.createLike(userService.findOneUserById(2l).getId(), post.getId());
            postService.createLike(userService.findOneUserById(3l).getId(), post.getId());

            //한 유저가 여러 게시글에 좋아요를 누를 수 있다.
            postService.createLike(userService.findOneUserById(4l).getId(), post.getId());
            postService.createLike(userService.findOneUserById(4l).getId(), post2.getId());
            postService.createLike(userService.findOneUserById(4l).getId(), post3.getId());
        }
    }
}
