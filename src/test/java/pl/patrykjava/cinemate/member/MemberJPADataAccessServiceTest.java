package pl.patrykjava.cinemate.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberJPADataAccessServiceTest {

    private MemberJPADataAccessService memberJPADataAccessService;
    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberJPADataAccessService = new MemberJPADataAccessService(memberRepository);
    }

    @Test
    void selectAllMembers() {
        //When
        memberJPADataAccessService.selectAllMembers();

        //Then
        verify(memberRepository).findAll();
    }

    @Test
    void selectMemberById() {
        //Given
        Long id = 1L;

        //When
        memberJPADataAccessService.selectMemberById(id);

        //Then
        verify(memberRepository).findById(id);
    }

    @Test
    void insertMember() {
        //Given
        Member member = new Member(
                "Tom", "tom@gmail.com", "password"
        );

        //When
        memberJPADataAccessService.insertMember(member);

        //Then
        verify(memberRepository).save(member);
    }

    @Test
    void existsMemberWithEmail() {
        //Given
        String email = "email";

        //When
        memberJPADataAccessService.existsMemberWithEmail(email);

        //Then
        verify(memberRepository).existsMemberByEmail(email);
    }

    @Test
    void existsMemberWithId() {
        //Given
        Long id = 1L;

        //When
        memberJPADataAccessService.existsMemberWithId(id);

        //Then
        verify(memberRepository).existsMemberById(id);
    }

    @Test
    void existsMemberWithUsername() {
        //Given
        String username = "username";

        //When
        memberJPADataAccessService.existsMemberWithUsername(username);

        //Then
        verify(memberRepository).existsMemberByUsername(username);
    }

    @Test
    void deleteMemberById() {
        //Given
        Long id = 1L;

        //When
        memberJPADataAccessService.deleteMemberById(id);

        //Then
        verify(memberRepository).deleteById(id);
    }

    @Test
    void updateMember() {
        //Given
        Member member = new Member(
                "Tom", "tom@gmail.com", "password"
        );

        //When
        memberJPADataAccessService.updateMember(member);

        //Then
        verify(memberRepository).save(member);
    }

    @Test
    void selectMemberByEmail() {
        //Given
        String email = "tom@gmail.com";
        Member member = new Member(
                "Tom", email, "password"
        );

        //When
        memberJPADataAccessService.selectMemberByEmail(email);

        //Then
        verify(memberRepository).findMemberByEmail(email);
    }
}