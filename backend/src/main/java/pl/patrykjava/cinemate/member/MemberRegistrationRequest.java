package pl.patrykjava.cinemate.member;

public record MemberRegistrationRequest(
        String username,
        String email,
        String password
) {
}
