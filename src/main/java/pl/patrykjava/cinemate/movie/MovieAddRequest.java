package pl.patrykjava.cinemate.movie;

public record MovieAddRequest(
        String title,
        Double rating,
        String description,
        String imgUrl,
        String awards,
        String year,
        String categories,
        String director,
        String actors
) {
}
