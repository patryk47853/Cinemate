package pl.patrykjava.cinemate.auth;

import pl.patrykjava.cinemate.member.MemberDto;

public record AuthenticationResponse (
       String token,
       MemberDto memberDto
) {
}
