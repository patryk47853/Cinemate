package pl.patrykjava.cinemate.member;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MemberRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // Given
        MemberRowMapper memberRowMapper = new MemberRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("username")).thenReturn("Jack");
        when(resultSet.getString("email")).thenReturn("jack@gmail.com");
        when(resultSet.getString("password")).thenReturn("password");

        // When
        Member actual = memberRowMapper.mapRow(resultSet, 1);

        // Then
        Member expected = new Member(
                1L, "Jack", "jack@gmail.com", "password");
        assertThat(actual).isEqualTo(expected);
    }
}
