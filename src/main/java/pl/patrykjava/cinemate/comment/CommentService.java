package pl.patrykjava.cinemate.comment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.member.Member;
import pl.patrykjava.cinemate.member.MemberDao;
import pl.patrykjava.cinemate.movie.Movie;
import pl.patrykjava.cinemate.movie.MovieDao;
import pl.patrykjava.cinemate.movie.MovieService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentDao commentDao;
    private final MemberDao memberDao;
    private final MovieDao movieDao;
    private final MovieService movieService;

    public CommentService(CommentDao commentDao, @Qualifier("jpa") MemberDao memberDao, MovieDao movieDao, MovieService movieService) {
        this.commentDao = commentDao;
        this.memberDao = memberDao;
        this.movieDao = movieDao;
        this.movieService = movieService;
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
        Optional<Member> member = memberDao.selectMemberById(request.memberId());
        Optional<Movie> movie = movieDao.selectMovieById(request.movieId());

        if(member.isEmpty()) throw new ResourceNotFoundException(
                "No such member has been found."
        );

        if(movie.isEmpty()) throw new ResourceNotFoundException(
                "No such movie has been found."
        );

        Comment comment = new Comment(request.content(), member.get(), movie.get());

        commentDao.insertComment(comment);
        movieService.getMovie(request.movieId()).getComments().add(comment);
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
