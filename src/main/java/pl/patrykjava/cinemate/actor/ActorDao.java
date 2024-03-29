package pl.patrykjava.cinemate.actor;

import java.util.List;
import java.util.Optional;

public interface ActorDao {

    Optional<Actor> selectActorById(Long id);
    Optional<List<Actor>> selectActorsByLastName(String lastName);
    List<Actor> selectAllActors();
    boolean existsActorWithLastName(String lastName);
    boolean existsActorWithFullNameAndIsFrom(String firstName, String lastName, String country);
    boolean existsActorWithId(Long id);
    void insertActor(Actor actor);
    void deleteActorById(Long id);
    void updateActor(Actor actor);
}
