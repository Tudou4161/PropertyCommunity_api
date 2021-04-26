package io.propertyService.Service.Repository.Impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.propertyService.Service.Domain.Dto.PostSearchCondition;
import io.propertyService.Service.Domain.IsEntity.Post;
import io.propertyService.Service.Repository.Interface.PostRepository;
import org.springframework.stereotype.Repository;
import static io.propertyService.Service.Domain.IsEntity.QPost.*;
import static org.springframework.util.StringUtils.hasText;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    /**
     * @Author: 지영석
     * @description: postRepository 구현체
     */

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void save(Post post) {
        if (post.getId() == null) {
            em.persist(post);
        } else {
            em.merge(post);
        }
    }

    @Override
    public void delete(Long id) {
        Post post = em.find(Post.class, id);
        em.remove(post);
    }

    @Override
    public Post findById(Long id) {
        Post post = em.find(Post.class, id);
        return post;
    }

    @Override
    public List<Post> findByUsername(String username) {
        return queryFactory
                .selectFrom(post)
                .where(post.author.eq(username))
                .fetch();
    }

    @Override
    public List<Post> findAll() {
        return queryFactory
                .select(post)
                .from(post)
                .fetch();
    }

    @Override
    public List<Post> findAllAndSearchByBuilder(PostSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getFirstAddr())) {
            builder.and(post.address.firstAddr.eq(condition.getFirstAddr()));
        }
        if (hasText(condition.getSecondAddr())) {
            builder.and(post.address.secondAddr.contains(condition.getSecondAddr()));
        }
        if (hasText(condition.getSubAddress())) {
            builder.and(post.address.subAddress.contains(condition.getSubAddress()));
        }
        if (condition.getCategory() != null) {
            builder.and(post.category.eq(condition.getCategory()));
        }
        if (condition.getTransactionType() != null) {
            builder.and(post.transactionType.eq(condition.getTransactionType()));
        }

        //검색조건 where절에 넣고, 필터링된 데이터 가져오기!
        return queryFactory
                .select(post)
                .from(post)
                .where(builder)
                .orderBy(post.findCnt.desc())
                .fetch();
    }
}
