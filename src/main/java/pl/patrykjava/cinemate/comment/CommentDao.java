package pl.patrykjava.cinemate.comment;

import java.util.Optional;

public interface CommentDao {
    Optional<Comment> selectCommentById(Long id);
    void insertComment(Comment comment);
    void deleteCommentById(Long id);
    void updateComment(Comment comment);
}
