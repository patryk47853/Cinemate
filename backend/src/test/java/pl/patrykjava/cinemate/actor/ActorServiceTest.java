package pl.patrykjava.cinemate.actor;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.patrykjava.cinemate.director.Director;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.movie.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    private ActorService actorService;
    private final Faker FAKER = new Faker();

    @Mock
    ActorDao actorDao;

    @BeforeEach
    void setUp() {
        actorService = new ActorService(actorDao);
    }

    @Test
    void getActorById() {
        //Given
        Long id = 1L;

        Actor actor = new Actor(id, "firstName", "lastName", "country", new ArrayList<>());

        when(actorDao.selectActorById(id)).thenReturn(Optional.of(actor));

        //When
        var actual = actorService.getActorById(id);

        //Then
        assertThat(actual).isEqualTo(actor);
    }

    @Test
    void willThrowWhenGetActorByIdNotPresent() {
        //Given
        Long id = 1L;

        when(actorDao.selectActorById(id)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> actorService.getActorById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No actor with ID: " + id + " has been found.");
    }

    @Test
    void getActorsByLastName() {
        //Given
        String lastName = FAKER.name().lastName();

        Actor actor = new Actor("firstName", lastName, "country");

        when(actorDao.selectActorsByLastName(lastName)).thenReturn(Optional.of(List.of(actor)));

        //When
        var actual = actorService.getActorsByLastName(lastName);

        //Then
        assertThat(actual).contains(actor);
    }

    @Test
    void willThrowGetActorsByLastNameWhenLastNameNotPresent() {
        //Given
        String lastName = FAKER.name().lastName();

        when(actorDao.selectActorsByLastName(lastName)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> actorService.getActorsByLastName(lastName))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No actor with last name: " + lastName + " has been found.");
    }

    @Test
    void findAllActors() {
        //Given
        actorService.findAllActors();

        //Then
        verify(actorDao).selectAllActors();
    }

    @Test
    void addActor() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        ActorAddRequest request = new ActorAddRequest("firstName", lastName, "country");

        //When
        actorService.addActor(request);

        //Then
        ArgumentCaptor<Actor> actorArgumentCaptor = ArgumentCaptor.forClass(Actor.class);

        verify(actorDao).insertActor(actorArgumentCaptor.capture());

        Actor actual = actorArgumentCaptor.getValue();

        assertThat(actual.getId()).isNull();
        assertThat(actual.getFirstName()).isEqualTo(request.firstName());
        assertThat(actual.getLastName()).isEqualTo(request.lastName());
        assertThat(actual.getCountry()).isEqualTo(request.country());
        assertThat(actual.getMovies()).isEmpty();
    }

    @Test
    void willThrowAddActorWhenExistsActorWithFullName() {
        //Given
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String country = FAKER.country().name();

        String fullName = firstName + " " + lastName;

        when(actorDao.existsActorWithFullNameAndIsFrom(firstName, lastName, country)).thenReturn(true);

        ActorAddRequest request = new ActorAddRequest(firstName, lastName, country);

        //When
        assertThatThrownBy(() -> actorService.addActor(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Actor: " + fullName + " born in " + country + " already exists.");

        //Then
        verify(actorDao, never()).insertActor(any());
    }

    @Test
    void deleteActorById() {
        //Given
        Long id = 1L;

        when(actorDao.existsActorWithId(id)).thenReturn(true);

        //When
        actorService.deleteActorById(id);

        //Then
        verify(actorDao).deleteActorById(id);
    }

    @Test
    void willThrowDeleteActorByIdWhenIdNotPresent() {
        //Given
        Long id = 1L;

        when(actorDao.existsActorWithId(id)).thenReturn(false);

        //When
        assertThatThrownBy(() -> actorService.deleteActorById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Actor with ID: " + id + " not found.");

        //Then
        verify(actorDao, never()).deleteActorById(any());
    }

    @Test
    void canUpdateOnlyActorFirstName() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String country = FAKER.country().name() + UUID.randomUUID();

        Actor actor = new Actor(id, firstName, lastName, country, new ArrayList<>());

        when(actorDao.selectActorById(id)).thenReturn(Optional.of(actor));

        String firstNameUpdated = "firstName";
        ActorUpdateRequest request = new ActorUpdateRequest(firstNameUpdated, null, null, null);

        when(actorDao.existsActorWithFullNameAndIsFrom(firstNameUpdated, lastName, country)).thenReturn(false);

        //When
        actorService.updateActor(id, request);

        //Then
        ArgumentCaptor<Actor> actorArgumentCaptor = ArgumentCaptor.forClass(Actor.class);

        verify(actorDao).updateActor(actorArgumentCaptor.capture());

        Actor actual = actorArgumentCaptor.getValue();

        assertThat(actual.getFirstName()).isEqualTo(request.firstName());
        assertThat(actual.getLastName()).isEqualTo(actor.getLastName());
        assertThat(actual.getCountry()).isEqualTo(actor.getCountry());
        assertThat(actual.getMovies()).isEqualTo(actor.getMovies());
    }

    @Test
    void canUpdateOnlyActorLastName() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String country = FAKER.country().name() + UUID.randomUUID();

        Actor actor = new Actor(id, firstName, lastName, country, new ArrayList<>());

        when(actorDao.selectActorById(id)).thenReturn(Optional.of(actor));

        String lastNameUpdated = "lastName";
        ActorUpdateRequest request = new ActorUpdateRequest(null, lastNameUpdated, null, null);

        when(actorDao.existsActorWithFullNameAndIsFrom(firstName, lastNameUpdated, country)).thenReturn(false);

        //When
        actorService.updateActor(id, request);

        //Then
        ArgumentCaptor<Actor> actorArgumentCaptor = ArgumentCaptor.forClass(Actor.class);

        verify(actorDao).updateActor(actorArgumentCaptor.capture());

        Actor actual = actorArgumentCaptor.getValue();

        assertThat(actual.getFirstName()).isEqualTo(actor.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(request.lastName());
        assertThat(actual.getCountry()).isEqualTo(actor.getCountry());
        assertThat(actual.getMovies()).isEqualTo(actor.getMovies());
    }

    @Test
    void canUpdateOnlyActorCountry() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String country = FAKER.country().name() + UUID.randomUUID();

        Actor actor = new Actor(id, firstName, lastName, country, new ArrayList<>());

        when(actorDao.selectActorById(id)).thenReturn(Optional.of(actor));

        String countryUpdated = "Spain";
        ActorUpdateRequest request = new ActorUpdateRequest(null, null, countryUpdated, null);

        //When
        actorService.updateActor(id, request);

        //Then
        ArgumentCaptor<Actor> actorArgumentCaptor = ArgumentCaptor.forClass(Actor.class);

        verify(actorDao).updateActor(actorArgumentCaptor.capture());

        Actor actual = actorArgumentCaptor.getValue();

        assertThat(actual.getFirstName()).isEqualTo(actor.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(actor.getLastName());
        assertThat(actual.getCountry()).isEqualTo(request.country());
        assertThat(actual.getMovies()).isEqualTo(actor.getMovies());
    }

    @Test
    void willThrowWhenUpdateActorFullNameWithExistingOne() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Actor actor = new Actor(id, firstName, lastName, "USA", new ArrayList<>());

        when(actorDao.selectActorById(id)).thenReturn(Optional.of(actor));

        String lastNameUpdated = FAKER.name().lastName() + UUID.randomUUID();
        when(actorDao.existsActorWithFullNameAndIsFrom(firstName, lastNameUpdated, actor.getCountry())).thenReturn(true);

        ActorUpdateRequest request = new ActorUpdateRequest(null, lastNameUpdated, null, null);

        //When
        assertThatThrownBy(() -> actorService.updateActor(id, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Actor: " + firstName + " " + lastNameUpdated + " born in " + actor.getCountry() + " already exists.");

        //Then
        verify(actorDao, never()).updateActor(any());
    }

    @Test
    void canUpdateOnlyActorMovieList() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String country = FAKER.country().name() + UUID.randomUUID();

        Actor actor = new Actor(id, firstName, lastName, country, new ArrayList<>());

        when(actorDao.selectActorById(id)).thenReturn(Optional.of(actor));

        Movie movieToAdd = new Movie(9999L, "Random", new Director());

        List<Movie> moviesToAdd = List.of(movieToAdd);
        ActorUpdateRequest request = new ActorUpdateRequest(null, null, null, moviesToAdd);

        //When
        actorService.updateActor(id, request);

        //Then
        ArgumentCaptor<Actor> actorArgumentCaptor = ArgumentCaptor.forClass(Actor.class);

        verify(actorDao).updateActor(actorArgumentCaptor.capture());

        Actor actual = actorArgumentCaptor.getValue();

        assertThat(actual.getFirstName()).isEqualTo(actor.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(actor.getLastName());
        assertThat(actual.getCountry()).isEqualTo(actor.getCountry());
        assertThat(actual.getMovies()).contains(movieToAdd);
    }

    @Test
    void canUpdateAllActorProperties() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String country = FAKER.country().name() + UUID.randomUUID();

        Actor actor = new Actor(id, firstName, lastName, country, new ArrayList<>());

        when(actorDao.selectActorById(id)).thenReturn(Optional.of(actor));

        Movie movieToAdd = new Movie(9999L, "Random", new Director());

        String firstNameUpdated = "firstName";
        String lastNameUpdated = "lastName";
        String countryUpdated = "Spain";
        List<Movie> moviesToAdd = List.of(movieToAdd);
        ActorUpdateRequest request = new ActorUpdateRequest(firstNameUpdated, lastNameUpdated, countryUpdated, moviesToAdd);

        //When
        actorService.updateActor(id, request);

        //Then
        ArgumentCaptor<Actor> actorArgumentCaptor = ArgumentCaptor.forClass(Actor.class);

        verify(actorDao).updateActor(actorArgumentCaptor.capture());

        Actor actual = actorArgumentCaptor.getValue();

        assertThat(actual.getFirstName()).isEqualTo(request.firstName());
        assertThat(actual.getLastName()).isEqualTo(request.lastName());
        assertThat(actual.getCountry()).isEqualTo(request.country());
        assertThat(actual.getMovies()).contains(movieToAdd);
    }

    @Test
    void willThrowWhenUpdateNonExistingActor() {
        //Given
        Long id = 1L;

        ActorUpdateRequest request = new ActorUpdateRequest("firstName", null, null, null);

        //When
        assertThatThrownBy(() -> actorService.updateActor(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No actor with such ID: " + id + " has been found.");

        //Then
        verify(actorDao, never()).updateActor(any());
    }

    @Test
    void willThrowWhenUpdateActorWithoutChanges() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String country = FAKER.country().name() + UUID.randomUUID();

        Actor actor = new Actor(id, firstName, lastName, country, new ArrayList<>());

        when(actorDao.selectActorById(id)).thenReturn(Optional.of(actor));

        ActorUpdateRequest request = new ActorUpdateRequest(null, null, null, null);

        //When
        assertThatThrownBy(() -> actorService.updateActor(id, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No changes were made.");

        //Then
        verify(actorDao, never()).updateActor(any());
    }
}