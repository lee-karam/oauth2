package org.spring.springbootoauth2_ex2.controller;

import org.spring.springbootoauth2_ex2.config.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

  @GetMapping({"","/"})
  public String index(){
    return "index";
  }

  @GetMapping("/shop")
  public @ResponseBody String shop(@AuthenticationPrincipal MyUserDetails myUserDetails){
    return "shop페이지입니다. SELLER,ADMIN , "+myUserDetails.getMemberEntity().getEmail();
  }
  @GetMapping("/admin")
  public @ResponseBody String admin(@AuthenticationPrincipal MyUserDetails myUserDetails){
    return "admin페이지입니다. ADMIN "+myUserDetails.getMemberEntity().getEmail();
  }
}
