package pl.patrykjava.cinemate.service;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.entity.Actor;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.repository.ActorRepository;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Actor findById(Long id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No actor with ID: " + id + " has been found."));
    }
}
