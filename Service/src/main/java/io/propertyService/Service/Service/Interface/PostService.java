package io.propertyService.Service.Service.Interface;

import io.propertyService.Service.Domain.Dto.PostGeoInfoDto;
import io.propertyService.Service.Domain.Dto.PostSearchCondition;
import io.propertyService.Service.Domain.IsEntity.Post;

import java.util.List;

public interface PostService {

    void savePost(Post post);

    PostGeoInfoDto findPost(Long postId);

    void deletePost(Long postId);

    void setPostFindCnt(Long postId);

    void createLike(Long userId, Long postId);

    void deleteLike(Long userId, Long postId);

    Long likeCount(Long postId);

    void createPropertyInfo(Long postId, double lat, double lon);

    void updatePropertyInfo(Long postId, double lat, double lon);

    List<PostGeoInfoDto> findPostsBySearchCondition(PostSearchCondition condition);
}
