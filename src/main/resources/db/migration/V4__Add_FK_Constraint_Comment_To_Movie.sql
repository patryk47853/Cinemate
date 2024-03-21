ALTER TABLE comment
    ADD COLUMN movie_id BIGINT,
    ADD CONSTRAINT fk_comment_movie
        FOREIGN KEY (movie_id) REFERENCES movie (id);