package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //어디 내장 타입?
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA 스펙상 엔티티나 임베디드 타입은 기본생성자를 public or protected 필수
    // JPA 구현 라이브러리가 객체 생성할 때 리플렉션 같은 기술 사용하려면 기본 생성자 필요함
    // 누가 호출 못하게 protected 로 생성
    protected Address() {
    }

    //값 타입은 변경 불가능하게 설계 필수! 셋터 없고 생성자만만
   public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
