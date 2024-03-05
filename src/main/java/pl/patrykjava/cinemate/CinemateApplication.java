package pl.patrykjava.cinemate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.patrykjava.cinemate.member.Member;
import pl.patrykjava.cinemate.member.MemberRepository;

import java.util.List;

@SpringBootApplication
public class CinemateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemateApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(MemberRepository memberRepository) {
        return args -> {
            Member patryk = new Member(1L, "Patryk", "patryk@gmail.com", "admin123");
            Member patrycja = new Member(2L, "Patrycja", "patrycja@gmail.com", "admin123");

            List<Member> members = List.of(patryk, patrycja);

            memberRepository.saveAll(members);
        };
    }
}
