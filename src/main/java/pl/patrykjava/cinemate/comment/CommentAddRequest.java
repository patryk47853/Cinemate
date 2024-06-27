package pl.patrykjava.cinemate.comment;

public record CommentAddRequest(String content, Long movieId, Long memberId) {
}
