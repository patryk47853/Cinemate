import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {Badge, Box, Button, Flex, Image, Text} from "@chakra-ui/react";
import SidebarWithHeader from "../shared/SiderBar.jsx";

const API_URL = `http://www.omdbapi.com?apikey=5f07f8b0`;

const MovieDetails = () => {
    const {title, year} = useParams();
    const [movieDetails, setMovieDetails] = useState(null);
    const [isFavourite, setIsFavourite] = useState(false);

    useEffect(() => {
        const fetchMovieDetails = async () => {
            try {
                const response = await fetch(`${API_URL}&t=${title}&y=${year}`);
                const data = await response.json();
                if (response.ok) {
                    setMovieDetails(data);
                } else {
                    throw new Error(data.Error);
                }
            } catch (err) {
                console.error('Failed to fetch movie details:', err);
                // Handle error
            }
        };

        fetchMovieDetails();

        // Cleanup function
        return () => {
            // Cleanup if necessary
        };
    }, [title, year]);

    const toggleFavourite = () => {
        setIsFavourite(!isFavourite);
    };

    if (!movieDetails) {
        return <div>Loading...</div>;
    }

    const property = {
        imageUrl: movieDetails.Poster,
        imageAlt: movieDetails.Title,
        actors: movieDetails.Actors,
        director: movieDetails.Director,
        title: movieDetails.Title,
        rating: movieDetails.imdbRating,
        reviewCount: movieDetails.imdbVotes,
        genre: movieDetails.Genre,
        plot: movieDetails.Plot,
        release: movieDetails.Year,
        runtime: movieDetails.Runtime,
        awards: movieDetails.Awards
    };

    return (
        <SidebarWithHeader>
            <Box maxW='xl' borderWidth='5px' borderRadius='lg' overflow='hidden' textAlign='center' mx={'auto'}>
                <Image src={property.imageUrl} alt={property.imageAlt} mx='auto' maxW={'250'} mt={4}/>
                <Box p='4'>
                    <Box mb='2' fontWeight='semibold' as='h4' lineHeight='tight' noOfLines={1}
                         justifyContent={'center'}>
                        {property.title} ({property.release})
                    </Box>
                    <Flex alignItems='center' justifyContent='center' mb={4}>
                        <Image
                            src={'https://png.pngtree.com/png-clipart/20221206/ourmid/pngtree-yellow-star-3d-icon-png-image_6511591.png'}
                            maxH={'6'} maxW={'9'} mr={1}/>
                        <Text fontSize='xl'>{property.rating}</Text>
                    </Flex>
                    <Badge borderRadius='full' px='2' colorScheme='teal'>{property.genre}</Badge>
                    <Text color='gray.500' fontWeight='semibold' letterSpacing='wide' fontSize='xs'
                          textTransform='uppercase' mt='2'>&bull; Actors: {property.actors}</Text>
                    <Text color='gray.500' fontWeight='semibold' letterSpacing='wide' fontSize='xs'
                          textTransform='uppercase' mt='2'>&bull; Directed by {property.director}</Text>
                    <Box mt={5}>{property.plot}</Box>
                    <Text color='gray.500' fontWeight='semibold' letterSpacing='wide' fontSize='xs'
                          textTransform='uppercase' mt={3}>{property.awards}</Text>
                    <Box mt={4}>
                        <Flex justifyContent="center" alignItems="center">
                            <Button leftIcon={<Image
                                src={'https://png.pngtree.com/png-clipart/20221206/ourmid/pngtree-yellow-star-3d-icon-png-image_6511591.png'}
                                maxH="4" maxW="4"/>} colorScheme={isFavourite ? "red" : "yellow"} mr={4}
                                    onClick={toggleFavourite}>
                                {isFavourite ? "Remove from Favourites" : "Add to Favourites"}
                            </Button>
                            <Button colorScheme="blue">Add to Library</Button>
                        </Flex>
                    </Box>
                </Box>
            </Box>
        </SidebarWithHeader>
    );
};

export default MovieDetails;
