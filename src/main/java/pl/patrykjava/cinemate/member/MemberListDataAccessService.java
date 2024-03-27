package pl.patrykjava.cinemate.member;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Main purpose: Testing without interacting with database
@Repository("list")
public class MemberListDataAccessService implements MemberDao {

    private static final List<Member> members;

    static {
        Member patryk = new Member(1L, "Patryk", "patryk@gmail.com", "admin123");
        Member patrycja = new Member(2L, "Patrycja", "patrycja@gmail.com", "admin123");

        members = List.of(patryk, patrycja);
    }
    @Override
    public List<Member> selectAllMembers() {
        return members;
    }

    @Override
    public Optional<Member> selectMemberById(Long id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Member> selectMemberByUsername(String username) {
        return members.stream()
                .filter(member -> member.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<Member> selectMemberByEmail(String email) {
        return members.stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void insertMember(Member member) {
        members.add(member);
    }

    @Override
    public boolean existsMemberWithEmail(String email) {
        return members.stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }

    @Override
    public boolean existsMemberWithId(Long id) {
        return members.stream()
                .anyMatch(member -> member.getId().equals(id));
    }

    @Override
    public boolean existsMemberWithUsername(String username) {
        return members.stream()
                .anyMatch(member -> member.getUsername().equals(username));
    }

    @Override
    public void deleteMemberById(Long id) {
        members.removeIf(member -> member.getId().equals(id));
    }

    @Override
    public void updateMember(Member member) {
        members.add(member);
    }
}
