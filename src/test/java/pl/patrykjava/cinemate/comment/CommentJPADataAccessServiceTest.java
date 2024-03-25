package pl.patrykjava.cinemate.comment;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentJPADataAccessServiceTest {

    private CommentJPADataAccessService commentJPADataAccessService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        commentJPADataAccessService = new CommentJPADataAccessService(commentRepository);
    }

    @Test
    void selectCommentById() {
        //Given
        Long id = 1L;

        //When
        commentJPADataAccessService.selectCommentById(id);

        //Then
        verify(commentRepository).findById(id);
    }

    @Test
    void selectCommentsByMemberId() {
        //Given
        Long id = 1L;

        //When
        commentJPADataAccessService.selectCommentsByMemberId(id);

        //Then
        verify(commentRepository).findAllByMemberId(id);
    }

    @Test
    void selectCommentsByMovieId() {
        //Given
        Long id = 1L;

        //When
        commentJPADataAccessService.selectCommentsByMovieId(id);

        //Then
        verify(commentRepository).findAllByMovieId(id);
    }

    @Test
    void selectAllComments() {
        //When
        commentJPADataAccessService.selectAllComments();

        //Then
        verify(commentRepository).findAll();
    }

    @Test
    void existsCommentWithMemberId() {
        //Given
        Long id = 1L;

        //When
        commentJPADataAccessService.existsCommentWithMemberId(id);

        //Then
        verify(commentRepository).existsCommentByMemberId(id);
    }

    @Test
    void existsCommentWithMovieId() {
        //Given
        Long id = 1L;

        //When
        commentJPADataAccessService.existsCommentWithMovieId(id);

        //Then
        verify(commentRepository).existsCommentByMovieId(id);
    }

    @Test
    void existsCommentWithId() {
        //Given
        Long id = 1L;

        //When
        commentJPADataAccessService.existsCommentWithId(id);

        //Then
        verify(commentRepository).existsCommentById(id);
    }

    @Test
    void insertComment() {
        //Given
        Comment comment = new Comment();

        //When
        commentJPADataAccessService.insertComment(comment);

        //Then
        verify(commentRepository).save(comment);
    }

    @Test
    void deleteCommentById() {
        //Given
        Long id = 1L;

        //When
        commentJPADataAccessService.deleteCommentById(id);

        //Then
        verify(commentRepository).deleteById(id);
    }

    @Test
    void deleteCommentsByMemberId() {
        //Given
        Long id = 1L;

        //When
        commentJPADataAccessService.deleteCommentsByMemberId(id);

        //Then
        verify(commentRepository).deleteAllByMemberId(id);
    }

    @Test
    void deleteCommentsByMovieId() {
        //Given
        Long id = 1L;

        //When
        commentJPADataAccessService.deleteCommentsByMovieId(id);

        //Then
        verify(commentRepository).deleteAllByMovieId(id);
    }

    @Test
    void updateComment() {
        //Given
        Comment comment = new Comment();

        //When
        commentJPADataAccessService.updateComment(comment);

        //Then
        verify(commentRepository).save(comment);
    }
}