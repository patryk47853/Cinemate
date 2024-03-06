package pl.patrykjava.cinemate.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.patrykjava.cinemate.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByEmail(String email);
    boolean existsMemberById(Long id);
    boolean existsMemberByUsername(String username);
}
