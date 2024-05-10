import React, { useState, useEffect } from "react";
import {Wrap, WrapItem, Spinner, Text, Button, Input, Flex} from '@chakra-ui/react';
import MovieCard from "./MovieCard.jsx";
import SidebarWithHeader from "../shared/SiderBar.jsx";
import {errorNotification} from "../../services/notification.js";

const API_URL = `http://www.omdbapi.com?apikey=5f07f8b0`;

const Movie = () => {
    const [searchTerm, setSearchTerm] = useState("");
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const fetchMovies = async (title) => {
        setLoading(true);
        try {
            const response = await fetch(`${API_URL}&s=${title}`);
            const data = await response.json();
            setMovies(data.Search);
        } catch (err) {
            setError("Failed to fetch movies");
            errorNotification("Error", "Failed to fetch movies");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchMovies("Batman");
    }, []);

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
                            <MovieCard movie={movie} />
                        </WrapItem>
                    ))}
                </Wrap>
            )}
        </SidebarWithHeader>
    );
};

export default Movie;
