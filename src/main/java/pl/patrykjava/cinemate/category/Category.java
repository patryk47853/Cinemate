package pl.patrykjava.cinemate.category;

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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private List<Movie> movies;
}
