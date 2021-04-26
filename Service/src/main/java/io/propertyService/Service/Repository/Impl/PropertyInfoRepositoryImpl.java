package io.propertyService.Service.Repository.Impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.propertyService.Service.Domain.Dto.PostGeoInfoDto;
import io.propertyService.Service.Domain.Dto.QPostGeoInfoDto;
import io.propertyService.Service.Domain.IsEntity.PropertyInfo;
import io.propertyService.Service.Repository.Interface.PropertyInfoRepository;
import org.springframework.stereotype.Repository;
import static io.propertyService.Service.Domain.IsEntity.QPost.*;
import static io.propertyService.Service.Domain.IsEntity.QPropertyInfo.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class PropertyInfoRepositoryImpl implements PropertyInfoRepository {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public PropertyInfoRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void save(PropertyInfo propertyInfo) {
        if (propertyInfo.getId() == null) {
            em.persist(propertyInfo);
        } else {
            em.merge(propertyInfo);
        }
    }

    @Override
    public PropertyInfo findById(Long id) {
        return em.find(PropertyInfo.class, id);
    }

    @Override
    public PropertyInfo findByPostId(Long postId) {
        return queryFactory
                .select(propertyInfo)
                .from(propertyInfo)
                .where(propertyInfo.post.id.eq(postId))
                .fetchOne();
    }

    @Override
    public List<Tuple> findAll() {
        return queryFactory
                .select(
                        post.address.roadAddr,
                        post.address.subAddress,
                        post.id,
                        propertyInfo.latitude,
                        propertyInfo.longitude)
                .from(propertyInfo)
                .leftJoin(post).on(propertyInfo.post.id.eq(post.id))
                .groupBy(propertyInfo.latitude, propertyInfo.longitude)
                .fetch();
    }

    @Override
    public List<Tuple> findOneMarkerByAggrCategory(Long postId) {
        return queryFactory
                .select(post.category,
                        post.category.count())
                .from(propertyInfo)
                .leftJoin(post).on(propertyInfo.post.id.eq(post.id))
                .where(propertyInfo.latitude.in(
                        JPAExpressions
                                .select(propertyInfo.latitude)
                                .from(propertyInfo)
                                .where(propertyInfo.post.id.eq(postId))
                )
                        .and(propertyInfo.longitude.in(
                                JPAExpressions
                                        .select(propertyInfo.longitude)
                                        .from(propertyInfo)
                                        .where(propertyInfo.post.id.eq(postId))
                        )))
                .groupBy(post.category)
                .fetch();
    }

    @Override
    public List<Double> findOneMarkerByAggrGrade(Long postId) {
        return queryFactory
                .select(post.grade.avg())
                .from(propertyInfo)
                .leftJoin(post).on(propertyInfo.post.id.eq(post.id))
                .where(propertyInfo.latitude.in(
                        JPAExpressions
                                .select(propertyInfo.latitude)
                                .from(propertyInfo)
                                .where(propertyInfo.post.id.eq(postId))
                )
                        .and(propertyInfo.longitude.in(
                                JPAExpressions
                                        .select(propertyInfo.longitude)
                                        .from(propertyInfo)
                                        .where(propertyInfo.post.id.eq(postId))
                        )))
                .fetch();
    }
}
