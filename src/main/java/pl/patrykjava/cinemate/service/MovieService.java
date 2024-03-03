package pl.patrykjava.cinemate.service;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.entity.Actor;
import pl.patrykjava.cinemate.entity.Movie;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.repository.ActorRepository;
import pl.patrykjava.cinemate.repository.MovieRepository;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No movie with ID: " + id + " has been found."));
    }
}
