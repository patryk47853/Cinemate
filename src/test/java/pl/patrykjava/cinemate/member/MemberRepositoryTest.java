package pl.patrykjava.cinemate.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest extends MemberAbstractTestcontainers {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    void existsMemberByEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        Member member = new Member(
                FAKER.name().username() + UUID.randomUUID(),
                email,
                "test123"
        );

        memberRepository.save(member);

        //When
        var actual = memberRepository.existsMemberByEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsMemberByEmailFailsWhenEmailNotPresent() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        //When
        var actual = memberRepository.existsMemberByEmail(email);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsMemberById() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        Member member = new Member(
                FAKER.name().username() + UUID.randomUUID(),
                email,
                "test123"
        );

        memberRepository.save(member);

        Long id =  memberRepository.findAll()
                .stream()
                .filter(m -> m.getEmail().equals(email))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = memberRepository.existsMemberById(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsMemberByIdFailsWhenIdNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = memberRepository.existsMemberById(id);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsMemberByUsername() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        Member member = new Member(
                username,
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                "test123"
        );

        memberRepository.save(member);

        //When
        var actual = memberRepository.existsMemberByUsername(username);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsMemberByUsernameFailsWhenUsernameNotPresent() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        //When
        var actual = memberRepository.existsMemberByUsername(username);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void findMemberByEmail() {
        String username = FAKER.name().username() + UUID.randomUUID();
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        Member member = new Member(
                username,
                email,
                "test123"
        );

        memberRepository.save(member);

        //When
        var actualOptional = memberRepository.findMemberByEmail(email);
        assertThat(actualOptional).isPresent();

        var actual = actualOptional.get();

        //Then
        assertThat(actual.getEmail()).isEqualTo(email);
    }
}