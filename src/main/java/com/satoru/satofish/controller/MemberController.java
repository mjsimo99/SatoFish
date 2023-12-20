package com.satoru.satofish.controller;

import com.satoru.satofish.model.dto.MemberDTO;
import com.satoru.satofish.model.dto.response.MemberRespDTO;
import com.satoru.satofish.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;




    @PostMapping("/add")
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) {
        MemberDTO createdMember = memberService.save(memberDTO);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberRespDTO>>getAllMembers() {
        List<MemberRespDTO> members = memberService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/{num}")
    public ResponseEntity<MemberRespDTO> getMemberById(@PathVariable Long num) {
        MemberRespDTO member = memberService.getMemberById(num);
        return member != null
                ? new ResponseEntity<>(member, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<MemberRespDTO>> searchMembers(@PathVariable String query) {
        List<MemberRespDTO> searchResults = memberService.searchMembers(query);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }


    @PutMapping("/{num}")
    public ResponseEntity<MemberDTO> updateMember(
            @PathVariable Long num, @Valid @RequestBody MemberDTO memberDTO) {
        MemberDTO updatedMember = memberService.updateMember(num, memberDTO);
        return updatedMember != null
                ? new ResponseEntity<>(updatedMember, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<String> deleteMember(@PathVariable Long num) {
        try {
            boolean deleted = memberService.deleteMember(num);
            return deleted
                    ? new ResponseEntity<>("Member deleted successfully", HttpStatus.OK)
                    : new ResponseEntity<>("Member not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
