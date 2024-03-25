package pl.patrykjava.cinemate.director;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class DirectorServiceTest {

    private DirectorService directorService;
    private final Faker FAKER = new Faker();
    @Mock
    private DirectorDao directorDao;

    @BeforeEach
    void setUp() {
        directorService = new DirectorService(directorDao);
    }

    @Test
    void getDirectorById() {
        //Given
        Long id = 1L;

        Director director = new Director(id, "firstName", "lastName", new ArrayList<>());

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.of(director));

        //When
        var actual = directorService.getDirectorById(id);

        //Then
        assertThat(actual).isEqualTo(director);
    }

    @Test
    void willThrowWhenGetDirectorByIdNotPresent() {
        //Given
        Long id = 1L;

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> directorService.getDirectorById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No director with ID: " + id + " has been found.");
    }

    @Test
    void getDirectorsByLastName() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director("firstName", lastName);

        when(directorDao.selectDirectorsByLastName(lastName)).thenReturn(Optional.of(List.of(director)));

        //When
        var actual = directorService.getDirectorsByLastName(lastName);

        //Then
        assertThat(actual).contains(director);
    }

    @Test
    void willThrowWhenGetDirectorsByLastNameNotPresent() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        when(directorDao.selectDirectorsByLastName(lastName)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> directorService.getDirectorsByLastName(lastName))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No director with last name: " + lastName + " has been found.");
    }

    @Test
    void findAllDirectors() {
        //When
        directorService.findAllDirectors();

        //Then
        verify(directorDao).selectAllDirectors();
    }

    @Test
    void addDirector() {
        //Given
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        DirectorAddRequest request = new DirectorAddRequest("firstName", lastName);

        //When
        directorService.addDirector(request);

        //Then
        ArgumentCaptor<Director> directorArgumentCaptor = ArgumentCaptor.forClass(Director.class);

        verify(directorDao).insertDirector(directorArgumentCaptor.capture());

        Director actual = directorArgumentCaptor.getValue();

        assertThat(actual.getId()).isNull();
        assertThat(actual.getFirstName()).isEqualTo(request.firstName());
        assertThat(actual.getLastName()).isEqualTo(request.lastName());
    }

    @Test
    void willThrowWhenAddDirectorWithExistingFullName() {
        //Given
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();
        String fullName = firstName + " " + lastName;

        when(directorDao.existsDirectorWithFullName(firstName, lastName)).thenReturn(true);

        DirectorAddRequest request = new DirectorAddRequest(firstName, lastName);

        //When
        assertThatThrownBy(() -> directorService.addDirector(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Director: " + fullName + " already exists.");

        //Then
        verify(directorDao, never()).insertDirector(any());
    }

    @Test
    void deleteDirectorById() {
        //Given
        Long id = 1L;

        when(directorDao.existsDirectorWithId(id)).thenReturn(true);

        //When
        directorService.deleteDirectorById(id);

        //Then
        verify(directorDao).deleteDirectorById(id);
    }

    @Test
    void willThrowWhenDeleteDirectorByIdDoesNotExists() {
        //Given
        Long id = 1L;

        when(directorDao.existsDirectorWithId(id)).thenReturn(false);

        //When
        assertThatThrownBy(() -> directorService.deleteDirectorById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Director with ID: " + id + " not found.");

        //Then
        verify(directorDao, never()).deleteDirectorById(any());
    }

    @Test
    void canUpdateOnlyDirectorFirstName() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director(id, firstName, lastName, new ArrayList<>());

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.of(director));

        String firstNameUpdated = FAKER.name().firstName() + UUID.randomUUID();
        DirectorUpdateRequest request = new DirectorUpdateRequest(firstNameUpdated, null, null);

        when(directorDao.existsDirectorWithFullName(firstNameUpdated, lastName)).thenReturn(false);

        //When
        directorService.updateDirector(id, request);

        //Then
        ArgumentCaptor<Director> directorArgumentCaptor = ArgumentCaptor.forClass(Director.class);

        verify(directorDao).updateDirector(directorArgumentCaptor.capture());
        Director actual = directorArgumentCaptor.getValue();

        assertThat(actual.getFirstName()).isEqualTo(request.firstName());
        assertThat(actual.getLastName()).isEqualTo(director.getLastName());
    }

    @Test
    void canUpdateOnlyDirectorLastName() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director(id, firstName, lastName, new ArrayList<>());

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.of(director));

        String lastNameUpdated = FAKER.name().lastName() + UUID.randomUUID();
        DirectorUpdateRequest request = new DirectorUpdateRequest(null, lastNameUpdated, null);

        when(directorDao.existsDirectorWithFullName(firstName, lastNameUpdated)).thenReturn(false);

        //When
        directorService.updateDirector(id, request);

        //Then
        ArgumentCaptor<Director> directorArgumentCaptor = ArgumentCaptor.forClass(Director.class);

        verify(directorDao).updateDirector(directorArgumentCaptor.capture());
        Director actual = directorArgumentCaptor.getValue();

        assertThat(actual.getFirstName()).isEqualTo(director.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(request.lastName());
    }

    @Test
    void willThrowWhenUpdateDirectorLastNameWithExistingDirectorFullName() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director(id, firstName, lastName, new ArrayList<>());

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.of(director));

        String lastNameUpdated = FAKER.name().lastName() + UUID.randomUUID();
        when(directorDao.existsDirectorWithFullName(firstName, lastNameUpdated)).thenReturn(true);

        DirectorUpdateRequest request = new DirectorUpdateRequest(null, lastNameUpdated, null);

        //When
        assertThatThrownBy(() -> directorService.updateDirector(id, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Director: " + firstName + " " + lastNameUpdated + " already exists.");

        //Then
        verify(directorDao, never()).updateDirector(any());
    }

    @Test
    void canUpdateOnlyDirectorMovieList() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director(id, firstName, lastName, new ArrayList<>());

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.of(director));

        Movie movieToAdd = new Movie(id, "Inception", director);
        DirectorUpdateRequest request = new DirectorUpdateRequest(null, null, List.of(movieToAdd));

        //When
        directorService.updateDirector(id, request);

        //Then
        ArgumentCaptor<Director> directorArgumentCaptor = ArgumentCaptor.forClass(Director.class);

        verify(directorDao).updateDirector(directorArgumentCaptor.capture());
        Director actual = directorArgumentCaptor.getValue();

        assertThat(actual.getFirstName()).isEqualTo(director.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(director.getLastName());
        assertThat(actual.getMovies()).contains(movieToAdd);
    }

    @Test
    void canUpdateAllPropertiesOfDirector() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director(id, firstName, lastName, new ArrayList<>());

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.of(director));

        String firstNameUpdated = FAKER.name().firstName() + UUID.randomUUID();
        String lastNameUpdated = FAKER.name().lastName() + UUID.randomUUID();
        Movie movieToAdd = new Movie(id, "Inception", director);
        DirectorUpdateRequest request = new DirectorUpdateRequest(firstNameUpdated, lastNameUpdated, List.of(movieToAdd));

        when(directorDao.existsDirectorWithFullName(firstNameUpdated, lastName)).thenReturn(false);

        //When
        directorService.updateDirector(id, request);

        //Then
        ArgumentCaptor<Director> directorArgumentCaptor = ArgumentCaptor.forClass(Director.class);

        verify(directorDao).updateDirector(directorArgumentCaptor.capture());
        Director actual = directorArgumentCaptor.getValue();

        assertThat(actual.getFirstName()).isEqualTo(request.firstName());
        assertThat(actual.getLastName()).isEqualTo(request.lastName());
        assertThat(actual.getMovies()).contains(movieToAdd);
    }

    // TODO: 18/03/2024 REFACTOR
    @Test
    void willThrowWhenUpdateDirectorMovieListWithExistingMovieDirectedByThisDirector() {
        //Given
        Long id = 1L;
        Director director = new Director("Christopher", "Nolan");

        Movie movie = new Movie(1L, "Inception", director);

        director.setMovies(List.of(movie));

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.of(director));

        Movie movieToAdd = new Movie(2L, "Inception", director);
        DirectorUpdateRequest request = new DirectorUpdateRequest(null, null, List.of(movieToAdd));

        //When
        assertThatThrownBy(() -> directorService.updateDirector(id, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Movie " + movieToAdd.getTitle() +
                        " is already assigned to this director.");

        //Then
        verify(directorDao, never()).updateDirector(any());
    }

    @Test
    void willThrowWhenUpdateNonExistingDirector() {
        //Given
        Long id = -1L;

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.empty());
        DirectorUpdateRequest request = new DirectorUpdateRequest(null, null, new ArrayList<>());
        //When
        assertThatThrownBy(() -> directorService.updateDirector(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No director with such ID: " + id + " has been found.");

        //Then
        verify(directorDao, never()).updateDirector(any());
    }

    @Test
    void willThrowWhenUpdateDirectorWithoutChanges() {
        //Given
        Long id = 1L;
        String firstName = FAKER.name().firstName() + UUID.randomUUID();
        String lastName = FAKER.name().lastName() + UUID.randomUUID();

        Director director = new Director(id, firstName, lastName, new ArrayList<>());

        when(directorDao.selectDirectorById(id)).thenReturn(Optional.of(director));

        DirectorUpdateRequest request = new DirectorUpdateRequest(null, null, null);

        //When
        assertThatThrownBy(() -> directorService.updateDirector(id, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No changes were made.");

        //Then
        verify(directorDao, never()).updateDirector(any());
    }
}