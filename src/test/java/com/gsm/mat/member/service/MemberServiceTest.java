package com.gsm.mat.member.service;

import com.gsm.mat.member.Member;
import com.gsm.mat.member.dto.MemberDto;
import com.gsm.mat.member.dto.SignInDto;
import com.gsm.mat.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Test
    public void 회원가입()throws Exception{
        MemberDto memberDto=new MemberDto("test@gmail.com","1234","조재영",5,"백엔드","조재영@2319");
        Long saveId = memberService.join(memberDto);
        Assertions.assertThat(memberRepository.findById(saveId).getName()).isEqualTo("조재영");
    }
    @Test
    public void 중복_회원_예외(){
        MemberDto member1=new MemberDto("test@gmail.com","1234","조재영",5,"백엔드","조재영@2319");
        MemberDto member2=new MemberDto("test@gmail.com","1234","조재영",5,"백엔드","조재영@2319");
        memberService.join(member1);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,()->{
            memberService.join(member2);//예외가 발생해야함!!
        });
    }
    @Test
    public void 회원_이메일로_조회(){
        MemberDto member1=new MemberDto("k01066624566@gmail.com","1234","조재영",5,"BE","조재영@2319");
        memberService.join(member1);
        Member byEmail = memberService.findByEmail("k01066624566@gmail.com");
        Assertions.assertThat(byEmail.getName()).isEqualTo("조재영");
    }
    @Test
    public void 로그인(){
        MemberDto member1=new MemberDto("test@gmail.com","1234","조재영",5,"BE","조재영@2319");
        memberService.join(member1);
        SignInDto signInDto=new SignInDto("test@gmail.com","1234");
        memberService.login(signInDto.getEmail(),signInDto.getPassword());
        System.out.println("accessToken = "+ memberService.login(signInDto.getEmail(),signInDto.getPassword()).get("accessToken"));
    }
}