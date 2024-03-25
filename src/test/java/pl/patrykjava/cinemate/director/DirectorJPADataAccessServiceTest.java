package pl.patrykjava.cinemate.director;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DirectorJPADataAccessServiceTest {

    private DirectorJPADataAccessService directorJPADataAccessService;
    private final Faker FAKER = new Faker();

    @Mock
    private DirectorRepository directorRepository;

    @BeforeEach
    void setUp() {
        directorJPADataAccessService = new DirectorJPADataAccessService(directorRepository);
    }

    @Test
    void selectDirectorById() {
        //Given
        Long id = 1L;

        //When
        directorJPADataAccessService.selectDirectorById(id);

        //Then
        verify(directorRepository).findById(id);
    }

    @Test
    void selectDirectorsByLastName() {
        //Given
        String lastName = FAKER.name().lastName();

        //When
        directorJPADataAccessService.selectDirectorsByLastName(lastName);

        //Then
        verify(directorRepository).findDirectorsByLastName(lastName);
    }

    @Test
    void existsDirectorWithLastName() {
        //Given
        String lastName = FAKER.name().lastName();

        //When
        directorJPADataAccessService.existsDirectorWithLastName(lastName);

        //Then
        verify(directorRepository).existsDirectorByLastName(lastName);
    }

    @Test
    void existsDirectorWithFullName() {
        //Given
        String firstName = FAKER.name().firstName();
        String lastName = FAKER.name().lastName();

        //When
        directorJPADataAccessService.existsDirectorWithFullName(firstName, lastName);

        //Then
        verify(directorRepository).existsDirectorByFirstNameAndLastName(firstName, lastName);
    }

    @Test
    void existsDirectorWithId() {
        //Given
        Long id = 1L;

        //When
        directorJPADataAccessService.existsDirectorWithId(id);

        //Then
        verify(directorRepository).existsDirectorById(id);
    }

    @Test
    void selectAllDirectors() {
        //When
        directorJPADataAccessService.selectAllDirectors();

        //Then
        verify(directorRepository).findAll();
    }

    @Test
    void insertDirector() {
        //Given
        Director director = new Director("firstName", "lastName");

        //When
        directorJPADataAccessService.insertDirector(director);

        //Then
        verify(directorRepository).save(director);
    }

    @Test
    void deleteDirectorById() {
        //Given
        Long id = 1L;

        //When
        directorJPADataAccessService.deleteDirectorById(id);

        //Then
        verify(directorRepository).deleteById(id);
    }

    @Test
    void updateDirector() {
        //Given
        Director director = new Director("firstName", "lastName");

        //When
        directorJPADataAccessService.updateDirector(director);

        //Then
        verify(directorRepository).save(director);
    }
}