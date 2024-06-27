package pl.patrykjava.cinemate.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add")
    public ResponseEntity<String> addComment(@RequestBody CommentAddRequest request) {
        commentService.addComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment added successfully.");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Comment>> getCommentsByMovieId(@PathVariable Long movieId) {
        List<Comment> comments = commentService.getCommentsByMovieId(movieId);
        return ResponseEntity.ok(comments);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }
}
