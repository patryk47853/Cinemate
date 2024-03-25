package pl.patrykjava.cinemate.comment;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.patrykjava.cinemate.director.Director;
import pl.patrykjava.cinemate.director.DirectorRepository;
import pl.patrykjava.cinemate.member.Member;
import pl.patrykjava.cinemate.member.MemberRepository;
import pl.patrykjava.cinemate.movie.Movie;
import pl.patrykjava.cinemate.movie.MovieRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest extends CommentAbstractTestcontainers {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    private final Faker FAKER = new Faker();

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        memberRepository.deleteAll();
        movieRepository.deleteAll();
        directorRepository.deleteAll();
    }

    @Test
    void existsCommentById() {
        //Given
        String content = "Test";

        Comment comment = new Comment(1L, content);

        commentRepository.save(comment);

        Long id = commentRepository.findAll()
                .stream()
                .filter(c -> c.getContent().equals(content))
                .map(Comment::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = commentRepository.existsCommentById(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCommentByIdFailsWhenIdNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = commentRepository.existsCommentById(id);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsCommentByMemberId() {
        //Given
        String username = FAKER.name().username();

        Member member = new Member(username, "username@test.com", "password");
        memberRepository.save(member);

        Director director = new Director("Christopher", "Nolan");
        directorRepository.save(director);

        Movie movie = new Movie("Interstellar", director);
        movieRepository.save(movie);

        Comment comment = new Comment("Test", member, movie);
        commentRepository.save(comment);

        Long memberId = memberRepository.findAll()
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = commentRepository.existsCommentByMemberId(memberId);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCommentByMemberIdFailsWhenIdNotPresent() {
        //Given
        Long memberId = -1L;

        //When
        var actual = commentRepository.existsCommentByMemberId(memberId);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsCommentByMovieId() {
        //Given
        String title = "Interstellar";

        Member member = new Member("username", "username@test.com", "password");
        memberRepository.save(member);

        Director director = new Director("Christopher", "Nolan");
        directorRepository.save(director);

        Movie movie = new Movie(title, director);
        movieRepository.save(movie);

        Comment comment = new Comment("Test", member, movie);
        commentRepository.save(comment);

        Long movieId = movieRepository.findAll()
                .stream()
                .filter(m -> m.getTitle().equals(title))
                .map(Movie::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = commentRepository.existsCommentByMovieId(movieId);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCommentByMovieIdFailsWhenIdNotPresent() {
        //Given
        Long movieId = -1L;

        //When
        var actual = commentRepository.existsCommentByMemberId(movieId);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void findAllByMemberId() {
        //Given
        String username = FAKER.name().username();

        Member member = new Member(username, "username@test.com", "password");
        memberRepository.save(member);

        Director director = new Director("Christopher", "Nolan");
        directorRepository.save(director);

        Movie movie = new Movie("Interstellar", director);
        movieRepository.save(movie);

        Comment comment = new Comment("Test", member, movie);
        Comment comment2 = new Comment("Test", member, movie);
        Comment comment3 = new Comment("Test", member, movie);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        Long memberId = memberRepository.findAll()
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actualCommentsOptionalList = commentRepository.findAllByMemberId(memberId);

        assertThat(actualCommentsOptionalList).isPresent();

        var actualCommentsList = actualCommentsOptionalList.get();

        //Then
        assertThat(actualCommentsList).contains(comment);
        assertThat(actualCommentsList).contains(comment2);
        assertThat(actualCommentsList).contains(comment3);
    }

    @Test
    void findAllByMemberIdFailsWhenMemberNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = commentRepository.findAllByMemberId(id);

        //Then
        assertThat(actual).isPresent(); // Empty list
        assertThat(actual.get()).isEmpty();
    }

    @Test
    void findAllByMovieId() {
        //Given
        String title = "Interstellar";

        Member member = new Member("username", "username@test.com", "password");
        memberRepository.save(member);

        Director director = new Director("Christopher", "Nolan");
        directorRepository.save(director);

        Movie movie = new Movie(1L,  title, director);
        movieRepository.save(movie);

        Comment comment = new Comment("Test", member, movie);
        Comment comment2 = new Comment("Test", member, movie);
        Comment comment3 = new Comment("Test", member, movie);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        Long movieId = movieRepository.findAll()
                .stream()
                .filter(m -> m.getTitle().equals(title))
                .map(Movie::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actualCommentsOptionalList = commentRepository.findAllByMovieId(movieId);

        assertThat(actualCommentsOptionalList).isPresent();

        var actualCommentsList = actualCommentsOptionalList.get();

        //Then
        assertThat(actualCommentsList).contains(comment);
        assertThat(actualCommentsList).contains(comment2);
        assertThat(actualCommentsList).contains(comment3);
    }

    @Test
    void findAllByMovieIdFailsWhenMovieNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = commentRepository.findAllByMovieId(id);

        //Then
        assertThat(actual).isPresent(); // Empty list
        assertThat(actual.get()).isEmpty();
    }

    @Test
    void deleteAllByMemberId() {
        //Given
        String username = FAKER.name().username();

        Member member = new Member(username, "username@test.com", "password");
        memberRepository.save(member);

        Director director = new Director("Christopher", "Nolan");
        directorRepository.save(director);

        Movie movie = new Movie("Interstellar", director);
        movieRepository.save(movie);

        Comment comment = new Comment("Test", member, movie);
        Comment comment2 = new Comment("Test", member, movie);
        Comment comment3 = new Comment("Test", member, movie);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        Long memberId = memberRepository.findAll()
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        var actualCommentsOptionalList = commentRepository.findAllByMemberId(memberId);

        assertThat(actualCommentsOptionalList).isPresent();

        var actualCommentsList = actualCommentsOptionalList.get();

        assertThat(actualCommentsList).contains(comment);
        assertThat(actualCommentsList).contains(comment2);
        assertThat(actualCommentsList).contains(comment3);

        //When
        commentRepository.deleteAllByMemberId(memberId);

        var actual = commentRepository.findAllByMemberId(memberId);

        //Then
        assertThat(actual).isPresent(); // Empty list
        assertThat(actual.get()).isEmpty();
    }

    @Test
    void deleteAllByMovieId() {
        //Given
        String title = "Interstellar";

        Member member = new Member("username", "username@test.com", "password");
        memberRepository.save(member);

        Director director = new Director("Christopher", "Nolan");
        directorRepository.save(director);

        Movie movie = new Movie(title, director);
        movieRepository.save(movie);

        Comment comment = new Comment("Test", member, movie);
        Comment comment2 = new Comment("Test", member, movie);
        Comment comment3 = new Comment("Test", member, movie);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        Long movieId = movieRepository.findAll()
                .stream()
                .filter(m -> m.getTitle().equals(title))
                .map(Movie::getId)
                .findFirst()
                .orElseThrow();

        var actualCommentsOptionalList = commentRepository.findAllByMovieId(movieId);

        assertThat(actualCommentsOptionalList).isPresent();

        var actualCommentsList = actualCommentsOptionalList.get();

        assertThat(actualCommentsList).contains(comment);
        assertThat(actualCommentsList).contains(comment2);
        assertThat(actualCommentsList).contains(comment3);

        //When
        commentRepository.deleteAllByMovieId(movieId);

        var actual = commentRepository.findAllByMovieId(movieId);

        //Then
        assertThat(actual).isPresent(); // Empty list
        assertThat(actual.get()).isEmpty();
    }
}