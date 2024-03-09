package pl.patrykjava.cinemate.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void existsMemberByEmail() {
        //Given

        //When

        //Then
    }

    @Test
    void existsMemberById() {
        //Given

        //When

        //Then
    }

    @Test
    void existsMemberByUsername() {
        //Given

        //When

        //Then
    }
}