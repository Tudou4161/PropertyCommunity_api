package io.propertyService.Service.Repository.Interface;

import io.propertyService.Service.Domain.Dto.PostSearchCondition;
import io.propertyService.Service.Domain.IsEntity.Post;

import java.util.List;

public interface PostRepository {
    /**
     * @Author: 지영석
     * @Description: post repository 인터페이스
     * @Methods: save -> 엔티티 저장,
     *           delete -> 엔티티 삭제,
     *           findById -> pk기반 게시글 탐색,
     *           findByUsername -> 유저이름 기반 게시글 탐색,
     *           findAll -> 모든 게시글 탐색,
     *           findAllAndSearchByBuilder -> 검색 기반 게시글 필터링. findAllV2라고 생각하면 될듯.
     */

    void save(Post post);

    void delete(Long id);

    Post findById(Long id);

    List<Post> findByUsername(String username);

    List<Post> findAll();

    List<Post> findAllAndSearchByBuilder(PostSearchCondition condition);

}
