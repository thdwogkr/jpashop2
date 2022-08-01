package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    /*
    //기존 코드
    ============================================
    @PersistenceContext //JPA / 스프링이 엔티티매니저를 만들어서 주입?
    private EntityManager em;
    ============================================

    스프링 부트(스프링 Data JPA) 쓰면 위 코드를 다음처럼 변경 가능

    ============================================
    @Autowired  //얘가 @PersistenceContext 기능을 한다(스프링 Data JPA 덕분)
    private EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }
    ============================================
    그러면 @RequiredArgsConstructor 쓰면 private 필드 보고 자동으로 생성자 만들어주니까
    현재 코드처럼 작성 가능!

     */

    public void save(Member member) {
        em.persist(member); //JPA 가 member 를 저장
   }

    public Member findOne(Long id) {
        //(타입, PrimaryKey) 넣어주기
        return em.find(Member.class, id);   //Member 를 찾아서 반환
    }

    public List<Member> findAll() {
        // JPQL query, 반환타입 순으로 입력
        // Member 를 List 로 변환
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();

/*      아래처럼 작성하고 상단에 있는 result 에서 ctrl+alt+N 누르면 위 코드로 변경
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();// Member 를 List 로 변환

        return result;*/
    }

    //이름으로 조회하는 기능 구현
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
