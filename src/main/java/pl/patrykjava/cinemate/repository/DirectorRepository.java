package pl.patrykjava.cinemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykjava.cinemate.entity.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
}
