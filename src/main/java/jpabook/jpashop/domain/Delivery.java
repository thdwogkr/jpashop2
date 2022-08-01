package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    //Enum 쓸꺼면 @Enumerated 애노테이션 쓰기
    //EnumType default 값이 ORDINAL 인데 숫자로 1,2,3... 된다
    //이럴경우 중간에 ENUM 목록이 바뀌면 COMP 가 2->3로 될 수도 있다
    //따라서 무조건 STRING으로 쓰기
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    //배송상태 READY, COMP
}
