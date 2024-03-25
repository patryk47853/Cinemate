package pl.patrykjava.cinemate.member;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class MemberJPADataAccessService implements MemberDao {

    private final MemberRepository memberRepository;

    public MemberJPADataAccessService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> selectAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> selectMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> selectMemberByUsername(String username) {
        return memberRepository.findMemberByUsername(username);
    }

    @Override
    public void insertMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public boolean existsMemberWithEmail(String email) {
        return memberRepository.existsMemberByEmail(email);
    }

    @Override
    public boolean existsMemberWithId(Long id) {
        return memberRepository.existsMemberById(id);
    }

    @Override
    public boolean existsMemberWithUsername(String username) {
        return memberRepository.existsMemberByUsername(username);
    }

    @Override
    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public void updateMember(Member member) {
        memberRepository.save(member);
    }
}
