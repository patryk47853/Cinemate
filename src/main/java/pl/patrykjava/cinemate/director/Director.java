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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "director")
    private List<Movie> movies;
}
