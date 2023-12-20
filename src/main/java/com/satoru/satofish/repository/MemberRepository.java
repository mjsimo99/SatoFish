package com.satoru.satofish.repository;

import com.satoru.satofish.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface MemberRepository extends JpaRepository<Member, Long> {



    List<Member> findMembersByNum(Long num);
    List<Member> findMembersByNameIgnoreCaseContaining(String name);
    List<Member> findMembersByFamilyNameIgnoreCaseContaining(String familyName);


}
