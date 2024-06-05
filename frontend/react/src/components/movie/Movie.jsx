import React, { useState, useEffect } from "react";
import { Wrap, WrapItem, Spinner, Text, Button, Input, Flex } from '@chakra-ui/react';
import MovieCard from "./MovieCard.jsx";
import SidebarWithHeader from "../shared/SiderBar.jsx";
import { errorNotification } from "../../services/notification.js";
import { fetchMoviesFromAPI } from "../../services/client.js";
import { fetchMoviesFromDatabase} from "../../services/client.js";

const Movie = ({ source }) => {
    const [searchTerm, setSearchTerm] = useState("");
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const standardizeMovieData = (movie, source) => {
        if (source === 'api') {
            return {
                Title: movie.Title,
                Year: movie.Year,
                Poster: movie.Poster,
                imdbID: movie.imdbID,
            };
        } else if (source === 'database') {
            return {
                Title: movie.title,
                Year: movie.year,
                Poster: movie.imgUrl,
                imdbID: movie.id,
            };
        }
    };

    const fetchMovies = async (title) => {
        setLoading(true);
        try {
            let movies;
            if (source === "api") {
                movies = await fetchMoviesFromAPI(title);
            } else if (source === "database") {
                movies = await fetchMoviesFromDatabase();
            }
            const standardizedMovies = movies.map(movie => standardizeMovieData(movie, source));
            setMovies(standardizedMovies);
        } catch (err) {
            setError(err.message);
            errorNotification("Error", err.message);
        } finally {
            setLoading(false);
        }
    };


    useEffect(() => {
        fetchMovies("Batman");
    }, [source]);

    const handleSearch = () => {
        if (searchTerm.trim() !== "") {
            fetchMovies(searchTerm);
        }
    };

    return (
        <SidebarWithHeader>
            <Flex align="center">
                <Input
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    placeholder="Search for movies"
                    size="md"
                />
                <Button ml={3} colorScheme="blue" onClick={handleSearch}>
                    Search
                </Button>
            </Flex>
            {loading && <Spinner />}
            {error && <Text mt={5}>Error: {error}</Text>}
            {!loading && !error && movies?.length === 0 && (
                <Text mt={5}>No movies found</Text>
            )}
            {!loading && !error && movies?.length > 0 && (
                <Wrap spacing={"10px"} marginTop={10}>
                    {movies.map((movie) => (
                        <WrapItem key={movie.imdbID}>
                            <MovieCard movie={movie} source={source} />
                        </WrapItem>
                    ))}
                </Wrap>
            )}
        </SidebarWithHeader>
    );
};

export default Movie;
