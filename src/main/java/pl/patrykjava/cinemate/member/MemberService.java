package pl.patrykjava.cinemate.member;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberDao memberDao;

    public MemberService(MemberRepository memberRepository, MemberDao memberDao) {
        this.memberRepository = memberRepository;
        this.memberDao = memberDao;
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No actor with ID: " + id + " has been found."));
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public void addMember(MemberRegistrationRequest memberRegistrationRequest) {
        String email = memberRegistrationRequest.email();
        if(memberDao.existsMemberWithEmail(email)) {
            throw new DuplicateResourceException("Email: " + email + "is already taken.");
        }

        Member member = new Member(
                memberRegistrationRequest.username(),
                memberRegistrationRequest.email()
        );

        memberDao.insertMember(member);
    }
}
