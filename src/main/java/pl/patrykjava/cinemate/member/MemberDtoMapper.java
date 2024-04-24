package pl.patrykjava.cinemate.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MemberDtoMapper implements Function<Member, MemberDto> {

    @Override
    public MemberDto apply(Member member) {
        return new MemberDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getImgUrl(),
                member.getComments(),
                member.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                member.getFavoriteMovies()
        );
    }
}
