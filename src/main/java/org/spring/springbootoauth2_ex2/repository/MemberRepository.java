package org.spring.springbootoauth2_ex2.repository;

import org.spring.springbootoauth2_ex2.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
  Optional<MemberEntity> findByEmail(String email);
}
