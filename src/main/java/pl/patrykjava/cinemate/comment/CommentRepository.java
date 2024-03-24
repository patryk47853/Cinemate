package pl.patrykjava.cinemate.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykjava.cinemate.actor.Actor;
import pl.patrykjava.cinemate.member.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsCommentById(Long id);
    boolean existsCommentByMemberId(Long id);
    boolean existsCommentByMovieId(Long id);
    Optional<List<Comment>> findAllByMemberId(Long id);
    Optional<List<Comment>> findAllByMovieId(Long id);
    void deleteAllByMemberId (Long id);
    void deleteAllByMovieId(Long id);
}
