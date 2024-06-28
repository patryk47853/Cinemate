package pl.patrykjava.cinemate.movie;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MovieDtoMapper implements Function<Movie, MovieDto> {

    @Override
    public MovieDto apply(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getImgUrl()
        );
    }
}
