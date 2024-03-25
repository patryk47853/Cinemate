package pl.patrykjava.cinemate.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.patrykjava.cinemate.jwt.JWTUtil;
import pl.patrykjava.cinemate.member.Member;
import pl.patrykjava.cinemate.member.MemberRegistrationRequest;
import pl.patrykjava.cinemate.member.MemberService;
import pl.patrykjava.cinemate.member.MemberUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MainController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    public MainController(MemberService memberService, JWTUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Member> getMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("{memberId}")
    public Member getMember(
            @PathVariable("memberId") Long memberId) {
        return memberService.getMember(memberId);
    }

    @PostMapping
    public ResponseEntity<?> registerMember(
            @RequestBody MemberRegistrationRequest request) {
        memberService.addMember(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @DeleteMapping("{memberId}")
    public void deleteMember(
            @PathVariable("memberId") Long memberId) {
        memberService.deleteMemberById(memberId);
    }

    @PutMapping("{memberId}")
    public void updateMember(
            @PathVariable("memberId") Long memberId,
            @RequestBody MemberUpdateRequest updateRequest) {
        memberService.updateMember(memberId, updateRequest);
    }

}
