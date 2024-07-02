import React, {useEffect, useState} from 'react';
import {Box, Grid, Heading, Spinner, Alert, AlertIcon, Input, Button, Flex, Wrap} from "@chakra-ui/react";
import MovieCard from "../movie/MovieCard.jsx";
import useMemberProfile from "../../services/useMemberProfile.js";
import SidebarWithHeader from "../shared/SiderBar.jsx";

const FavoriteMovies = () => {
    const [loading, setLoading] = useState(false);
    const {memberProfile, error, fetchMemberProfile, isAdmin} = useMemberProfile();
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        const fetchProfile = async () => {
            setLoading(true);
            try {
                await fetchMemberProfile();
            } catch (err) {
                console.error("Error fetching member profile:", err);
            } finally {
                setLoading(false);
            }
        };

        fetchMemberProfile();
    }, [fetchMemberProfile]);

    if (error) {
        return (
            <SidebarWithHeader>
                <Box p={5}>
                    <Alert status="error">
                        <AlertIcon/>
                        There was an error fetching the member profile.
                    </Alert>
                </Box>
            </SidebarWithHeader>
        );
    }

    if (!memberProfile) {
        return null;
    }

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
            <Wrap spacing={"10px"} marginTop={10}>
                    {memberProfile?.favoriteMovies.map(movie => (
                        <MovieCard
                            key={movie.id}
                            movie={{
                                Year: movie.year,
                                Poster: movie.imgUrl,
                                Title: movie.title,
                                imdbID: movie.id
                            }}
                            source="user"
                        />
                    ))}
            </Wrap>
        </SidebarWithHeader>
    );
}

export default FavoriteMovies;
