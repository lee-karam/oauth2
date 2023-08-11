package org.spring.springbootoauth2_ex2.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.springbootoauth2_ex2.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class MyUserDetails implements UserDetails , OAuth2User{

  @Autowired
  private MemberEntity memberEntity;

  private Map<String, Object> attributes;

  //일반
  public MyUserDetails(MemberEntity memberEntity){
    this.memberEntity=memberEntity;
  }

  //oAuth2
  public MyUserDetails(MemberEntity memberEntity, Map<String, Object> attributes){
    this.memberEntity=memberEntity;
    this.attributes=attributes;
  }

  // === oAuth2
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getName() {  //SNS아이디 (emaio)
    return memberEntity.getEmail();
  }
  // oAuth2 ====
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collectionRole=new ArrayList<>();
    collectionRole.add(new GrantedAuthority() {
      @Override
      public String getAuthority() {
        return "ROLE_"+memberEntity.getRole().toString();// ROLE_,,
      }
    });
    return collectionRole;
  }

  @Override
  public String getPassword() {
    return memberEntity.getPw();
  }

  @Override
  public String getUsername() {
    return memberEntity.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
