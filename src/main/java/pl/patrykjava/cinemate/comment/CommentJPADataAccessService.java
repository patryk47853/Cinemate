package pl.patrykjava.cinemate.comment;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentJPADataAccessService implements CommentDao {

    private final CommentRepository commentRepository;

    public CommentJPADataAccessService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Comment> selectCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Optional<List<Comment>> selectCommentsByMemberId(Long id) {
        return commentRepository.findAllByMemberId(id);
    }

    @Override
    public Optional<List<Comment>> selectCommentsByMovieId(Long id) {
        return commentRepository.findAllByMovieId(id);
    }

    @Override
    public List<Comment> selectAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public boolean existsCommentWithMemberId(Long id) {
        return commentRepository.existsCommentByMemberId(id);
    }

    @Override
    public boolean existsCommentWithMovieId(Long id) {
        return commentRepository.existsCommentByMovieId(id);
    }

    @Override
    public boolean existsCommentWithId(Long id) {
        return commentRepository.existsCommentById(id);
    }

    @Override
    public void insertComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteCommentsByMemberId(Long id) {
        commentRepository.deleteAllByMemberId(id);
    }

    @Override
    public void deleteCommentsByMovieId(Long id) {
        commentRepository.deleteAllByMovieId(id);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }
}
