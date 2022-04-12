package com.gsm.mat.member.controller;

import com.gsm.mat.member.dto.MemberDto;
import com.gsm.mat.member.dto.SignInDto;
import com.gsm.mat.member.service.MemberService;
import com.gsm.mat.response.ResponseService;
import com.gsm.mat.response.result.CommonResult;
import com.gsm.mat.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class memberController {
    private final MemberService memberService;
    private final ResponseService responseService;
    @PostMapping("/member/join")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult join(@RequestBody MemberDto memberDto){
        memberService.join(memberDto);
        return responseService.getSuccessResult();
    }
    @GetMapping("/member/{memberIdx}")
    public SingleResult findUserById(@PathVariable Long memberIdx){
        return responseService.getSingleResult(memberService.findOne(memberIdx));
    }
    @PutMapping("/member/majority")
    public CommonResult changeMajority(@RequestBody String majority){
        memberService.changeMajority(majority);
        return responseService.getSuccessResult();
    }
    @PostMapping("/member/login")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<Map<String, String>> login(@RequestBody SignInDto signInDto)throws Exception{
        return responseService.getSingleResult(memberService.login(signInDto.getEmail(),signInDto.getPassword()));
    }
    @PostMapping("/member/logout")
    public CommonResult logOut() {
        return responseService.getSuccessResult();
    }
}
