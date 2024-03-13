package pl.patrykjava.cinemate.member;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public List<Member> showAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/hello")
    public String helloAWS() {
        return "Hello AWS! :)";
    }

    @GetMapping("/members/{id}")
    public Member showMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    @PostMapping("/register")
    public void registerMember(@RequestBody MemberRegistrationRequest request) {
        memberService.addMember(request);
    }

    @DeleteMapping("/members/{id}")
    public void deleteMemberById(@PathVariable Long id) {
        memberService.deleteMemberById(id);
    }

    @PutMapping("/members/{id}")
    public void updateMemberById(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
        memberService.updateMember(id, request);
    }
}
