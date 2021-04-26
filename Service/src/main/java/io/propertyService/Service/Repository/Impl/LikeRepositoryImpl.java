package io.propertyService.Service.Repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.propertyService.Service.Domain.IsEntity.Like;
import io.propertyService.Service.Domain.IsEntity.Post;
import io.propertyService.Service.Repository.Interface.LikeRepository;
import org.springframework.stereotype.Repository;
import static io.propertyService.Service.Domain.IsEntity.QLike.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public LikeRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void save(Like like) {
        em.persist(like);
    }

    @Override
    public void delete(Like like) {
        em.remove(like);
    }

    @Override
    public Like findById(Long id) {
        return em.find(Like.class, id);
    }

    @Override
    public Like findByUserIdAndPostId(Long userId, Long postId) {
        return queryFactory
                .selectFrom(like)
                .where(like.account.id.eq(userId).and(
                        like.post.id.eq(postId)
                ))
                .fetchOne();
    }

    @Override
    public List<Like> findAll() {
        return queryFactory
                .selectFrom(like)
                .fetch();
    }

    @Override
    public Long getCountLike(Post findPost) {
        return queryFactory
                .select(like)
                .from(like)
                .where(like.post.id.eq(findPost.getId()))
                .fetchCount();
    }
}
