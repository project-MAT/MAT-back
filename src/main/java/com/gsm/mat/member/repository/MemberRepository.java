package com.gsm.mat.member.repository;

import com.gsm.mat.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findOneByEmail(String email);
}
