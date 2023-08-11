package org.spring.springbootoauth2_ex2.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.springbootoauth2_ex2.entity.MemberEntity;
import org.spring.springbootoauth2_ex2.entity.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class MemberDto {

  public Long id;

  private String email;

  private String pw;

  private String nickName;

  private Role role;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;



}
