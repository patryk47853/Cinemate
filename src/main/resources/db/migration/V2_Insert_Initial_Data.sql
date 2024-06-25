-- Populate the actor table
INSERT INTO actor (first_name, last_name) VALUES
                                              ('Michael', 'Keaton'),
                                              ('Jack', 'Nicholson'),
                                              ('Kim', 'Basinger'),
                                              ('Leonardo', 'DiCaprio'),
                                              ('Joseph', 'Gordon-Levitt'),
                                              ('Elliot', 'Page'),
                                              ('Daniel', 'Radcliffe'),
                                              ('Emma', 'Watson'),
                                              ('Rupert', 'Grint'),
                                              ('Guy', 'Pearce'),
                                              ('Carrie-Anne', 'Moss'),
                                              ('Joe', 'Pantoliano'),
                                              ('Cillian', 'Murphy'),
                                              ('Emily', 'Blunt'),
                                              ('Matt', 'Damon'),
                                              ('Margot', 'Robbie'),
                                              ('Ryan', 'Gosling'),
                                              ('Issa', 'Rae'),
                                              ('Russell', 'Crowe'),
                                              ('Joaquin', 'Phoenix'),
                                              ('Connie', 'Nielsen');

-- Populate the category table
INSERT INTO category (category_name) VALUES
                                         ('Action'),
                                         ('Adventure'),
                                         ('Sci-Fi'),
                                         ('Family'),
                                         ('Fantasy'),
                                         ('Mystery'),
                                         ('Thriller'),
                                         ('Biography'),
                                         ('Drama'),
                                         ('History'),
                                         ('Comedy');

-- Populate the director table
INSERT INTO director (first_name, last_name) VALUES
                                                 ('Tim', 'Burton'),
                                                 ('Christopher', 'Nolan'),
                                                 ('David', 'Yates'),
                                                 ('Ridley', 'Scott'),
                                                 ('Greta', 'Gerwig');

-- Populate the member table with avatars
INSERT INTO member (username, email, password, img_url) VALUES
                                                            ('john', 'john@example.com', '$2a$10$D5wrapgEtAihND7Qy7HtzulsHlLTwb9.ltxMYSbLAQcqkMGUJgykK', 'https://i.pravatar.cc/300?img=51'),
                                                            ('jane', 'jane@example.com', '$2a$10$7LuC.T6flNSN3PEC3r8lsecQx.M8sFFhc/.ewjMHWKNjfAqz3Slma', 'https://i.pravatar.cc/300?img=49'),
                                                            ('michael', 'michael@example.com', '$2a$10$W..YPfRv.n1cKmsW9yHP.u7C23dJO1Cr15GS99IkAV4jdQ251frRy', 'https://i.pravatar.cc/300?img=52'),
                                                            ('emma', 'emma@example.com', '$2a$10$vXg50rkXKuqLyYG2.0pRUuT5SUsZPPQ9oRrmKFrms7MOURNJzCkDC', 'https://i.pravatar.cc/300?img=43'),
                                                            ('david', 'david@example.com', '$2a$10$5OLUZBNiOrmMRm5/CgD/PeAyFOHMnaOMpzKbxoQIEsBuNUAgsCn.O', 'https://i.pravatar.cc/300?img=53')  ,
                                                            ('sarah', 'sarah@example.com', '$2a$10$Hf5QgKnpvyiLyS.GxEKDf.N6w.KqgvR0LQmtu/6xL9isKR42QmSle', 'https://i.pravatar.cc/300?img=48'),
                                                            ('chris', 'chris@example.com', '$2a$10$5tJBraGK5bY6dbK9WanYSuEFlQSvAIThI8rnTqh.OeF08MYlTUgj.', 'https://i.pravatar.cc/300?img=54'),
                                                            ('kate', 'kate@example.com', '$2a$10$/uFIrtKp0D3QJDnXuHL9deMcRAnwyYM1jLtskqllyUBakBSzGoylK', 'https://i.pravatar.cc/300?img=47'),
                                                            ('james', 'james@example.com', '$2a$10$4JQAbIoDkgAdfffc1Rywku8b5SHgWm8qnIvNYaDBUicBde3crOeGO', 'https://i.pravatar.cc/300?img=55'),
                                                            ('olivia', 'olivia@example.com', '$2a$10$rRPE6CkmH41Px4vkCyndm.FFcXfAXP3xZdX0GCVhFPcwcwlSiZ4GS', 'https://i.pravatar.cc/300?img=46'),
                                                            ('robert', 'robert@example.com', '$2a$10$WtNJCiupCEA.WdgAR.z4I.o2VqOxZQ6cDIw1/O0FNaU12Aln2/8pi', 'https://i.pravatar.cc/300?img=56'),
                                                            ('linda', 'linda@example.com', '$2a$10$UCb6TUiPR1oQStMyQtioWeWuqYWRbSlH1Ojwo6Ft4jPM5hod6ZKum', 'https://i.pravatar.cc/300?img=45');


