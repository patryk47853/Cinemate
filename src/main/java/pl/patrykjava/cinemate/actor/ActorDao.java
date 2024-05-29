package pl.patrykjava.cinemate.actor;

import java.util.List;
import java.util.Optional;

public interface ActorDao {

    Optional<Actor> selectActorById(Long id);
    Optional<Actor> selectActorByFullName(String firstName, String lastName);
    Optional<List<Actor>> selectActorsByLastName(String lastName);
    List<Actor> selectAllActors();
    boolean existsActorWithLastName(String lastName);
    boolean existsActorWithFullName(String firstName, String lastName);
    boolean existsActorWithId(Long id);
    void insertActor(Actor actor);
    void deleteActorById(Long id);
    void updateActor(Actor actor);
}
