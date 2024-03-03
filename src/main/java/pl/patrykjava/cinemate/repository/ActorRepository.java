package pl.patrykjava.cinemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykjava.cinemate.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
}
