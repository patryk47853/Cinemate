package pl.patrykjava.cinemate.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.patrykjava.cinemate.AbstractTestcontainers;

import java.util.UUID;

class MemberJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private MemberJDBCDataAccessService memberJDBCDataAccessService;
    private final MemberRowMapper memberRowMapper = new MemberRowMapper();

    @BeforeEach
    void setUp() {
        memberJDBCDataAccessService = new MemberJDBCDataAccessService(
                getJdbcTemplate(),
                memberRowMapper
        );
    }

    @Test
    void selectAllMembers() {
        //Given
        Member member = new Member(
                FAKER.name().username() + UUID.randomUUID(),
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                "test123"
        );

        memberJDBCDataAccessService.insertMember(member);

        //When
        var actual = memberJDBCDataAccessService.selectAllMembers();

        //Then
        Assertions.assertThat(actual).isNotEmpty();
    }

    @Test
    void selectMemberById() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        Member member = new Member(
                FAKER.name().username() + UUID.randomUUID(),
                email,
                "test123"
        );

        memberJDBCDataAccessService.insertMember(member);

        Long id = memberJDBCDataAccessService.selectAllMembers()
                .stream()
                .filter(m -> m.getEmail().equals(email))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = memberJDBCDataAccessService.selectMemberById(id);

        //Then
        Assertions.assertThat(actual).isPresent().hasValueSatisfying(m -> {
            Assertions.assertThat(m.getId()).isEqualTo(id);
            Assertions.assertThat(m.getUsername()).isEqualTo(member.getUsername());
            Assertions.assertThat(m.getEmail()).isEqualTo(member.getEmail());
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        //Given
        Long id = -1L;

        //When
        var actual = memberJDBCDataAccessService.selectMemberById(id);

        //Then
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    void insertMember() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        Member member = new Member(
                username,
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                "password"
        );

        memberJDBCDataAccessService.insertMember(member);

        //When
        boolean actual = memberJDBCDataAccessService.selectAllMembers()
                .stream()
                .anyMatch(m -> m.getUsername().equals(username));

        //Then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void existsMemberWithEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        Member member = new Member(
                FAKER.name().username() + UUID.randomUUID(),
                email,
                "password"
        );

        memberJDBCDataAccessService.insertMember(member);

        //When
        boolean actual = memberJDBCDataAccessService.existsMemberWithEmail(email);


        //Then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        //When
        boolean actual = memberJDBCDataAccessService.existsMemberWithEmail(email);

        //Then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void existsMemberWithId() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        Member member = new Member(
                username,
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                "password"
        );

        memberJDBCDataAccessService.insertMember(member);

        Long id = memberJDBCDataAccessService.selectAllMembers()
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        //When
        boolean actual = memberJDBCDataAccessService.existsMemberWithId(id);

        //Then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void existsMemberWithIdReturnsFalseWhenDoesNotExists() {
        //Given
        Long id = -1L;

        //When
        boolean actual = memberJDBCDataAccessService.existsMemberWithId(id);

        //Then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void existsMemberWithUsername() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        Member member = new Member(
                username,
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                "password"
        );

        memberJDBCDataAccessService.insertMember(member);

        //When
        boolean actual = memberJDBCDataAccessService.existsMemberWithUsername(username);

        //Then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void existsMemberWithUsernameReturnsFalseWhenDoesNotExists() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        //When
        boolean actual = memberJDBCDataAccessService.existsMemberWithUsername(username);

        //Then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void deleteMemberById() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        Member member = new Member(
                username,
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                "password"
        );

        memberJDBCDataAccessService.insertMember(member);

        int sizeAfterInsertion = memberJDBCDataAccessService.selectAllMembers().size();

        Long id = memberJDBCDataAccessService.selectAllMembers()
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        //When
        memberJDBCDataAccessService.deleteMemberById(id);

        int actual = memberJDBCDataAccessService.selectAllMembers().size();

        //Then
        Assertions.assertThat(actual).isEqualTo(sizeAfterInsertion - 1);
    }

    @Test
    void updateMemberUsername() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        Member member = new Member(
                username,
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                "password"
        );

        memberJDBCDataAccessService.insertMember(member);

        Long id = memberJDBCDataAccessService.selectAllMembers()
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        String newUsername = "Patryk";

        //When
        Member update = new Member();
        update.setId(id);
        update.setUsername(newUsername);

        memberJDBCDataAccessService.updateMember(update);
        //Then
        var actual = memberJDBCDataAccessService.selectMemberById(id);

        Assertions.assertThat(actual).isPresent().hasValueSatisfying(m -> {
            Assertions.assertThat(m.getId()).isEqualTo(id);
            Assertions.assertThat(m.getUsername()).isEqualTo(newUsername);
            Assertions.assertThat(m.getEmail()).isEqualTo(member.getEmail());
        });
    }

    @Test
    void updateMemberEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        Member member = new Member(
                FAKER.name().username() + UUID.randomUUID(),
                email,
                "password"
        );

        memberJDBCDataAccessService.insertMember(member);

        Long id = memberJDBCDataAccessService.selectAllMembers()
                .stream()
                .filter(m -> m.getEmail().equals(email))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        String newEmail = "patryk@gmail.com";

        //When
        Member update = new Member();
        update.setId(id);
        update.setEmail(newEmail);

        memberJDBCDataAccessService.updateMember(update);
        //Then
        var actual = memberJDBCDataAccessService.selectMemberById(id);

        Assertions.assertThat(actual).isPresent().hasValueSatisfying(m -> {
            Assertions.assertThat(m.getId()).isEqualTo(id);
            Assertions.assertThat(m.getUsername()).isEqualTo(member.getUsername());
            Assertions.assertThat(m.getEmail()).isEqualTo(newEmail);
        });
    }

    @Test
    void willUpdateAllPropertiesMember() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        Member member = new Member(
                username,
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                "password"
        );

        memberJDBCDataAccessService.insertMember(member);

        Long id = memberJDBCDataAccessService.selectAllMembers()
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        String newUsername = "John";
        String newEmail = "john@gmail.com";

        //When
        Member update = new Member();
        update.setId(id);
        update.setUsername(newUsername);
        update.setEmail(newEmail);

        memberJDBCDataAccessService.updateMember(update);
        //Then
        var actual = memberJDBCDataAccessService.selectMemberById(id);

        Assertions.assertThat(actual).isPresent().hasValueSatisfying(m -> {
                    Assertions.assertThat(m.getId()).isEqualTo(id);
                    Assertions.assertThat(m.getUsername()).isEqualTo(newUsername);
                    Assertions.assertThat(m.getEmail()).isEqualTo(newEmail);
                }
        );
    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {
        //Given
        String username = FAKER.name().username() + UUID.randomUUID();

        Member member = new Member(
                username,
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                "password"
        );

        memberJDBCDataAccessService.insertMember(member);

        Long id = memberJDBCDataAccessService.selectAllMembers()
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .map(Member::getId)
                .findFirst()
                .orElseThrow();

        //When
        Member update = new Member();
        update.setId(id);

        memberJDBCDataAccessService.updateMember(update);

        //Then
        var actual = memberJDBCDataAccessService.selectMemberById(id);

        Assertions.assertThat(actual).isPresent().hasValueSatisfying(m -> {
                    Assertions.assertThat(m.getId()).isEqualTo(id);
                    Assertions.assertThat(m.getUsername()).isEqualTo(member.getUsername());
                    Assertions.assertThat(m.getEmail()).isEqualTo(member.getEmail());
                }
        );
    }
}