package pl.patrykjava.cinemate.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

class MemberJDBCDataAccessServiceTest {

    private MemberJDBCDataAccessService memberJDBCDataAccessService;
    private MemberRowMapper memberRowMapper = new MemberRowMapper();

    @BeforeEach
    void setUp() {
        memberJDBCDataAccessService = new MemberJDBCDataAccessService(
                new JdbcTemplate(),
                memberRowMapper
        );
    }

    @Test
    void selectAllMembers() {
        //Given

        //When

        //Then
    }

    @Test
    void selectMemberById() {
        //Given

        //When

        //Then
    }

    @Test
    void insertMember() {
        //Given

        //When

        //Then
    }

    @Test
    void existsMemberWithEmail() {
        //Given

        //When

        //Then
    }

    @Test
    void existsMemberWithId() {
        //Given

        //When

        //Then
    }

    @Test
    void existsMemberWithUsername() {
        //Given

        //When

        //Then
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

        //When

        //Then
    }
}