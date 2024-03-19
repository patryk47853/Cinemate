package pl.patrykjava.cinemate.actor;

import org.springframework.stereotype.Repository;
import pl.patrykjava.cinemate.actor.Actor;

import java.util.List;
import java.util.Optional;

@Repository
public class ActorJPADataAccessService implements ActorDao {
    
    private final ActorRepository actorRepository;

    public ActorJPADataAccessService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public Optional<Actor> selectActorById(Long id) {
        return actorRepository.findById(id);
    }

    @Override
    public Optional<List<Actor>> selectActorsByLastName(String lastName) {
        return actorRepository.findActorsByLastName(lastName);
    }

    @Override
    public boolean existsActorWithLastName(String lastName) {
        return actorRepository.existsActorByLastName(lastName);
    }

    @Override
    public boolean existsActorWithFullName(String firstName, String lastName) {
        return actorRepository.existsActorByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public boolean existsActorWithId(Long id) {
        return actorRepository.existsActorById(id);
    }

    @Override
    public List<Actor> selectAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public void insertActor(Actor actor) {
        actorRepository.save(actor);
    }

    @Override
    public void deleteActorById(Long id) {
        actorRepository.deleteById(id);
    }

    @Override
    public void updateActor(Actor actor) {
        actorRepository.save(actor);
    }
}
