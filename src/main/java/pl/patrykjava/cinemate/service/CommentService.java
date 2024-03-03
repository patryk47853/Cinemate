package pl.patrykjava.cinemate.service;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.entity.Actor;
import pl.patrykjava.cinemate.entity.Comment;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No comment with ID: " + id + " has been found."));
    }
}
