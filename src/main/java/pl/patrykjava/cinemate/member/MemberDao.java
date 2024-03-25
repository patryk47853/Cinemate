package pl.patrykjava.cinemate.member;

import java.util.List;
import java.util.Optional;

public interface MemberDao {
    List<Member> selectAllMembers();
    Optional<Member> selectMemberById(Long id);
    Optional<Member> selectMemberByUsername(String username);
    void insertMember(Member member);
    boolean existsMemberWithEmail(String email);
    boolean existsMemberWithId(Long id);
    boolean existsMemberWithUsername(String username);
    void deleteMemberById(Long id);
    void updateMember(Member member);
}
