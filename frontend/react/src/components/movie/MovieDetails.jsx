import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Badge, Box, Button, Flex, Image, Text } from "@chakra-ui/react";
import SidebarWithHeader from "../shared/SiderBar.jsx";
import {fetchMovieFromDatabase, addToLibrary, addMovieToFavorites} from "../../services/client.js";
import useMemberProfile from "../../services/useMemberProfile.js";

const API_URL = `http://www.omdbapi.com?apikey=5f07f8b0`;

const MovieDetails = () => {
    const { title, year, movieId } = useParams();
    const [movieDetails, setMovieDetails] = useState(null);
    const [isFavourite, setIsFavourite] = useState(false);
    const [source, setSource] = useState(movieId ? 'database' : 'api');
    const { memberProfile, loading, error, fetchMemberProfile, isAdmin } = useMemberProfile();

    useEffect(() => {
        const fetchMovieDetails = async () => {
            try {
                let data;
                if (source === 'api') {
                    const response = await fetch(`${API_URL}&t=${title}&y=${year}`);
                    data = await response.json();
                    if (!response.ok) throw new Error(data.Error);
                } else {
                    data = await fetchMovieFromDatabase(movieId);
                }
                setMovieDetails(data);
            } catch (err) {
                console.error('Failed to fetch movie details:', err);
            }
        };

        fetchMovieDetails();
    }, [title, year, movieId, source]);

    const toggleFavourite = async () => {
        try {
            const memberId = memberProfile?.id;
            if (!memberId) {
                console.error('Member ID not found.');
                return;
            }
            await addMovieToFavorites(memberId, movieId);
            setIsFavourite(!isFavourite);
        } catch (error) {
            console.error('Failed to add movie to favorites:', error);
        }
    };

    if (!movieDetails) {
        return <div>Loading...</div>;
    }

    const property = {
        imageUrl: source === 'api' ? movieDetails.Poster : movieDetails.imgUrl,
        imageAlt: source === 'api' ? movieDetails.Title : movieDetails.title,
        actors: source === 'api' ? movieDetails.Actors : movieDetails.actors.map(actor => `${actor.firstName} ${actor.lastName}`).join(', '),
        director: source === 'api' ? movieDetails.Director : `${movieDetails.director.firstName} ${movieDetails.director.lastName}`,
        title: source === 'api' ? movieDetails.Title : movieDetails.title,
        rating: source === 'api' ? movieDetails.imdbRating : movieDetails.rating,
        reviewCount: source === 'api' ? movieDetails.imdbVotes : null,
        genre: source === 'api' ? movieDetails.Genre : movieDetails.categories.map(category => category.categoryName).join(', '),
        plot: source === 'api' ? movieDetails.Plot : movieDetails.description,
        release: source === 'api' ? movieDetails.Year : movieDetails.released,
        runtime: source === 'api' ? movieDetails.Runtime : null,
        awards: source === 'api' ? movieDetails.Awards : movieDetails.awards
    };

    return (
        <SidebarWithHeader>
            <Box maxW='xl' borderWidth='5px' borderRadius='lg' overflow='hidden' textAlign='center' mx={'auto'}>
                <Image src={property.imageUrl} alt={property.imageAlt} mx='auto' maxW={'250'} mt={4} />
                <Box p='4'>
                    <Box mb='2' fontWeight='semibold' as='h4' lineHeight='tight' noOfLines={1} justifyContent={'center'}>
                        {property.title} ({property.release})
                    </Box>
                    <Flex alignItems='center' justifyContent='center' mb={4}>
                        <Image
                            src={'https://png.pngtree.com/png-clipart/20221206/ourmid/pngtree-yellow-star-3d-icon-png-image_6511591.png'}
                            maxH={'6'} maxW={'9'} mr={1} />
                        <Text fontSize='xl'>{property.rating}</Text>
                    </Flex>
                    <Badge borderRadius='full' px='2' colorScheme='teal'>{property.genre}</Badge>
                    <Text color='gray.500' fontWeight='semibold' letterSpacing='wide' fontSize='xs' textTransform='uppercase' mt='2'>
                        &bull; Actors: {property.actors}
                    </Text>
                    <Text color='gray.500' fontWeight='semibold' letterSpacing='wide' fontSize='xs' textTransform='uppercase' mt='2'>
                        &bull; Directed by {property.director}
                    </Text>
                    <Box mt={5}>{property.plot}</Box>
                    <Text color='gray.500' fontWeight='semibold' letterSpacing='wide' fontSize='xs' textTransform='uppercase' mt={3}>
                        {property.awards}
                    </Text>
                    <Box mt={4}>
                        <Flex justifyContent="center" alignItems="center">
                            {source === 'database' && (
                                <Button leftIcon={<Image
                                    src={'https://png.pngtree.com/png-clipart/20221206/ourmid/pngtree-yellow-star-3d-icon-png-image_6511591.png'}
                                    maxH="4" maxW="4" />} colorScheme={isFavourite ? "red" : "yellow"} mr={4}
                                        onClick={toggleFavourite}>
                                    {isFavourite ? "Remove from Favourites" : "Add to Favourites"}
                                </Button>
                            )}
                            {source === 'api' && (
                                <Button colorScheme="blue" onClick={() => addToLibrary(movieDetails)}>
                                    Add to Library
                                </Button>
                            )}
                        </Flex>
                    </Box>
                </Box>
            </Box>
        </SidebarWithHeader>
    );
};

export default MovieDetails;
