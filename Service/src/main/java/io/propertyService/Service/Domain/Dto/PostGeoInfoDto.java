package io.propertyService.Service.Domain.Dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import io.propertyService.Service.Domain.NotEntity.Category;
import io.propertyService.Service.Domain.NotEntity.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PostGeoInfoDto {

    private Long id;

    @NotBlank(message = "필수 입력란입니다.")
    private String title;

    @NotBlank(message = "필수 입력란입니다.")
    private String content;

    private String author;

    private double latitude;
    private double longitude;

    private Long likeCnt;
    private Long findCnt;

    @NotNull(message = "필수 입력란입니다.")
    private Integer grade;

    @NotBlank(message = "필수 입력란입니다.")
    private String roadAddr;

    private String firstAddr;
    private String secondAddr;
    private String thirdAddr;

    @NotBlank(message = "필수 입력란입니다.")
    private String subAddress;

    private String category;

    private Long categoryCnt;

    private Double gradeAvg;

    private String transactionType;

    @QueryProjection
    public PostGeoInfoDto(String category, Long categoryCnt) {
        this.category = category;
        this.categoryCnt = categoryCnt;
    }

    @QueryProjection
    public PostGeoInfoDto(String roadAddr, String subAddress, Long id,
                          double latitude, double longitude) {
        this.id = id;
        this.roadAddr = roadAddr;
        this.subAddress = subAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Builder
    public PostGeoInfoDto(Long id, String author, String title, String content,
                          String roadAddr, Double gradeAvg,
                          String firstAddr, String secondAddr, String thirdAddr, String subAddress,
                          String category, String transactionType,
                          Long categoryCnt,
                          double latitude, double longitude, Integer grade, Long likeCnt,
                          Long findCnt) {

        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.roadAddr = roadAddr;
        this.firstAddr = firstAddr;
        this.secondAddr = secondAddr;
        this.thirdAddr = thirdAddr;
        this.subAddress = subAddress;
        this.category = category;
        this.categoryCnt = categoryCnt;
        this.gradeAvg = gradeAvg;
        this.transactionType = transactionType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.grade = grade;
        this.findCnt = findCnt;
        this.likeCnt = likeCnt;

    }
}
