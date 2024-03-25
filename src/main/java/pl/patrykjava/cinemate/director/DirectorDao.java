package pl.patrykjava.cinemate.director;

import java.util.List;
import java.util.Optional;

public interface DirectorDao {
    Optional<Director> selectDirectorById(Long id);
    Optional<List<Director>> selectDirectorsByLastName(String lastName);
    List<Director> selectAllDirectors();
    boolean existsDirectorWithLastName(String lastName);
    boolean existsDirectorWithFullName(String firstName, String lastName);
    boolean existsDirectorWithId(Long id);
    void insertDirector(Director director);
    void deleteDirectorById(Long id);
    void updateDirector(Director director);
}
