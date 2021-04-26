package io.propertyService.Service.Repository.Impl;

import static io.propertyService.Service.Domain.IsEntity.QAccount.*;
import static io.propertyService.Service.Domain.IsEntity.QPost.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.propertyService.Service.Domain.IsEntity.Account;
import io.propertyService.Service.Domain.IsEntity.Post;
import io.propertyService.Service.Domain.IsEntity.QAccount;
import io.propertyService.Service.Repository.Interface.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    /**
     * @Author: 지영석
     * @description: userRepository 구현체
     */

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void save(Account account) {
        if (account.getId() == null) {
            em.persist(account);
        } else {
            em.merge(account);
        }
    }

    @Override
    public void delete(Long id) {
        Account findAccount = em.find(Account.class, id);
        String username = findAccount.getUsername();
        em.remove(findAccount);

        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(post.author.eq(username))
                .fetch();

        for (Post userPost : posts) {
            if (userPost.getAuthor() != null) {
                em.remove(userPost);
            }
            System.out.println("--삭제완료--");
        }
    }

    @Override
    public Account findById(Long id) {
        return em.find(Account.class, id);
    }

    @Override
    public Account findByUserEmail(String email) {
        return queryFactory
                .selectFrom(account)
                .where(account.email.eq(email))
                .fetchOne();
    }

    @Override
    public List<Account> findByUserEmail2(String email) {
        return queryFactory
                .selectFrom(account)
                .where(account.email.eq(email))
                .fetch();
    }

    @Override
    public List<Account> findAll() {
        return queryFactory
                .selectFrom(account)
                .fetch();
    }
}
