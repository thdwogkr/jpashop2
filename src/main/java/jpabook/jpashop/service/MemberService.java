package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)      //JPA 데이터 변경은 트랜잭션 안에서 동작 필요(여기에 하면 아래 모든 public 메서드에도 적용됨)
@RequiredArgsConstructor    //lombok 기능
public class MemberService {


    private final MemberRepository memberRepository;
/*
    @Autowired  //생성자 하나라서 생략 가능
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // @RequiredArgsConstructor 는 final 있는 것만 생성자 만들어줘서 생략 가능
    // @AllArgsConstructor 는 모든 필드 생성자 다 만들어 줌
    */

    //회원 가입
    @Transactional      //여기에 ReadOnly=True 넣으면 데이터 변경이 안됨, default = false
    public Long join(Member member) {
        validateDuplicateMember(member);    //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
//    @Transactional(readOnly = true) //읽기 기능에는 해당 내용 적어주기, 클래스에 해놔서 생략 가능)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
