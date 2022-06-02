package com.gsm.mat.member.service;

import com.gsm.mat.configuration.security.jwt.TokenProvider;
import com.gsm.mat.exception.ErrorCode;
import com.gsm.mat.exception.exception.DuplicateMemberException;
import com.gsm.mat.exception.exception.MemberNotExistsException;
import com.gsm.mat.exception.exception.PasswordNotCorrectException;
import com.gsm.mat.exception.exception.UserNotFoundException;
import com.gsm.mat.member.Member;
import com.gsm.mat.member.dto.MemberDto;
import com.gsm.mat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

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
    public Map<String,String> login(String email,String password){
        Member member = memberRepository.findOneByEmail(email);
        if(member==null){throw new UserNotFoundException("User can't find", ErrorCode.USER_NOT_FOUND);}//해당 id를 가진 유저가 존재하는지 확인

        boolean check = passwordEncoder.matches(password, member.getPassword());
        if(!check){throw new PasswordNotCorrectException("Password is not correct", ErrorCode.PASSWORD_NOT_MATCH);}

        //토큰 발급
        final String accessToken=tokenProvider.generateAccessToken(member.getEmail());
        final String refreshToken=tokenProvider.generateRefreshToken(member.getEmail());

        member.updateRefreshToken(refreshToken);

        Map<String,String> map=new HashMap<>();
        map.put("email",member.getEmail());
        map.put("accessToken",accessToken);
        map.put("refreshToken",refreshToken);
        return map;
    }

    public void changeMajority(String majority){
        String userEmail = getUserEmail();
        Member member = findByEmail(userEmail);
        member.updateMajority(majority);
    }
    private void validDuplicateMember(Member member) {
        Member byEmail = memberRepository.findOneByEmail(member.getEmail());
        if(!(byEmail==null)){
            throw new DuplicateMemberException("Member already exists", ErrorCode.DUPLICATE_MEMBER);
        }
    }

    //회원조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotExistsException("Member can't find", ErrorCode.MEMBER_NOT_EXISTS));
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email){
        Member byEmail = memberRepository.findOneByEmail(email);
        if(byEmail==null){
            throw new MemberNotExistsException("Member can't find", ErrorCode.MEMBER_NOT_EXISTS);
        }
        return byEmail;
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
