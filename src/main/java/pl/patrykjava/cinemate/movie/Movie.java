package pl.patrykjava.cinemate.movie;

import jakarta.persistence.*;
import lombok.*;
import pl.patrykjava.cinemate.actor.Actor;
import pl.patrykjava.cinemate.category.Category;
import pl.patrykjava.cinemate.director.Director;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    private Double rating;
    private String description;
    private String imgUrl;
    @ManyToMany
    private List<Category> categories;
    @ManyToOne
    private Director director;
    @ManyToMany
    private List<Actor> actors;
}
