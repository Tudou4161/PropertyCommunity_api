package io.propertyService.Service.Domain.IsEntity;

import io.propertyService.Service.Domain.NotEntity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    /**
     * @Author: 지영석
     * @description: 유저정보 관리
     * @Fields: pk(user_id), username, password, email, role, like(OneToMany)
     * @Etc: 빌더를 사용해서 엔티티에 접근할 것!
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    private String phoneNum;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Like> like = new HashSet<>();

    @Builder
    public Account(Long id, String username, String password,
                   String email, String role, String phoneNum) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phoneNum = phoneNum;
    }
}
