package io.propertyService.Service.Service.Impl;

import com.querydsl.core.Tuple;
import io.propertyService.Service.Domain.Dto.PostGeoInfoDto;
import io.propertyService.Service.Domain.IsEntity.PropertyInfo;
import static io.propertyService.Service.Domain.IsEntity.QPost.*;
import static io.propertyService.Service.Domain.IsEntity.QPropertyInfo.*;
import io.propertyService.Service.Repository.Interface.PropertyInfoRepository;
import io.propertyService.Service.Service.Interface.PropertyInfoService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class PropertyInfoServiceImpl implements PropertyInfoService {

    @Autowired
    private PropertyInfoRepository propertyInfoRepository;

    @Override
    @Transactional
    public void saveInfo(PropertyInfo propertyInfo) {
        propertyInfoRepository.save(propertyInfo);
    }

    @Override
    public PostGeoInfoDto findInfoOneById(Long infoId) {
        PropertyInfo propertyInfo = propertyInfoRepository.findById(infoId);

        PostGeoInfoDto postGeoInfoDto = PostGeoInfoDto.builder()
                .latitude(propertyInfo.getLatitude())
                .longitude(propertyInfo.getLongitude())
                .build();

        return postGeoInfoDto;
    }

    @Override
    public PostGeoInfoDto findInfoByPostId(Long postId) {
        PropertyInfo findInfo = propertyInfoRepository.findByPostId(postId);

        PostGeoInfoDto postGeoInfoDto = PostGeoInfoDto.builder()
                .latitude(findInfo.getLatitude())
                .longitude(findInfo.getLongitude())
                .roadAddr(findInfo.getPost().getAddress().getRoadAddr())
                .build();

        return postGeoInfoDto;
    }

    @Override
    public JSONObject findInfos() {
        //List<PostGeoInfoDto> result = new ArrayList<>();
        List<Tuple> Infos = propertyInfoRepository.findAll();
        JSONObject result = collectionToJsonConverter(Infos);

        return result;
        };

    @Override
    public JSONObject findOneMarkerAggrCategory(Long postId) {
        JSONObject result;

        List<Tuple> markerInfos
                = propertyInfoRepository.findOneMarkerByAggrCategory(postId);

        List<Double> markerInfos2
                = propertyInfoRepository.findOneMarkerByAggrGrade(postId);

        result = collectionToJsonConverter2(markerInfos, markerInfos2);

        return result;
    }

    public static JSONObject collectionToJsonConverter2(List<Tuple> aggrCategory, List<Double> aggrGrade) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < aggrCategory.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("category_type", aggrCategory.get(i).get(post.category));
            jsonObject.put("cnt", aggrCategory.get(i).get(post.category.count()));
            jsonArray.add(jsonObject);
        }

        JSONObject data = new JSONObject();
        try {
            data.put("aggr_category", jsonArray);
            data.put("aggr_grade", aggrGrade.get(0));
            data.put("message", "success!");
        } catch (Exception e) {
            e.printStackTrace();
            data.put("error", e.getMessage());
        }
        return data;
    }


    //컨트롤러 내부 로직함수.1 -> 부동산 정보 데이터를 json포맷으로 변환
    public static JSONObject collectionToJsonConverter(List<Tuple> propertyInfoList) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < propertyInfoList.size(); i++) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("lat", propertyInfoList.get(i).get(propertyInfo.latitude));
            jsonObject.put("lng", propertyInfoList.get(i).get(propertyInfo.longitude));
            jsonObject.put("postId", propertyInfoList.get(i).get(post.id));
            jsonObject.put("roadAddr", propertyInfoList.get(i).get(post.address.roadAddr));
            jsonObject.put("subAddr", propertyInfoList.get(i).get(post.address.subAddress));
            jsonArray.add(jsonObject);
        }

        JSONObject data = new JSONObject();
        try {
            data.put("data", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            data.put("error", e.getMessage());
        }
        return data;
    }
}
