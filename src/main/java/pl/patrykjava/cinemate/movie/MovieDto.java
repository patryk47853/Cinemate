package pl.patrykjava.cinemate.movie;

public record MovieDto(
        Long id,
        String title,
        String imgUrl
) {
}