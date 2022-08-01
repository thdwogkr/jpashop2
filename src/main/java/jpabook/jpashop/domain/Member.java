package jpabook.jpashop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    //Address class에 @Embeddable 붙여줘서 여긴 이거 붙여준 듯
    //원래 여기 or Address Class 둘중 하나만 써도 되긴 되는데 보통 둘다 쓴다고 한다
    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany  //하나에 멤버가 여러개의 오더를 하니까 1대다 (오더 class에도 해주자)
            (mappedBy = "member")   //양방향 연관관계일 경우 주인이 아닌 녀석에게 이거 붙여줌, 주인 클래스의 필드명 붙여주면 됨
    private List<Order> orders = new ArrayList<>();

}
