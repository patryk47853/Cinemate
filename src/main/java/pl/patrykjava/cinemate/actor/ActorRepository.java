package pl.patrykjava.cinemate.actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    boolean existsActorByLastName(String lastName);
    boolean existsActorByFirstNameAndLastName(String firstName, String lastName);
    Optional<List<Actor>> findActorsByLastName(String lastName);
    Optional<Actor> findActorByFirstNameAndLastName(String firstName, String lastName);
    boolean existsActorById(Long id);
}
