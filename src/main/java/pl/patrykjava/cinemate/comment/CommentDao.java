package pl.patrykjava.cinemate.comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Optional<Comment> selectCommentById(Long id);
    Optional<List<Comment>> selectCommentsByMemberId(Long id);
    Optional<List<Comment>> selectCommentsByMovieId(Long id);
    List<Comment> selectAllComments();
    boolean existsCommentWithMemberId(Long id);
    boolean existsCommentWithMovieId(Long id);
    boolean existsCommentWithId(Long id);
    void insertComment(Comment comment);
    void deleteCommentById(Long id);
    void deleteCommentsByMemberId(Long id);
    void deleteCommentsByMovieId(Long id);
    void updateComment(Comment comment);
}
