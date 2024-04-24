package pl.patrykjava.cinemate.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.jwt.JWTUtil;
import pl.patrykjava.cinemate.member.Member;
import pl.patrykjava.cinemate.member.MemberDto;
import pl.patrykjava.cinemate.member.MemberDtoMapper;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final MemberDtoMapper memberDtoMapper;
    private final JWTUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, MemberDtoMapper memberDtoMapper, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.memberDtoMapper = memberDtoMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        Member principal = (Member) authentication.getPrincipal();
        MemberDto memberDto = memberDtoMapper.apply(principal);
        String token = jwtUtil.issueToken(memberDto.username(), memberDto.roles());

        return new AuthenticationResponse(token, memberDto);
    }
}
