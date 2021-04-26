package io.propertyService.Service.Service.Interface;

import io.propertyService.Service.Domain.Dto.PostGeoInfoDto;
import io.propertyService.Service.Domain.IsEntity.PropertyInfo;
import org.json.simple.JSONObject;

import java.util.List;

public interface PropertyInfoService {

    void saveInfo(PropertyInfo propertyInfo);

    PostGeoInfoDto findInfoOneById(Long infoId);

    PostGeoInfoDto findInfoByPostId(Long postId);

    JSONObject findInfos();

    JSONObject findOneMarkerAggrCategory(Long postId);

}
