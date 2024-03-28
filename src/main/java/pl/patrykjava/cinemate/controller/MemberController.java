package pl.patrykjava.cinemate.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.patrykjava.cinemate.jwt.JWTUtil;
import pl.patrykjava.cinemate.member.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    public MemberController(MemberService memberService, JWTUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("{memberId}")
    public MemberDto getMember(@PathVariable("memberId") Long memberId) {
        return memberService.getMember(memberId);
    }

    @GetMapping
    public List<MemberDto> getMembers() {
        return memberService.getAllMembers();
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
