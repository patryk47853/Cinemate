package pl.patrykjava.cinemate.member;

import pl.patrykjava.cinemate.comment.Comment;
import pl.patrykjava.cinemate.movie.MovieDto;

import java.util.List;

public record MemberDto(
        Long id,
        String username,
        String email,
        String imgUrl,
        List<Comment> comments,
        List<String> roles,
        List<MovieDto> favoriteMovies

) {
}
