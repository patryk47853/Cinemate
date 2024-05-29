package pl.patrykjava.cinemate.actor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ActorRepositoryTest extends ActorAbstractTestcontainers {

    @Autowired
    ActorRepository actorRepository;

    @BeforeEach
    void setUp() {
        actorRepository.deleteAll();
    }

    @Test
    void existsActorByLastName() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Actor actor = new Actor("firstName", lastName);

        actorRepository.save(actor);

        //When
        var actual = actorRepository.existsActorByLastName(lastName);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsActorByLastNameFailWhenActorWithLastNameNotPresent() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        //When
        var actual = actorRepository.existsActorByLastName(lastName);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsActorByFirstNameAndLastName() {
        //Given
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Actor actor = new Actor(firstName, lastName);

        actorRepository.save(actor);

        //When
        var actual = actorRepository.existsActorByFirstNameAndLastName(firstName, lastName);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsActorByFirstNameAndLastNameFailWhenActorWithFullNameNotPresent() {
        //Given
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String country = FAKER.country().name();

        //When
        var actual = actorRepository.existsActorByFirstNameAndLastName(firstName, lastName);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void findActorsByLastName() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Actor actor1 = new Actor("actor1", lastName);
        Actor actor2 = new Actor("actor2", lastName);

        actorRepository.save(actor1);
        actorRepository.save(actor2);

        //When
        var actualOptional = actorRepository.findActorsByLastName(lastName);

        //Then
        assertThat(actualOptional).isPresent();

        var actualList = actualOptional.get();
        assertThat(actualList).isNotEmpty();
        assertThat(actualList).contains(actor1, actor2);
        assertThat(actualList).hasSize(2);
    }

    @Test
    void findActorsByLastNameWillFailWhenActorsWithLastNameNotPresent() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        //When
        var actual = actorRepository.findActorsByLastName(lastName).get();

        //Then
        assertThat(actual).hasSize(0);
    }

    @Test
    void existsActorById() {
        //Given
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String country = FAKER.country().name();

        Actor actor = new Actor(firstName, lastName);

        actorRepository.save(actor);

        Long id = actorRepository.findAll()
                .stream()
                .filter(a -> a.getFirstName().equals(firstName) && a.getLastName().equals(lastName))
                .map(Actor::getId)
                .findFirst()
                .orElseThrow();

        //When

        var actual = actorRepository.existsActorById(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsActorByIdWillFailWhenIdNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = actorRepository.existsActorById(id);

        //Then
        assertThat(actual).isFalse();
    }
}