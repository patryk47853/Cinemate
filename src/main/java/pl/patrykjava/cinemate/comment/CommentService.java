package pl.patrykjava.cinemate.comment;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.member.Member;
import pl.patrykjava.cinemate.movie.Movie;

import java.util.List;

@Service
public class CommentService {

    private final CommentDao commentDao;

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public Comment getCommentById(Long id) {
        return commentDao.selectCommentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No comment with ID: " + id + " has been found."));
    }

    public List<Comment> getCommentsByMemberId(Long memberId) {
        return commentDao.selectCommentsByMemberId(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("No comments found for member with ID: " + memberId));
    }

    public List<Comment> getCommentsByMovieId(Long movieId) {
        return commentDao.selectCommentsByMovieId(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("No comments found for movie with ID: " + movieId));
    }

    public List<Comment> getAllComments() {
        return commentDao.selectAllComments();
    }

    public void addComment(CommentAddRequest request) {
        Member member = request.member();
        Movie movie = request.movie();

        if(member == null) throw new ResourceNotFoundException(
                "No such member has been found."
        );

        if(movie == null) throw new ResourceNotFoundException(
                "No such movie has been found."
        );

        Comment comment = new Comment(request.content(), member, movie);

        commentDao.insertComment(comment);
    }

    public void deleteCommentById(Long id) {
        if (!commentDao.existsCommentWithId(id)) {
            throw new ResourceNotFoundException("Comment with ID: " + id + " not found.");
        }

        commentDao.deleteCommentById(id);
    }

    public void deleteCommentsByMemberId(Long memberId) {
        if (!commentDao.existsCommentWithMemberId(memberId)) {
            throw new ResourceNotFoundException("Comments for member with ID: " + memberId + " not found.");
        }
        commentDao.deleteCommentsByMemberId(memberId);
    }

        public void deleteCommentsByMovieId(Long movieId) {
            if (!commentDao.existsCommentWithMovieId(movieId)) {
                throw new ResourceNotFoundException("Comments for movie with ID: " + movieId + " not found.");
            }
            commentDao.deleteCommentsByMovieId(movieId);
        }

    public void updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentDao.selectCommentById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("No comment found with ID: " + commentId));

        if (request.content() != null && !request.content().equals(comment.getContent())) {
            comment.setContent(request.content());
        } else {
            throw new RequestValidationException("No changes were made.");
        }

        commentDao.updateComment(comment);
    }
}
