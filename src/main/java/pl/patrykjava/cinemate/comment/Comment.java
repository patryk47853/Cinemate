package pl.patrykjava.cinemate.comment;

import jakarta.persistence.*;
import lombok.*;
import pl.patrykjava.cinemate.member.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private Member member;
}