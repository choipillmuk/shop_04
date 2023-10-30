package inhatc.spring.shop.service;

import inhatc.spring.shop.dto.MemberFormDto;
import inhatc.spring.shop.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto dto = MemberFormDto.builder()
                .password("1111")
                .name("홍길동")
                .email("test@test.com")
                .address("인천시 미추홀구")
                .build();

        return Member.createMember(dto, passwordEncoder);
    }

    @Test
    public  void saveMemberTest(){
        Member member = createMember();
        System.out.println(member);
        Member savedMember = memberService.saveMember(member);
        System.out.println(savedMember);
    }

    @Test
    @DisplayName("중복 회원 테스트")
    public void saveMemberTest2(){
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, ()->{
            memberService.saveMember(member2);
        });

        assertEquals("이미 존재하는 회원입니다.", e.getMessage());
    }
}