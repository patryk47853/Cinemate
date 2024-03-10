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
}
