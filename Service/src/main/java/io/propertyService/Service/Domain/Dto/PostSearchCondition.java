package io.propertyService.Service.Domain.Dto;

import io.propertyService.Service.Domain.NotEntity.Category;
import io.propertyService.Service.Domain.NotEntity.TransactionType;
import lombok.Data;

@Data
public class PostSearchCondition {

    private String firstAddr;
    private String secondAddr;
    private String subAddress;
    private String category;
    private String transactionType;

}
