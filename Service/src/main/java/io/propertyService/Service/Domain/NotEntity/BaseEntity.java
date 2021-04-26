package io.propertyService.Service.Domain.NotEntity;

import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseEntity {

    /**
     * @Author: 지영석
     * @description: 게시글과 유저정보의 생성시간과 수정시간을 다루기 위한 super class.
     * @Fields: createDate -> 생성시간, updateDate -> 수정시간
     * @Methods: prePersist() -> 최초의 생성 및 수정시간 처리, preUpdate() -> 추후 수정시간 처리.
     */

    @Column(updatable = false)
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updateDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}
