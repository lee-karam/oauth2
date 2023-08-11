package org.spring.springbootoauth2_ex2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigClass {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    http.csrf().disable();

    // 사용자 요청에 대한 페이지별 설정
    http.authorizeHttpRequests()
            // 모든 접근 허용
//            .antMatchers("/","/member/login","/member/join").permitAll()
//            .antMatchers("/css/**","/images/**","/js/**").permitAll()
            // 로그인시 접근 허용
            .antMatchers("/member/logOut").authenticated()
            // MEMBER, ADMIN
            .antMatchers("/member/detail/**","/member/update/**").hasAnyRole("ADMIN","MEMBER","SELLER")
            // SELLER, ADMIN
            .antMatchers("/shop/**").hasAnyRole("ADMIN","SELLER")
            //  ADMIN
            .antMatchers("/admin/**","/member/memberList").hasAnyRole("ADMIN")
            // 권한 이외의 페이지는 모두허용
            .anyRequest().permitAll()
    ;

    // 로그인 설정
    http.formLogin()
            .loginPage("/member/login")  // 직접 로그인페이지를 설정, 권한이 없는 페이지 -> 자동 이동
            .usernameParameter("email")   //username -> 아이디
            .passwordParameter("pw")      //password -> 비빌번호
            .loginProcessingUrl("/member/login") // form URL POST
            .defaultSuccessUrl("/")   // 로그인 성공시
            .failureUrl("/")
            .permitAll()
            .and()
            .oauth2Login()
            .loginPage("/member/login")
            .userInfoEndpoint()
            .userService(myAuth2UserService())
            ;

    // 로그아웃 설정  -> logout(기본)
    http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/member/logOut")) // 직접 로그아웃URL
            .logoutSuccessUrl("/")
    ;
    return http.build();
  }

  @Bean                   //요청 , User
  public OAuth2UserService<OAuth2UserRequest, OAuth2User> myAuth2UserService(){
    return new MyOAuth2UserService();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }


}
