package pl.patrykjava.cinemate.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import pl.patrykjava.cinemate.movie.Movie;

import java.util.ArrayList;
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

    public Category(String categoryName, List<Movie> movies) {
        this.categoryName = categoryName;
        this.movies = movies;
    }

    @Id
    @SequenceGenerator(
            name = "category_id_seq",
            sequenceName = "category_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_id_seq"
    )
    private Long id;

    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private List<Movie> movies = new ArrayList<>();
}
