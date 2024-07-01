package pl.patrykjava.cinemate.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.movie.MovieDtoMapper;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MemberDtoMapper implements Function<Member, MemberDto> {

    private final MovieDtoMapper movieDtoMapper;

    public MemberDtoMapper(MovieDtoMapper movieDtoMapper) {
        this.movieDtoMapper = movieDtoMapper;
    }

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
                        .stream()
                        .map(movieDtoMapper)
                        .collect(Collectors.toList())
        );
    }
}
