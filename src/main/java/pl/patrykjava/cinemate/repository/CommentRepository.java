package pl.patrykjava.cinemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.patrykjava.cinemate.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
