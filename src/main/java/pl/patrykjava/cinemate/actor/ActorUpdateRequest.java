package pl.patrykjava.cinemate.actor;

import pl.patrykjava.cinemate.movie.Movie;

import java.util.List;

public record ActorUpdateRequest(String firstName, String lastName, List<Movie> moviesToAdd) {
}
