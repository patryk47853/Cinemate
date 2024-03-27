package pl.patrykjava.cinemate.member;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MemberDtoMapper implements Function<Member, MemberDto> {

    @Override
    public MemberDto apply(Member member) {
        return new MemberDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getComments(),
                member.getFavoriteMovies()
        );
    }
}
