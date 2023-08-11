package org.spring.springbootoauth2_ex2.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "oauth2_member1")
public class MemberEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  public Long id;

  @Column(nullable = false,unique = true)
  private String email;

  @Column(nullable = false)
  private String pw;

  @Enumerated(EnumType.STRING)
  private Role role;



}
