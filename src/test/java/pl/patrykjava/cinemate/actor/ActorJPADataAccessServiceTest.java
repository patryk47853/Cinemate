package pl.patrykjava.cinemate.actor;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ActorJPADataAccessServiceTest {

    private ActorJPADataAccessService actorJPADataAccessService;
    private final Faker FAKER = new Faker();

    @Mock
    ActorRepository actorRepository;

    @BeforeEach
    void setUp() {
        actorJPADataAccessService = new ActorJPADataAccessService(actorRepository);
    }

    @Test
    void selectActorById() {
        //Given
        Long id = 1L;

        //When
        actorJPADataAccessService.selectActorById(id);

        //Then
        verify(actorRepository).findById(id);
    }

    @Test
    void selectActorsByLastName() {
        //Given
        String lastName = FAKER.name().lastName();

        //When
        actorJPADataAccessService.selectActorsByLastName(lastName);

        //Then
        verify(actorRepository).findActorsByLastName(lastName);
    }

    @Test
    void existsActorWithLastName() {
        //Given
        String lastName = FAKER.name().lastName();

        //When
        actorJPADataAccessService.existsActorWithLastName(lastName);

        //Then
        verify(actorRepository).existsActorByLastName(lastName);
    }

    @Test
    void existsActorWithFullName() {
        //Given
        String firstName = FAKER.name().firstName();
        String lastName = FAKER.name().lastName();

        //When
        actorJPADataAccessService.existsActorWithFullName(firstName, lastName);

        //Then
        verify(actorRepository).existsActorByFirstNameAndLastName(firstName, lastName);
    }

    @Test
    void existsActorWithId() {
        //Given
        Long id = 1L;

        //When
        actorJPADataAccessService.existsActorWithId(id);

        //Then
        verify(actorRepository).existsActorById(id);
    }

    @Test
    void selectAllActors() {
        //When
        actorJPADataAccessService.selectAllActors();

        //Then
        verify(actorRepository).findAll();
    }

    @Test
    void insertActor() {
        //Given
        Actor actor = new Actor("firstName", "lastName");

        //When
        actorJPADataAccessService.insertActor(actor);

        //Then
        verify(actorRepository).save(actor);
    }

    @Test
    void deleteActorById() {
        //Given
        Long id = 1L;

        //When
        actorJPADataAccessService.deleteActorById(id);

        //Then
        verify(actorRepository).deleteById(id);
    }

    @Test
    void updateActor() {
        //Given
        Actor actor = new Actor("firstName", "lastName");

        //When
        actorJPADataAccessService.updateActor(actor);

        //Then
        verify(actorRepository).save(actor);
    }
}