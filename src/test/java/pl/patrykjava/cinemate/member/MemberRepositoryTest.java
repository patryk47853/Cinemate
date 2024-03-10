package pl.patrykjava.cinemate.member;

import org.apache.catalina.core.ApplicationContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.patrykjava.cinemate.AbstractTestcontainers;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest extends AbstractTestcontainers {

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
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void existsMemberByEmailFailsWhenEmailNotPresent() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        //When
        var actual = memberRepository.existsMemberByEmail(email);

        //Then
        Assertions.assertThat(actual).isFalse();
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
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void existsMemberByIdFailsWhenIdNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = memberRepository.existsMemberById(id);

        //Then
        Assertions.assertThat(actual).isFalse();
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
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void existsMemberByUsernameFailsWhenUsernameNotPresent() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        //When
        var actual = memberRepository.existsMemberByUsername(username);

        //Then
        Assertions.assertThat(actual).isFalse();
    }
}