package pl.patrykjava.cinemate.actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykjava.cinemate.actor.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
}
