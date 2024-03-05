package pl.patrykjava.cinemate.comment;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No comment with ID: " + id + " has been found."));
    }
}
