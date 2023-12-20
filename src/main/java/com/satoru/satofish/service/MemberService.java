package com.satoru.satofish.service;

import com.satoru.satofish.model.dto.MemberDTO;
import com.satoru.satofish.model.entity.Member;
import com.satoru.satofish.model.dto.response.MemberRespDTO;
import com.satoru.satofish.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MemberDTO save(MemberDTO memberDTO) {

        Member member = modelMapper.map(memberDTO, Member.class);

        member = memberRepository.save(member);
        return modelMapper.map(member, MemberDTO.class);
    }

    public List <MemberRespDTO> getAllMembers() {
        List <Member> members = memberRepository.findAll();
        return members.stream()
                .map(member -> modelMapper.map(member, MemberRespDTO.class))
                .collect(Collectors.toList());
    }

    public MemberRespDTO getMemberById(Long num) {
        Member member = memberRepository.findById(num)
                .orElse(null);
        return (member != null) ? modelMapper.map(member, MemberRespDTO.class) : null;
    }

    public List<MemberRespDTO> searchMembers(Object query) {
        List<Member> members;

        if (query instanceof Long) {
            String queryString = String.valueOf(query);
            members = memberRepository.findMembersByNum(Long.parseLong(queryString));
        } else {
            String queryString = (String) query;
            members = memberRepository.findMembersByNameIgnoreCaseContaining(queryString);
            if (members.isEmpty()) {
                members = memberRepository.findMembersByFamilyNameIgnoreCaseContaining(queryString);
            }
        }

        return members.stream()
                .map(member -> modelMapper.map(member, MemberRespDTO.class))
                .collect(Collectors.toList());
    }


    public MemberDTO updateMember(Long num, MemberDTO memberDTO) {
        Member member = memberRepository.findById(num)
                .orElse(null);
        if (member != null) {
            member = modelMapper.map(memberDTO, Member.class);
            member.setNum(num);
            member = memberRepository.save(member);
            return modelMapper.map(member, MemberDTO.class);
        }
        return null;
    }

    public boolean deleteMember(Long num) {
        Member member = memberRepository.findById(num)
                .orElse(null);
        if (member != null) {
            memberRepository.delete(member);
            return true;
        }
        return false;
    }




}
