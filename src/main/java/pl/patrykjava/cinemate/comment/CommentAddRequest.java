package pl.patrykjava.cinemate.comment;

import pl.patrykjava.cinemate.member.Member;

public record CommentAddRequest(String content, Member member) {
}
