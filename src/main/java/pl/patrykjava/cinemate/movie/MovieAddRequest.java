package pl.patrykjava.cinemate.movie;

import pl.patrykjava.cinemate.actor.Actor;
import pl.patrykjava.cinemate.category.Category;
import pl.patrykjava.cinemate.director.Director;

import java.util.List;

public record MovieAddRequest(
        String title,
        Double rating,
        String description,
        String imgUrl,
        List<Category> categories,
        Director director,
        List<Actor> actors
) {
}
