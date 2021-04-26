package io.propertyService.Service.Repository.Interface;

import com.querydsl.core.Tuple;
import io.propertyService.Service.Domain.Dto.PostGeoInfoDto;
import io.propertyService.Service.Domain.IsEntity.PropertyInfo;

import java.util.List;

public interface PropertyInfoRepository {

    /**
     * @Author: 지영석
     * @Methods: save, findById, findByPostId, findAll,
     *           findOneMarkerByAggrCategory, findOneMarkerByAggrGrade
     */

    void save(PropertyInfo propertyInfo);

    PropertyInfo findById(Long id);

    PropertyInfo findByPostId(Long postId);

    List<Tuple> findAll();

    List<Tuple> findOneMarkerByAggrCategory(Long postId);

    List<Double> findOneMarkerByAggrGrade(Long postId);

}
