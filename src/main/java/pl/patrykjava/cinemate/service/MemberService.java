package pl.patrykjava.cinemate.service;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.entity.Actor;
import pl.patrykjava.cinemate.entity.Member;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.repository.ActorRepository;
import pl.patrykjava.cinemate.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No actor with ID: " + id + " has been found."));
    }
}
