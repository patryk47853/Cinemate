package pl.patrykjava.cinemate.member;

public record MemberUpdateRequest(
        String username,
        String email,
        String password
) {
}
