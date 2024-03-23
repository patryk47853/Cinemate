package pl.patrykjava.cinemate.comment;

import pl.patrykjava.cinemate.member.Member;
import pl.patrykjava.cinemate.movie.Movie;

public record CommentAddRequest(String content, Movie movie, Member member) {
}
