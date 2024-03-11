package pl.patrykjava.cinemate.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MemberService memberService;

    @Mock
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberDao);
    }

    @Test
    void canGetMember() {
        //Given
        Long id = 1L;

        Member member = new Member(
                id, "Tom", "tom@gmail.com", "password"
        );

        when(memberDao.selectMemberById(id)).thenReturn(Optional.of(member));

        //When
        Member actual = memberService.getMember(id);

        //Then
        assertThat(actual).isEqualTo(member);
    }

    @Test
    void willThrowWhenGetMemberReturnsEmptyOptional() {
        //Given
        Long id = 1L;

        when(memberDao.selectMemberById(id)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> memberService.getMember(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No member with ID: " + id + " has been found.");
    }

    @Test
    void getAllMembers() {
        //When
        memberService.getAllMembers();

        //Then
        verify(memberDao).selectAllMembers();
    }

    @Test
    void addMember() {
        //Given
        String email = "tom@gmail.com";

        when(memberDao.existsMemberWithEmail(email)).thenReturn(false);

        MemberRegistrationRequest request = new MemberRegistrationRequest(
                "Tom", email, "password"
        );
        //When
        memberService.addMember(request);

        //Then
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        verify(memberDao).insertMember(memberArgumentCaptor.capture());

        Member actual = memberArgumentCaptor.getValue();

        assertThat(actual.getId()).isNull();
        assertThat(actual.getUsername()).isEqualTo(request.username());
        assertThat(actual.getEmail()).isEqualTo(request.email());
    }

    @Test
    void deleteMemberById() {
        //Given

        //When

        //Then
    }

    @Test
    void updateMember() {
        //Given
        Member member = new Member(
                1L, "Tom", "tom@gmail.com", "password"
        );

        //When

        //Then
    }
}