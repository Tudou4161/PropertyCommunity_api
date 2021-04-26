package io.propertyService.Service.Service.Impl;

import io.propertyService.Service.Domain.Dto.PostGeoInfoDto;
import io.propertyService.Service.Domain.Dto.PostSearchCondition;
import io.propertyService.Service.Domain.IsEntity.Account;
import io.propertyService.Service.Domain.IsEntity.Like;
import io.propertyService.Service.Domain.IsEntity.Post;
import io.propertyService.Service.Domain.IsEntity.PropertyInfo;
import io.propertyService.Service.Repository.Interface.LikeRepository;
import io.propertyService.Service.Repository.Interface.PostRepository;
import io.propertyService.Service.Repository.Interface.PropertyInfoRepository;
import io.propertyService.Service.Repository.Interface.UserRepository;
import io.propertyService.Service.Service.Interface.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Access;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyInfoRepository propertyInfoRepository;

    @Override
    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public PostGeoInfoDto findPost(Long postId) {
        Post post = postRepository.findById(postId);

        PostGeoInfoDto postGeoInfoDto = PostGeoInfoDto.builder()
                .id(post.getId())
                .author(post.getAuthor())
                .title(post.getTitle())
                .content(post.getContent())
                .grade(post.getGrade())
                .likeCnt(likeRepository.getCountLike(post))
                .findCnt(post.getFindCnt())
                .category(post.getCategory())
                .roadAddr(post.getAddress().getRoadAddr())
                .subAddress(post.getAddress().getSubAddress())
                .build();

        return postGeoInfoDto;
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        postRepository.delete(postId);
    }

    @Override
    @Transactional
    public void setPostFindCnt(Long postId) {
        Post findPost = postRepository.findById(postId);
        Long findFindCnt = findPost.getFindCnt();

        findPost.setFindCnt(++findFindCnt);

        postRepository.save(findPost);
    }

    @Override
    @Transactional
    public void createLike(Long userId, Long postId) {
        Account findUser = userRepository.findById(userId);
        Post findPost = postRepository.findById(postId);

        Like createLike = Like.builder()
                .account(findUser)
                .post(findPost)
                .build();

        likeRepository.save(createLike);
    }

    @Override
    @Transactional
    public void deleteLike(Long userId, Long postId) {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId);
        likeRepository.delete(like);
    }

    @Override
    public Long likeCount(Long postId) {
        Post post = postRepository.findById(postId);
        return likeRepository.getCountLike(post);
    }

    @Override
    @Transactional
    public void createPropertyInfo(Long postId, double lat, double lon) {
        Post post = postRepository.findById(postId);

        PropertyInfo propertyInfo = PropertyInfo.builder()
                .post(post)
                .latitude(lat)
                .longitude(lon)
                .build();

        propertyInfoRepository.save(propertyInfo);
    }


    @Override
    public void updatePropertyInfo(Long postId, double lat, double lon) {
        PropertyInfo info = propertyInfoRepository.findByPostId(postId);
        info.setLatitude(lat);
        info.setLongitude(lon);

        propertyInfoRepository.save(info);
    }


    @Override
    public List<PostGeoInfoDto> findPostsBySearchCondition(PostSearchCondition condition) {
        List<Post> posts = postRepository.findAllAndSearchByBuilder(condition);
        List<PostGeoInfoDto> postGeoInfoDtos = new ArrayList<>();

        for (Post post : posts) {

            PostGeoInfoDto postGeoInfoDto = PostGeoInfoDto.builder()
                    .id(post.getId())
                    .author(post.getAuthor())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .grade(post.getGrade())
                    .likeCnt(likeRepository.getCountLike(post))
                    .findCnt(post.getFindCnt())
                    .category(post.getCategory())
                    .roadAddr(post.getAddress().getRoadAddr())
                    .subAddress(post.getAddress().getSubAddress())
                    .build();

            postGeoInfoDtos.add(postGeoInfoDto);
        }

        return postGeoInfoDtos;
    }
}
