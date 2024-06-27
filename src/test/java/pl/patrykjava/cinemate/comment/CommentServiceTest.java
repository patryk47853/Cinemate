package pl.patrykjava.cinemate.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.member.Member;
import pl.patrykjava.cinemate.member.MemberDao;
import pl.patrykjava.cinemate.movie.Movie;
import pl.patrykjava.cinemate.movie.MovieDao;
import pl.patrykjava.cinemate.movie.MovieService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentDao commentDao;

    @Mock
    private @Qualifier("jpa") MemberDao memberDao;
    @Mock
    private MovieDao movieDao;
    @Mock
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentDao, memberDao, movieDao, movieService);
    }

    @Test
    void getCommentById() {
        //Given
        Long id = 1L;

        Comment comment = new Comment(id, "Hi!");

        //When
        when(commentDao.selectCommentById(id)).thenReturn(Optional.of(comment));

        //Then
        Comment actual = commentService.getCommentById(id);
        assertThat(actual).isEqualTo(comment);
    }

    @Test
    void willThrowWhenGetCommentByIdReturnsEmptyOptional() {
        //Given
        Long id = 1L;

        //When
        when(commentDao.selectCommentById(id)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> commentService.getCommentById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No comment with ID: " + id + " has been found.");
    }

    @Test
    void getCommentsByMemberId() {
        //Given
        Long memberId = 1L;

        //When
        List<Comment> comments = Arrays.asList(new Comment(1L, "Great movie!"), new Comment(2L, "Nice work!"));
        when(commentDao.selectCommentsByMemberId(memberId)).thenReturn(Optional.of(comments));

        //Then
        List<Comment> actualComments = commentService.getCommentsByMemberId(memberId);
        assertThat(actualComments).hasSize(2);
    }

    @Test
    void getCommentsByMovieId() {
        //Given
        Long movieId = 1L;

        //When
        List<Comment> comments = Arrays.asList(new Comment(1L, "Great movie!"), new Comment(2L, "Nice work!"));
        when(commentDao.selectCommentsByMovieId(movieId)).thenReturn(Optional.of(comments));

        //Then
        List<Comment> actualComments = commentService.getCommentsByMovieId(movieId);
        assertThat(actualComments).hasSize(2);
    }

    @Test
    void getAllComments() {
        //Given
        List<Comment> comments = Arrays.asList(new Comment(1L, "Great movie!"), new Comment(2L, "Nice work!"));
        when(commentDao.selectAllComments()).thenReturn(comments);

        //Then
        List<Comment> actualComments = commentService.getAllComments();
        assertThat(actualComments).hasSize(2);
    }

    @Test
    void addComment() {
        // Given
        Member member = new Member(1L, "John", "test@123.com", "test123");
        Movie movie = new Movie(1L, "Inception");
        CommentAddRequest request = new CommentAddRequest("Awesome movie!", 1L, 1L);

        when(memberDao.selectMemberById(member.getId())).thenReturn(Optional.of(member));
        when(movieDao.selectMovieById(movie.getId())).thenReturn(Optional.of(movie));
        when(movieService.getMovie(request.movieId())).thenReturn(movie);

        // When
        commentService.addComment(request);

        // Then
        verify(commentDao, times(1)).insertComment(any(Comment.class));
        verify(movieService, times(1)).getMovie(request.movieId());
        assertThat(movie.getComments()).hasSize(1);
        assertThat(movie.getComments().get(0).getContent()).isEqualTo("Awesome movie!");
        assertThat(movie.getComments().get(0).getMember()).isEqualTo(member);
    }

    @Test
    void addComment_ThrowsExceptionWhenMemberNotFound() {
        // Given
        CommentAddRequest request = new CommentAddRequest("Awesome movie!", 1L, 1L);
        when(memberDao.selectMemberById(request.memberId())).thenReturn(Optional.empty());
        when(movieDao.selectMovieById(request.movieId())).thenReturn(Optional.of(new Movie(1L, "Inception")));

        // When & Then
        try {
            commentService.addComment(request);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No such member has been found.");
        }
        verify(commentDao, never()).insertComment(any(Comment.class));
    }

    @Test
    void addComment_ThrowsExceptionWhenMovieNotFound() {
        // Given
        Member member = new Member(1L, "John", "test@123.com", "test123");
        CommentAddRequest request = new CommentAddRequest("Awesome movie!", 1L, 1L);
        when(memberDao.selectMemberById(request.memberId())).thenReturn(Optional.of(member));
        when(movieDao.selectMovieById(request.movieId())).thenReturn(Optional.empty());

        // When & Then
        try {
            commentService.addComment(request);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No such movie has been found.");
        }
        verify(commentDao, never()).insertComment(any(Comment.class));
    }

    @Test
    void deleteCommentById() {
        //Given
        Long commentId = 1L;

        when(commentDao.existsCommentWithId(commentId)).thenReturn(true);

        //Then
        commentService.deleteCommentById(commentId);
        verify(commentDao, times(1)).deleteCommentById(commentId);
    }

    @Test
    void deleteCommentsByMemberId() {
        //Given
        Long memberId = 1L;
         
        when(commentDao.existsCommentWithMemberId(memberId)).thenReturn(true);

        //Then
        commentService.deleteCommentsByMemberId(memberId);
        verify(commentDao, times(1)).deleteCommentsByMemberId(memberId);
    }

    @Test
    void deleteCommentsByMovieId() {
        //Given
        Long movieId = 1L;

        when(commentDao.existsCommentWithMovieId(movieId)).thenReturn(true);

        //Then
        commentService.deleteCommentsByMovieId(movieId);
        verify(commentDao, times(1)).deleteCommentsByMovieId(movieId);
    }

    @Test
    void updateComment() {
        //Given
        Long commentId = 1L;
        CommentUpdateRequest request = new CommentUpdateRequest("Awesome movie!");

        //When
        Comment comment = new Comment(commentId, "Great movie!");
        when(commentDao.selectCommentById(commentId)).thenReturn(Optional.of(comment));

        //Then
        commentService.updateComment(commentId, request);
        verify(commentDao, times(1)).updateComment(comment);
    }

    @Test
    void deleteCommentByIdThrowsExceptionWhenCommentNotFound() {
        //Given
        Long commentId = 1L;
        when(commentDao.existsCommentWithId(commentId)).thenReturn(false);

        //Then
        assertThatThrownBy(() -> commentService.deleteCommentById(commentId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Comment with ID: " + commentId + " not found.");
    }

    @Test
    void deleteCommentsByMemberIdThrowsExceptionWhenCommentsNotExist() {
        //Given
        Long memberId = 1L;
        when(commentDao.existsCommentWithMemberId(memberId)).thenReturn(false);

        //Then
        assertThatThrownBy(() -> commentService.deleteCommentsByMemberId(memberId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Comments for member with ID: " + memberId + " not found.");
    }

    @Test
    void updateCommentThrowsExceptionWhenCommentNotFound() {
        //Given
        Long commentId = 1L;
        CommentUpdateRequest request = new CommentUpdateRequest("Awesome movie!");
        when(commentDao.selectCommentById(commentId)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> commentService.updateComment(commentId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No comment found with ID: " + commentId);
    }

    @Test
    void updateCommentThrowsExceptionWhenNoChangesMade() {
        //Given
        Long commentId = 1L;
        CommentUpdateRequest request = new CommentUpdateRequest("Great movie!");
        Comment comment = new Comment(commentId, "Great movie!");
        when(commentDao.selectCommentById(commentId)).thenReturn(Optional.of(comment));

        //Then
        assertThatThrownBy(() -> commentService.updateComment(commentId, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No changes were made.");
    }

    @Test
    void deleteCommentsByMovieIdThrowsExceptionWhenCommentsNotExist() {
        //Given
        Long movieId = 1L;
        when(commentDao.existsCommentWithMovieId(movieId)).thenReturn(false);

        //Then
        assertThatThrownBy(() -> commentService.deleteCommentsByMovieId(movieId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Comments for movie with ID: " + movieId + " not found.");
    }
}

