package org.spring.springbootoauth2_ex2.service;

import lombok.RequiredArgsConstructor;
import org.spring.springbootoauth2_ex2.dto.MemberDto;
import org.spring.springbootoauth2_ex2.entity.MemberEntity;
import org.spring.springbootoauth2_ex2.entity.Role;
import org.spring.springbootoauth2_ex2.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void insertMember(MemberDto memberDto) {
    String email = memberRepository.save(MemberEntity.builder()
            .email(memberDto.getEmail())
            .pw(passwordEncoder.encode(memberDto.getPw()))
            .role(Role.MEMBER)
            .build()).getEmail();
    memberRepository.findByEmail(email).orElseThrow(() -> {
      return new IllegalArgumentException("이메일이 존재 하지 않습니다.");
    });
  }

  public List<MemberDto> memberListdo() {
    List<MemberDto> memberDtos = new ArrayList<>();
    List<MemberEntity> memberEntities = memberRepository.findAll();

    for (MemberEntity memberEntity : memberEntities) {
//      MemberDto memberDto = MemberDto.builder()
//              .id(memberEntity.getId())
//              .pw(memberEntity.getPw())
//              .role(memberEntity.getRole())
//              .createTime(memberEntity.getCreateTime())
//              .email(memberEntity.getEmail())
//              .build();
      memberDtos.add(MemberDto.builder()
              .id(memberEntity.getId())
              .pw(memberEntity.getPw())
              .role(memberEntity.getRole())
              .createTime(memberEntity.getCreateTime())
              .email(memberEntity.getEmail())
              .build());
    }
    return memberDtos;
  }

  public MemberDto detailMember(Long id)  {

//  MemberEntity memberEntity1=memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
  MemberEntity memberEntity=memberRepository.findById(id).orElseThrow(()->{
    return new IllegalArgumentException("이메일이 존재 하지 않습니다");
  });

  return MemberDto.builder()
          .id(memberEntity.getId())
          .email(memberEntity.getEmail())
          .role(memberEntity.getRole())
          .createTime(memberEntity.getCreateTime())
          .updateTime(memberEntity.getUpdateTime())
          .build();
  }

  public MemberDto updateMember(MemberDto memberDto) {

   MemberEntity memberEntity= memberRepository.save(MemberEntity.builder()
            .id(memberDto.id)
            .email(memberDto.getEmail())
            .role(memberDto.getRole())
            .build());
   MemberEntity memberEntity1
           = memberRepository.findByEmail(memberEntity.getEmail()).orElseThrow(IllegalArgumentException::new);
   return MemberDto.builder()
           .id(memberEntity1.getId())
           .email(memberEntity1.getEmail())
           .role(memberEntity1.getRole())
           .build();
  }
}
