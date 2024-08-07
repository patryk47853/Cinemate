package pl.patrykjava.cinemate.member;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.patrykjava.cinemate.comment.CommentDao;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.movie.MovieDtoMapper;
import pl.patrykjava.cinemate.movie.MovieRepository;
import pl.patrykjava.cinemate.movie.MovieService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MemberService memberService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberDao memberDao;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieDtoMapper movieDtoMapper;

    @Mock
    private MemberDtoMapper memberDtoMapper;

    @Mock
    private CommentDao commentDao;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberDao, movieRepository, passwordEncoder, memberDtoMapper, commentDao);
    }

    @Test
    void canGetMember() {
        // Given
        Long id = 1L;
        Member member = new Member(id, "Tom", "tom@gmail.com", "password");
        member.setFavoriteMovies(new ArrayList<>());

        when(memberDao.selectMemberById(id)).thenReturn(Optional.of(member));

        MemberDto expected = new MemberDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getImgUrl(),
                member.getComments(),
                member.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                member.getFavoriteMovies()
                        .stream()
                        .map(movieDtoMapper)
                        .collect(Collectors.toList())
        );

        when(memberDtoMapper.apply(member)).thenReturn(expected);

        // When
        MemberDto actual = memberService.getMember(id);

        // Then
        assertThat(actual).isEqualTo(expected);
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

        String passwordHash = "412asfml:op;[12";

        when(passwordEncoder.encode(request.password())).thenReturn(passwordHash);
        //When
        memberService.addMember(request);

        //Then
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        verify(memberDao).insertMember(memberArgumentCaptor.capture());

        Member actual = memberArgumentCaptor.getValue();

        assertThat(actual.getId()).isNull();
        assertThat(actual.getUsername()).isEqualTo(request.username());
        assertThat(actual.getEmail()).isEqualTo(request.email());
        assertThat(actual.getPassword()).isEqualTo(passwordHash);
    }

    @Test
    void willThrowWhenAddMemberWithExistingEmail() {
        //Given
        String email = "tom@gmail.com";

        when(memberDao.existsMemberWithEmail(email)).thenReturn(true);

        MemberRegistrationRequest request = new MemberRegistrationRequest(
                "Tom", email, "password"
        );
        //When
        assertThatThrownBy(() -> memberService.addMember(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email: " + email + " is already taken.");

        //Then
        verify(memberDao, never()).insertMember(any());
    }

    @Test
    void willThrowWhenAddMemberWithExistingUsername() {
        //Given
        String username = "Tom";

        when(memberDao.existsMemberWithUsername(username)).thenReturn(true);

        MemberRegistrationRequest request = new MemberRegistrationRequest(
                username, "tom@gmail.com", "password"
        );
        //When
        assertThatThrownBy(() -> memberService.addMember(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Username: " + username + " is already taken.");

        //Then
        verify(memberDao, never()).insertMember(any());
    }

    @Test
    void deleteMemberById() {
        //Given
        Long id = 1L;

        when(memberDao.existsMemberWithId(id)).thenReturn(true);

        //When
        memberService.deleteMemberById(id);

        //Then
        verify(memberDao).deleteMemberById(id);
    }

    @Test
    void willThrowWhenDeleteMemberByIdDoesNotExists() {
        //Given
        Long id = 1L;

        when(memberDao.existsMemberWithId(id)).thenReturn(false);

        //When
        assertThatThrownBy(() -> memberService.deleteMemberById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Member with ID: " + id + " not found.");

        //Then
        verify(memberDao, never()).deleteMemberById(any());
    }

    @Test
    void canUpdateAllMembersProperties() {
        //Given
        Long id = 1L;

        Member member = new Member(
                id, "Tom", "tom@gmail.com", "password"
        );
        when(memberDao.selectMemberById(id)).thenReturn(Optional.of(member));

        MemberUpdateRequest request = new MemberUpdateRequest(
                "John", "john@gmail.com", "password"
        );
        when(memberDao.existsMemberWithEmail(request.email())).thenReturn(false);

        //When
        memberService.updateMember(id, request);

        //Then
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        verify(memberDao).updateMember(memberArgumentCaptor.capture());
        Member capturedMember = memberArgumentCaptor.getValue();

        assertThat(capturedMember.getUsername()).isEqualTo(request.username());
        assertThat(capturedMember.getEmail()).isEqualTo(request.email());
        assertThat(capturedMember.getPassword()).isEqualTo(request.password());
    }

    @Test
    void canUpdateOnlyMemberUsername() {
        //Given
        Long id = 1L;

        Member member = new Member(
                id, "Tom", "tom@gmail.com", "password"
        );
        when(memberDao.selectMemberById(id)).thenReturn(Optional.of(member));

        MemberUpdateRequest request = new MemberUpdateRequest(
                "John", null, null
        );
        when(memberDao.existsMemberWithUsername(request.username())).thenReturn(false);


        //When
        memberService.updateMember(id, request);

        //Then
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        verify(memberDao).updateMember(memberArgumentCaptor.capture());
        Member capturedMember = memberArgumentCaptor.getValue();

        assertThat(capturedMember.getUsername()).isEqualTo(request.username());
        assertThat(capturedMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(capturedMember.getPassword()).isEqualTo(member.getPassword());
    }

    @Test
    void willThrowWhenUpdateMemberUsernameWithTakenUsername() {
        //Given
        Long id = 1L;

        Member member = new Member(
                id, "Tom", "tom@gmail.com", "password"
        );
        when(memberDao.selectMemberById(id)).thenReturn(Optional.of(member));

        MemberUpdateRequest request = new MemberUpdateRequest(
                "John", null, null);
        when(memberDao.existsMemberWithUsername(request.username())).thenReturn(true);

        //When
        assertThatThrownBy(() -> memberService.updateMember(id, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Username: " + request.username() + " is already taken.");

        //Then
        verify(memberDao, never()).updateMember(any());
    }

    @Test
    void canUpdateOnlyMemberEmail() {
        //Given
        Long id = 1L;

        Member member = new Member(
                id, "Tom", "tom@gmail.com", "password"
        );

        when(memberDao.selectMemberById(id)).thenReturn(Optional.of(member));

        //When
        MemberUpdateRequest request = new MemberUpdateRequest(null, "john@gmail.com", null);
        when(memberDao.existsMemberWithEmail(request.email())).thenReturn(false);

        memberService.updateMember(id, request);

        //Then
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        verify(memberDao).updateMember(memberArgumentCaptor.capture());
        Member capturedMember = memberArgumentCaptor.getValue();

        assertThat(capturedMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(capturedMember.getEmail()).isEqualTo(request.email());
        assertThat(capturedMember.getPassword()).isEqualTo(member.getPassword());
    }

    @Test
    void willThrowWhenUpdateMemberUsernameWithTakenEmail() {
        //Given
        Long id = 1L;

        Member member = new Member(
                id, "Tom", "tom@gmail.com", "password"
        );
        when(memberDao.selectMemberById(id)).thenReturn(Optional.of(member));

        MemberUpdateRequest request = new MemberUpdateRequest(
                null, "john@gmail.com", null
        );
        when(memberDao.existsMemberWithEmail(request.email())).thenReturn(true);

        //When
        assertThatThrownBy(() -> memberService.updateMember(id, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email: " + request.email() + " is already taken.");

        //Then
        verify(memberDao, never()).updateMember(any());
    }
}