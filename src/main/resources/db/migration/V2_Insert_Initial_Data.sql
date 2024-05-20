-- Actors
INSERT INTO actor (first_name, last_name, country)
VALUES ('Leonardo', 'DiCaprio', 'USA'),
       ('Tom', 'Hanks', 'USA'),
       ('Meryl', 'Streep', 'USA'),
       ('Scarlett', 'Johansson', 'USA'),
       ('Brad', 'Pitt', 'USA'),
       ('Emma', 'Watson', 'UK'),
       ('Daniel', 'Radcliffe', 'UK'),
       ('Jennifer', 'Lawrence', 'USA'),
       ('Johnny', 'Depp', 'USA'),
       ('Angelina', 'Jolie', 'USA');

-- Directors
INSERT INTO director (first_name, last_name)
VALUES ('Christopher', 'Nolan'),
       ('Steven', 'Spielberg'),
       ('Quentin', 'Tarantino'),
       ('Martin', 'Scorsese'),
       ('James', 'Cameron'),
       ('Peter', 'Jackson'),
       ('Francis Ford', 'Coppola'),
       ('Alfred', 'Hitchcock'),
       ('Stanley', 'Kubrick'),
       ('Ridley', 'Scott');

-- Movies
INSERT INTO movie (title, rating, description, img_url, director_id)
VALUES ('Inception', 8.8,
        'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.',
        'inception.jpg', 1),
       ('Forrest Gump', 8.8,
        'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.',
        'forrest_gump.jpg', 2),
       ('The Shawshank Redemption', 9.3,
        'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.',
        'shawshank_redemption.jpg', 3),
       ('Pulp Fiction', 8.9,
        'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.',
        'pulp_fiction.jpg', 3),
       ('The Godfather', 9.2,
        'An organized crime dynasty aging patriarch transfers control of his clandestine empire to his reluctant son.',
        'godfather.jpg', 7),
       ('Titanic', 7.8,
        'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.',
        'titanic.jpg', 5),
       ('The Dark Knight', 9.0,
        'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.',
        'dark_knight.jpg', 1),
       ('Avatar', 7.8,
        'A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.',
        'avatar.jpg', 5),
       ('Schindler List', 8.9,
        'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.',
        'schindlers_list.jpg', 2),
       ('Inglourious Basterds', 8.3,
        'In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner vengeful plans for the same.',
        'inglourious_basterds.jpg', 3);

-- Members
INSERT INTO member (username, email, password)
VALUES ('user1', 'user1@example.com', 'password1'),
       ('user2', 'user2@example.com', 'password2'),
       ('user3', 'user3@example.com', 'password3'),
       ('user4', 'user4@example.com', 'password4'),
       ('user5', 'user5@example.com', 'password5'),
       ('user6', 'user6@example.com', 'password6'),
       ('user7', 'user7@example.com', 'password7'),
       ('user8', 'user8@example.com', 'password8'),
       ('user9', 'user9@example.com', 'password9'),
       ('user10', 'user10@example.com', 'password10');

-- Members
INSERT INTO role (id, name)
VALUES (1, 'USER'),
       (2, 'ADMIN');

-- Comments
INSERT INTO comment (content, member_id, movie_id)
VALUES ('One of the best movies ever made!', 1, 1),
       ('Tom Hanks was outstanding in this role.', 2, 2),
       ('This movie left me speechless.', 3, 3),
       ('Leonardo DiCaprio performance was amazing.', 4, 1),
       ('Highly recommended!', 5, 5),
       ('The plot twists kept me on the edge of my seat.', 6, 4),
       ('A must-watch for everyone.', 7, 6),
       ('Such a powerful story.', 8, 7),
       ('The cinematography was breathtaking.', 9, 8),
       ('I have watched this movie multiple times and it never gets old.', 10, 9);

-- Insert categories
INSERT INTO category (category_name)
VALUES ('Action'),
       ('Drama'),
       ('Crime'),
       ('Adventure'),
       ('Romance');

-- Update movie_category relationships
INSERT INTO movie_category (movie_id, category_id)
VALUES (1, 1),   -- Inception, Action
       (2, 2),   -- Forrest Gump, Drama
       (3, 2),   -- The Shawshank Redemption, Drama
       (4, 3),   -- Pulp Fiction, Crime
       (5, 3),   -- The Godfather, Crime
       (6, 5),   -- Titanic, Romance
       (7, 1),   -- The Dark Knight, Action
       (8, 4),   -- Avatar, Adventure
       (9, 2),   -- Schindler List, Drama
       (10, 3);  -- Inglourious Basterds, Crime

-- Movie-Actor Relationships
INSERT INTO movie_actor (movie_id, actor_id)
VALUES (1, 1),
       (1, 4),
       (2, 2),
       (3, 1),
       (3, 4),
       (4, 1),
       (4, 4),
       (5, 5),
       (6, 1),
       (7, 1);

-- Member Favorite Movies
INSERT INTO member_favorite_movie (member_id, movie_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- Member Roles
INSERT INTO member_role (member_id, role_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 2),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 2),
       (9, 1),
       (10, 1);