package pl.patrykjava.cinemate.director;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    boolean existsDirectorByLastName(String lastName);
    boolean existsDirectorByFirstNameAndLastName(String firstName, String lastName);
    Optional<List<Director>> findDirectorsByLastName(String lastName);
    Optional<Director> findDirectorByFirstNameAndLastName(String firstName, String lastName);
    boolean existsDirectorById(Long id);

}
