package io.propertyService.Service.Domain.NotEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Embeddable;


@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /**
     * @Author: 지영석
     * @description: 주소값을 나타내는 Embeddable Type
     * @fields: roadAddr(도로명 주소 full name) , firstAddr(특별시, 광역시, 도), secondAddr(시, 군, 구),
     *          thirdAddr(나머지 도로명 + 번지), subAddress(건물 이름)
     */

    private String roadAddr;
    private String firstAddr;
    private String secondAddr;
    private String thirdAddr;
    private String subAddress;
}
