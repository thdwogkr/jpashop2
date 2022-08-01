package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //이거 안써주면 테이블명이 클래스명 따라가나보다
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  //여러개의 오더가 하나의 멤버로부터 올수 있으니 다대일, Member class에도 작업
    @JoinColumn(name = "member_id") //맵핑 할 기준으로 foreign key를 의미함
    private Member member;

    //Member class - orders 봐봐
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //1대1 관계는 foreign key 어디에 둬도 상관 없다(보통은 많이 보는 쪽에 foreign key 둔다)
    //이번 예제는 딜리버리로 오더를 보기보단, 오더로 딜리보기를 본다 (따라서 오더쪽에 포린키 둠)
    //따라서 연관관계 주인도 Order Class 에 있는 Delivery 가 된다
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;
    //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    //주문상태 [ORDER, CANCEL]

    /*
     //비지니스 로직상 아래처럼 했어야 한다.
     public static void main(String[] args) {
         Member member = new Member();
         Order order = new Order();

         member.getOrders().add(order);
         order.setMember(member);
     }
     */
    //==연관관계 편입 메서드==//
    //양방향 관계에서 넣어주는 메서드 (핵심적인 곳에 넣어라?)
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비지니스 로직==//
    //주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    //전체 주문 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}
