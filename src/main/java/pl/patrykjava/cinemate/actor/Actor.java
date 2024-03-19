package pl.patrykjava.cinemate.actor;

import jakarta.persistence.*;
import lombok.*;
import pl.patrykjava.cinemate.movie.Movie;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "actor")
public class Actor {
    @Id
    @SequenceGenerator(
            name = "actor_id_seq",
            sequenceName = "actor_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "actor_id_seq"
    )
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
    private String country;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

    public Actor(String firstName, String lastName, String country, List<Movie> movies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.movies = movies;
    }
}
