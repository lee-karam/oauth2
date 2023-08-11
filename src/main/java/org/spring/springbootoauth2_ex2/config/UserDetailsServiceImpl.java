package org.spring.springbootoauth2_ex2.config;

import org.spring.springbootoauth2_ex2.entity.MemberEntity;
import org.spring.springbootoauth2_ex2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private MemberRepository memberRepository;

  @Override                       // 실제 email
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    Optional<MemberEntity> optionalMemberEntity=memberRepository.findByEmail(email);
//    if(!optionalMemberEntity.isPresent()){
//      throw new UsernameNotFoundException("이메일(아이디가)이 없다.");
//    }
    MemberEntity memberEntity= memberRepository.findByEmail(email).orElseThrow(()->{
      throw new UsernameNotFoundException("이메일(아이디가)이 없다.");
    });
//    MemberEntity memberEntity2= memberRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
//    return User.builder()
//            .username(memberEntity.getEmail())
//            .password(memberEntity.getPw())
//            .roles(memberEntity.getRole().toString())
//            .build();

    return new MyUserDetails(memberEntity);
  }
}
