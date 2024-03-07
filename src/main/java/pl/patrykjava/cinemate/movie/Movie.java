package pl.patrykjava.cinemate.movie;

import jakarta.persistence.*;
import lombok.*;
import pl.patrykjava.cinemate.actor.Actor;
import pl.patrykjava.cinemate.category.Category;
import pl.patrykjava.cinemate.director.Director;
import pl.patrykjava.cinemate.member.Member;

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
    @JoinTable(
            name = "movie_category",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    @ManyToMany(mappedBy = "favoriteMovies")
    private List<Member> favoredBy;
}
