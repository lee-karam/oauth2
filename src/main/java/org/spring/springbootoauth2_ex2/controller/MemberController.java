package org.spring.springbootoauth2_ex2.controller;

import lombok.RequiredArgsConstructor;
import org.spring.springbootoauth2_ex2.config.MyUserDetails;
import org.spring.springbootoauth2_ex2.dto.MemberDto;
import org.spring.springbootoauth2_ex2.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/join")
  public String join(){
    return "member/join";
  }

  @PostMapping("/join")
  public String joinPost(MemberDto memberDto){

    memberService.insertMember(memberDto);

    return "redirect:/member/memberList";
  }


  //Security 로그인  /member/login
  @GetMapping("/login")
  public String login(){
    return "member/login";
  }


  @GetMapping("/memberList")
  public String memberList(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){

   List<MemberDto> memberList= memberService.memberListdo();

   model.addAttribute("memberList",memberList);
   model.addAttribute("myUserDetails",myUserDetails);
    return "member/memberList";
  }
  @GetMapping("/detail/{id}")
  public String detail(@PathVariable("id") Long id,Model model,
                       @AuthenticationPrincipal MyUserDetails myUserDetails){

    MemberDto member= memberService.detailMember(id);

    model.addAttribute("member",member);
    model.addAttribute("myUserDetails",myUserDetails);

    return "member/detail";
  }

  //ajax
  @PostMapping("/update")
  public @ResponseBody MemberDto detail(@RequestBody MemberDto memberDto){

   MemberDto memberDto1= memberService.updateMember(memberDto);

    return memberDto1;
  }





}
