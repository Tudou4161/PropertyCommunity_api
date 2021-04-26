package io.propertyService.Service.Repository.Interface;

import io.propertyService.Service.Domain.IsEntity.Like;
import io.propertyService.Service.Domain.IsEntity.Post;

import java.util.List;

public interface LikeRepository {

    /**
     * @Author: 지영석
     * @Methods: save, findById, delete, findByUserIdAndPostId,
     *           findAll, getCountLike
     */

    void save(Like like);

    void delete(Like like);

    Like findById(Long id);

    Like findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findAll();

    Long getCountLike(Post post);

}
