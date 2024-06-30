package pl.patrykjava.cinemate.member;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("{memberId}")
    public MemberDto getMember(@PathVariable("memberId") Long memberId) {
        return memberService.getMember(memberId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/profile/{username}")
    public MemberDto getMember(@PathVariable("username") String username) {
        return memberService.getMember(username);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<MemberDto> getMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping
    public ResponseEntity<?> registerMember(@RequestBody MemberRegistrationRequest request) {
        memberService.addMember(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{memberId}")
    public void deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMemberById(memberId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{memberId}")
    public void updateMember(
            @PathVariable("memberId") Long memberId,
            @RequestBody MemberUpdateRequest updateRequest) {
        memberService.updateMember(memberId, updateRequest);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{memberId}/favorites/{movieId}")
    public ResponseEntity<String> addMovieToFavorites(@PathVariable Long memberId, @PathVariable Long movieId) {
        memberService.addMovieToFavorites(memberId, movieId);
        return ResponseEntity.ok("Movie added to favorites.");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{memberId}/favorites/{movieId}")
    public ResponseEntity<String> removeMovieFromFavorites(@PathVariable Long memberId, @PathVariable Long movieId) {
        memberService.removeMovieFromFavorites(memberId, movieId);
        return ResponseEntity.ok("Movie removed from favorites.");
    }

}
