package pl.patrykjava.cinemate.director;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DirectorRepositoryTest extends DirectorAbstractTestcontainers {

    @Autowired
    DirectorRepository directorRepository;

    @BeforeEach
    void setUp() {
        directorRepository.deleteAll();
    }

    @Test
    void existsDirectorByLastName() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director("firstName", lastName);

        directorRepository.save(director);

        //When
        var actual = directorRepository.existsDirectorByLastName(lastName);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsDirectorByLastNameFailWhenDirectorWithLastNameNotPresent() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        //When
        var actual = directorRepository.existsDirectorByLastName(lastName);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsDirectorByFirstNameAndLastName() {
        //Given
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director(firstName, lastName);

        directorRepository.save(director);

        //When
        var actual = directorRepository.existsDirectorByFirstNameAndLastName(firstName, lastName);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsDirectorByFirstNameAndLastNameFailWhenNotPresent() {
        //Given
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        //When
        var actual = directorRepository.existsDirectorByFirstNameAndLastName(firstName, lastName);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void findDirectorsByLastName() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        Director director = new Director("firstName", lastName);
        Director director2 = new Director("firstName2", lastName);

        directorRepository.save(director);
        directorRepository.save(director2);

        //When
        var actualOptional = directorRepository.findDirectorsByLastName(lastName);

        //Then
        assertThat(actualOptional).isPresent();

        var actualList = actualOptional.get();
        assertThat(actualList).isNotEmpty();
        assertThat(actualList).hasSize(2);
        assertThat(actualList).contains(director, director2);
    }

    @Test
    void findDirectorsByLastNameWillFailWhenDirectorsWithLastNameNotPresent() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        //When
        var actual = directorRepository.findDirectorsByLastName(lastName).get();

        //Then
        assertThat(actual).hasSize(0);
    }


    @Test
    void existsDirectorById() {
        //Given
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director(firstName, lastName);

        directorRepository.save(director);

        Long id = directorRepository.findAll()
                .stream()
                .filter(d -> d.getFirstName().equals(firstName) && d.getLastName().equals(lastName))
                .map(Director::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = directorRepository.existsDirectorById(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsDirectorByIdWillFailWhenIdNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = directorRepository.existsDirectorById(id);

        //Then
        assertThat(actual).isFalse();
    }
}