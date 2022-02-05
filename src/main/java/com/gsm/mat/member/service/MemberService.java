package com.gsm.mat.member.service;

import com.gsm.mat.configuration.security.jwt.TokenProvider;
import com.gsm.mat.exception.ErrorCode;
import com.gsm.mat.exception.exception.PasswordNotCorrectException;
import com.gsm.mat.exception.exception.UserNotFoundException;
import com.gsm.mat.member.Member;
import com.gsm.mat.member.dto.MemberDto;
import com.gsm.mat.member.repository.MemberRepository;
import com.gsm.mat.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    /**
     *회원가입
     */
    public Long join(MemberDto memberDto){
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        Member member=memberDto.toEntity();
        validDuplicateMember(member);//중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     *로그인
     */
    public Map<String,String> login(String id,String password){
        List<Member> byEmail = memberRepository.findByEmail(id);
        if(byEmail.size()==0){throw new UserNotFoundException("User can't find", ErrorCode.USER_NOT_FOUND);}//해당 id를 가진 유저가 존재하는지 확인

        Member member = byEmail.get(0);
        boolean check = passwordEncoder.matches(password, member.getPassword());
        if(!check){throw new PasswordNotCorrectException("Password is not correct", ErrorCode.USER_NOT_FOUND);}

        //토큰 발급
        final String accessToken=tokenProvider.generateAccessToken(member.getEmail());
        final String refreshToken=tokenProvider.generateRefreshToken(member.getEmail());

        //토큰 유효기간 설정
        redisUtil.setDataExpire(refreshToken,member.getEmail(),TokenProvider.REFRESH_TOKEN_EXPIRE_TIME);

        Map<String,String> map=new HashMap<>();
        map.put("id",member.getEmail());
        map.put("accessToken",accessToken);
        map.put("refreshToken",refreshToken);
        return map;
    }

    public void logOut(){
        String userEmail = this.getUserEmail();
        redisUtil.deleteData(userEmail);
    }

    public void changeMajority(String majority){
        memberRepository.updateMajority(majority);
    }
    private void validDuplicateMember(Member member) {
        List<Member> byEmail = memberRepository.findByEmail(member.getEmail());
        if(!byEmail.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    //회원조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email){
        List<Member> byEmail = memberRepository.findByEmail(email);
        return byEmail.get(0);
    }
    static public String getUserEmail() {
        String userEmail;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }
}
