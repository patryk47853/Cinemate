package pl.patrykjava.cinemate.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByEmail(String email);
    boolean existsMemberById(Long id);
    boolean existsMemberByUsername(String username);
    Optional<Member> findMemberByUsername(String username);

    Optional<Member> findMemberByEmail(String email);
}
