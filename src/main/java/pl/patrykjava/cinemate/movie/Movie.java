package pl.patrykjava.cinemate.movie;

import jakarta.persistence.*;
import lombok.*;
import pl.patrykjava.cinemate.actor.Actor;
import pl.patrykjava.cinemate.category.Category;
import pl.patrykjava.cinemate.comment.Comment;
import pl.patrykjava.cinemate.director.Director;
import pl.patrykjava.cinemate.member.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @SequenceGenerator(
            name = "movie_id_seq",
            sequenceName = "movie_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_id_seq"
    )
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
    private List<Category> categories = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteMovies")
    private List<Member> favoredBy = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Comment> comments = new ArrayList<>();

    public Movie(String title, Double rating, String description, String imgUrl, List<Category> categories, Director director, List<Actor> actors, List<Member> favoredBy, List<Comment> comments) {
        this(null, title, rating, description, imgUrl, categories, director, actors, favoredBy, comments);
    }
    public Movie(Long id, String title, Director director) {
        this(id, title, director, null, null);
    }

    public Movie(String title, Director director, List<Actor> actors) {
        this(null, title, director, actors, null);
    }

    public Movie(String title, Director director) {
        this(null, title, director, null, null);
    }

    public Movie(Long id, String title) {
        this(id, title, null, null, null);
    }

    public Movie(String title, List<Category> categories) {
        this(null, title, null, null, categories);
    }

    private Movie(Long id, String title, Director director, List<Actor> actors, List<Category> categories) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.categories = categories;
    }
}
