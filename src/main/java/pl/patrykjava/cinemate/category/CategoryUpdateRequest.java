package pl.patrykjava.cinemate.category;

import pl.patrykjava.cinemate.movie.Movie;

import java.util.List;

public record CategoryUpdateRequest(String name, List<Movie> moviesToAdd) {

}
