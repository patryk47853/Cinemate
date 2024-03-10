package pl.patrykjava.cinemate.director;

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
@Table(name = "director")
public class Director {
    @Id
    @SequenceGenerator(
            name = "director_id_seq",
            sequenceName = "director_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "director_id_seq"
    )
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "director")
    private List<Movie> movies;
}
