package io.propertyService.Service.Domain.IsEntity;

import io.propertyService.Service.Domain.NotEntity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "post_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Entity
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Like(Account account, Post post) {
        this.account = account;
        this.post = post;
    }
}
