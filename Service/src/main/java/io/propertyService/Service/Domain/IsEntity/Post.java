package io.propertyService.Service.Domain.IsEntity;

import io.propertyService.Service.Domain.NotEntity.Address;
import io.propertyService.Service.Domain.NotEntity.BaseEntity;
import io.propertyService.Service.Domain.NotEntity.Category;
import io.propertyService.Service.Domain.NotEntity.TransactionType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    /**
     * @Author: 지영석
     * @Fields: pk(post_id), title, content, author, grade, address, category,
     *          transactionType, findCnt, like(ManyToOne), propertyInfo(OneToOne)
     * @Etc: setter를 어떻게 처리할까?
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private int grade;

    @Embedded
    private Address address;

    private String category;

    private String transactionType;
//
//    @Enumerated(EnumType.STRING)
//    private Category category;
//
//    @Enumerated(EnumType.STRING)
//    private TransactionType transactionType;

    private Long findCnt;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Like> like = new HashSet<>();

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PropertyInfo propertyInfo;

    /**
     * @Issues: setter가 update처리에는 유용하지만..추후에 어떻게 처리해야할지 고민해봐야할듯..
     */

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //조회수를 1씩 높혀줌.
    public void setFindCnt(Long findCnt) {
        this.findCnt = findCnt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPropertyInfo(PropertyInfo propertyInfo) {
        /**
         * @description: 1대1 관계를 처리하기 위한 연관관계 편의 메소드
         * @Params: propertyinfo
         */
        this.propertyInfo = propertyInfo;
        propertyInfo.setPost(this);
    }

    @Builder
    public Post(Long id, String title, String content, String author, int grade,
                Address address, String category,
                String transactionType, PropertyInfo propertyInfo, Long findCnt) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.grade = grade;
        this.address = address;
        this.category = category;
        this.propertyInfo = propertyInfo;
        this.transactionType = transactionType;
        this.findCnt = findCnt;
    }
}

