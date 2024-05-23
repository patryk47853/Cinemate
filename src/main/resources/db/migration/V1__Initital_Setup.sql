CREATE TABLE actor
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    country    VARCHAR(255)
);

CREATE TABLE category
(
    id            SERIAL PRIMARY KEY,
    category_name VARCHAR(255)
);

CREATE TABLE director
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

CREATE TABLE member
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    img_url  VARCHAR(255) NOT NULL
);

CREATE TABLE role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE movie
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    rating      DOUBLE PRECISION,
    description TEXT,
    img_url     VARCHAR(255),
    director_id BIGINT,
    FOREIGN KEY (director_id) REFERENCES director (id)
);

CREATE TABLE comment
(
    id        SERIAL PRIMARY KEY,
    content   TEXT NOT NULL,
    member_id BIGINT,
    movie_id  BIGINT,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id)
);

CREATE TABLE movie_actor
(
    movie_id BIGINT,
    actor_id BIGINT,
    FOREIGN KEY (movie_id) REFERENCES movie (id),
    FOREIGN KEY (actor_id) REFERENCES actor (id),
    PRIMARY KEY (movie_id, actor_id)
);

CREATE TABLE movie_category
(
    movie_id    BIGINT,
    category_id BIGINT,
    FOREIGN KEY (movie_id) REFERENCES movie (id),
    FOREIGN KEY (category_id) REFERENCES category (id),
    PRIMARY KEY (movie_id, category_id)
);

CREATE TABLE member_favorite_movie
(
    member_id BIGINT,
    movie_id  BIGINT,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id),
    PRIMARY KEY (member_id, movie_id)
);

CREATE TABLE member_role
(
    member_id BIGINT,
    role_id BIGINT,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    PRIMARY KEY (member_id, role_id)
);