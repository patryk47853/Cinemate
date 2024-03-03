package pl.patrykjava.cinemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykjava.cinemate.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