-- Populate the movie table
INSERT INTO movie (title, rating, description, img_url, year, awards, director_id) VALUES
                                                                                       ('Batman', 7.5, 'The Dark Knight of Gotham City begins his war on crime...', 'https://m.media-amazon.com/images/M/MV5BZWQ0OTQ3ODctMmE0MS00ODc2LTg0ZTEtZWIwNTUxOGExZTQ4XkEyXkFqcGdeQXVyNzAwMjU2MTY@._V1_SX300.jpg', '1989', 'Won 1 Oscar. 11 wins & 28 nominations total', 1),
                                                                                       ('Inception', 8.8, 'A thief who steals corporate secrets through the use of dream-sharing technology...', 'https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg', '2010', 'Won 4 Oscars. 159 wins & 220 nominations total', 2),
                                                                                       ('Harry Potter and the Deathly Hallows: Part 2', 8.1, 'Harry, Ron, and Hermione search for Voldemorts remaining Horcruxes...', 'https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg', '2011', 'Nominated for 3 Oscars. 48 wins & 95 nominations total', 3),
('Memento', 8.4, 'A man with short-term memory loss attempts to track down his wifes murderer...', 'https://m.media-amazon.com/images/M/MV5BZTcyNjk1MjgtOWI3Mi00YzQwLWI5MTktMzY4ZmI2NDAyNzYzXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg', '2000', 'Nominated for 2 Oscars. 57 wins & 59 nominations total', 2),
                                                                                       ('Oppenheimer', 8.3, 'The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb...', 'https://m.media-amazon.com/images/M/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY@._V1_SX300.jpg', '2023', 'Won 7 Oscars. 346 wins & 357 nominations total', 2),
                                                                                       ('Barbie', 6.8, 'Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land...', 'https://m.media-amazon.com/images/M/MV5BNjU3N2QxNzYtMjk1NC00MTc4LTk1NTQtMmUxNTljM2I0NDA5XkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg', '2023', 'Won 1 Oscar. 195 wins & 424 nominations total', 5),
                                                                                       ('Gladiator', 8.5, 'A former Roman General sets out to exact vengeance against the corrupt emperor...', 'https://m.media-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg', '2000', 'Won 5 Oscars. 60 wins & 104 nominations total', 4);

-- Populate the comment table
INSERT INTO comment (content, member_id, movie_id) VALUES
                                                       ('Great movie!', 1, 1),
                                                       ('Loved the plot twist.', 2, 2),
                                                       ('Amazing special effects.', 3, 2),
                                                       ('Best Harry Potter movie.', 4, 3),
                                                       ('Mind-blowing!', 5, 4),
                                                       ('A must-watch.', 6, 5),
                                                       ('Hilarious and fun.', 7, 6),
                                                       ('Epic historical drama.', 8, 7);

-- Populate the movie_actor table
INSERT INTO movie_actor (movie_id, actor_id) VALUES
                                                 (1, 1), (1, 2), (1, 3),
                                                 (2, 4), (2, 5), (2, 6),
                                                 (3, 7), (3, 8), (3, 9),
                                                 (4, 10), (4, 11), (4, 12),
                                                 (5, 13), (5, 14), (5, 15),
                                                 (6, 16), (6, 17), (6, 18),
                                                 (7, 19), (7, 20), (7, 21);

-- Populate the movie_category table
INSERT INTO movie_category (movie_id, category_id) VALUES
                                                       (1, 1), (1, 2),
                                                       (2, 1), (2, 2), (2, 3),
                                                       (3, 2), (3, 4), (3, 5),
                                                       (4, 6), (4, 7),
                                                       (5, 8), (5, 9), (5, 10),
                                                       (6, 2), (6, 11), (6, 5),
                                                       (7, 1), (7, 2), (7, 9);

-- Populate the member_favorite_movie table
INSERT INTO member_favorite_movie (member_id, movie_id) VALUES
                                                            (1, 1), (2, 2), (3, 3),
                                                            (4, 4), (5, 5), (6, 6),
                                                            (7, 7), (8, 1), (9, 2),
                                                            (10, 3), (11, 4), (12, 5);

-- Populate the member_role table
INSERT INTO member_role (member_id, role) VALUES
                                              (1, 'ROLE_USER'), (2, 'ROLE_USER'), (3, 'ROLE_USER'),
                                              (4, 'ROLE_USER'), (5, 'ROLE_USER'), (6, 'ROLE_USER'),
                                              (7, 'ROLE_USER'), (8, 'ROLE_USER'), (9, 'ROLE_USER'),
                                              (10, 'ROLE_USER'), (11, 'ROLE_USER'), (12, 'ROLE_USER'),
                                              (1, 'ROLE_ADMIN'), (12, 'ROLE_ADMIN');
