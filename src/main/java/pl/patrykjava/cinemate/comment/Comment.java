package pl.patrykjava.cinemate.comment;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.patrykjava.cinemate.member.Member;
import pl.patrykjava.cinemate.movie.Movie;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @SequenceGenerator(
            name = "comment_id_seq",
            sequenceName = "comment_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_id_seq"
    )
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Comment(String content, Member member, Movie movie) {
        this.content = content;
        this.member = member;
        this.movie = movie;
    }

    public Comment(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}