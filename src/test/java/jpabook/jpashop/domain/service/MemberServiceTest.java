package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


//아래 2개 애너테이션 있어야 스프링이랑 인티젼 해서 DB 까지 가져와서 테스트 함
@RunWith(SpringRunner.class)    //junit을 위함?
@SpringBootTest     //스프링 부트랑 같이 테스트(스프링 컨테이너 사용)0
@Transactional  // DB 롤백을 위함
public class MemberServiceTest {
    // 클래스에서 ctrl + shift + t 하면 테스트 생성

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    //@Rollback(false) //DB 들어간거 보려면 넣자자
   public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        memberService.join(member2);

        /* 원래코드
        try {
            memberService.join(member2);    //예외가 발생햐아 한다!!!
        } catch (IllegalStateException e) {
            return;
        }
        예외가 터지면 다음 코드가 아닌 메서드 밖으로 나가버린다
        그래서 안나가게 트라이 캐치문을 사용하는거
        근데 이러면 지저분해서 위에 (expected = IllegalStateException.class)
        해주면 클래스 밖으로 안나가게 된다.
       */

        //then
        fail("예외가 발생해야 한다.");
    }
}