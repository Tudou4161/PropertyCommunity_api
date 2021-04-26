package io.propertyService.Service.Controller.Map;

import io.propertyService.Service.Service.Interface.PropertyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MapDataController {
    /**
     * @Author: 지영석
     * @Method: getGeoInfos, getMarkerInfo, getMarkerDetailInfo
     */

    private final PropertyInfoService propertyInfoService;

    @GetMapping("/api/v1/maps/map")
    public ResponseEntity getGeoInfos() {
        return ResponseEntity.ok(
                propertyInfoService.findInfos()
        );
    }

    @GetMapping("/api/v1/maps/map/marker/{postId}/details")
    public ResponseEntity getMarkerDetailInfo(@PathVariable Long postId) {
        return ResponseEntity.ok(
                propertyInfoService.findOneMarkerAggrCategory(postId)
        );
    }
}
