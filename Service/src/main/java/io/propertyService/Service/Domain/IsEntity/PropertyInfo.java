package io.propertyService.Service.Domain.IsEntity;

import io.propertyService.Service.Domain.NotEntity.BaseEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "property_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyInfo extends BaseEntity {

    /**
     * @Author: 지영석
     * @Fields: pk(propertyinfo_id), post(OneToOne), latitude, longitude
     * @Etc: 빌더를 사용해서 엔티티에 접근할 것! Setter를 어떻게 둘지 고민해볼 것!
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "propertyinfo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private double latitude;
    private double longitude;

    @Builder
    public PropertyInfo(Post post, double latitude, double longitude) {
        this.post = post;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
