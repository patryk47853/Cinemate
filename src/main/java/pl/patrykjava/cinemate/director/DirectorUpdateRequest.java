package pl.patrykjava.cinemate.director;

import pl.patrykjava.cinemate.movie.Movie;

import java.util.List;

public record DirectorUpdateRequest(String firstName, String lastName, List<Movie> moviesToAdd) {
}
