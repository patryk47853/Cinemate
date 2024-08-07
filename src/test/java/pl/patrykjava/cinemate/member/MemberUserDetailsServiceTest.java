package pl.patrykjava.cinemate.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberUserDetailsServiceTest {

    @Mock
    @Qualifier("jpa")
    private MemberDao memberDao;

    @InjectMocks
    private MemberUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userDetailsService = new MemberUserDetailsService(memberDao);
    }

    @Test
    void loadUserByUsername_WhenUserExists_ReturnUserDetails() {
        // Arrange
        String email = "test@example.com";
        String username = "user";

        Member member = new Member(username, email, "password");

        when(memberDao.selectMemberByUsername(username)).thenReturn(Optional.of(member));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        verify(memberDao, times(1)).selectMemberByUsername(username);
    }


    @Test
    void loadUserByUsername_WhenUserDoesNotExist_ThrowUsernameNotFoundException() {
        // Arrange
        String username = "nonexistent";
        when(memberDao.selectMemberByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        assertEquals("User with username: " + username + " not found.", exception.getMessage());
        verify(memberDao, times(1)).selectMemberByUsername(username);
    }
}
