import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Badge, Box, Button, Flex, Image, Text, Card, CardHeader, CardBody, Heading, Stack, StackDivider, Avatar, Textarea } from "@chakra-ui/react";
import SidebarWithHeader from "../shared/SiderBar.jsx";
import {
    fetchMovieFromDatabase,
    addToLibrary,
    addMovieToFavorites,
    removeMovieFromFavorites,
    getCommentsForMovie,
    addComment, deleteComment
} from "../../services/client.js";
import useMemberProfile from "../../services/useMemberProfile.js";

const API_URL = 'http://www.omdbapi.com?apikey=5f07f8b0';

const MovieDetails = () => {
    const { title, year, movieId } = useParams();
    const [movieDetails, setMovieDetails] = useState(null);
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState('');
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

    useEffect(() => {
        if (movieId) {
            const fetchComments = async () => {
                try {
                    const response = await getCommentsForMovie(movieId);
                    setComments(response.data);
                } catch (err) {
                    console.error('Failed to fetch comments:', err);
                }
            };

            fetchComments();
        }
    }, [movieId]);

    useEffect(() => {
        if (memberProfile && movieId) {
            const isFavoriteMovie = memberProfile.favoriteMovies.some(movie => movie.id === parseInt(movieId));
            setIsFavourite(isFavoriteMovie);
        }
    }, [memberProfile, movieId]);

    const toggleFavourite = async () => {
        try {
            const memberId = memberProfile?.id;
            if (!memberId) {
                console.error('Member ID not found.');
                return;
            }
            if (isFavourite) {
                await removeMovieFromFavorites(memberId, movieId);
            } else {
                await addMovieToFavorites(memberId, movieId);
            }
            setIsFavourite(!isFavourite);
        } catch (error) {
            console.error('Failed to update movie favourites:', error);
        }
    };

    const handleCommentChange = (e) => {
        setNewComment(e.target.value);
    };

    const handleCommentSubmit = async () => {
        try {
            if (newComment.trim() === '') {
                return;
            }
            const memberId = memberProfile?.id;
            if (!memberId) {
                console.error('Member ID not found.');
                return;
            }
            const commentData = {
                content: newComment,
                movieId: parseInt(movieId),
                memberId: memberId
            };
            await addComment(commentData);
            setNewComment('');
            const response = await getCommentsForMovie(movieId);
            setComments(response.data);
        } catch (error) {
            console.error('Failed to add comment:', error);
        }
    };

    const handleDeleteComment = async (commentId) => {
        try {
            await deleteComment(commentId); // You need to implement this function in your service layer
            const response = await getCommentsForMovie(movieId);
            setComments(response.data);
        } catch (error) {
            console.error('Failed to delete comment:', error);
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
        year: source === 'api' ? movieDetails.Year : movieDetails.year,
        runtime: source === 'api' ? movieDetails.Runtime : null,
        awards: source === 'api' ? movieDetails.Awards : movieDetails.awards
    };

    return (
        <SidebarWithHeader>
            <Box maxW='xl' borderWidth='5px' borderRadius='lg' overflow='hidden' textAlign='center' mx={'auto'}>
                <Image src={property.imageUrl} alt={property.imageAlt} mx='auto' maxW={'250'} mt={4} />
                <Box p='4'>
                    <Box mb='2' fontWeight='semibold' as='h4' lineHeight='tight' noOfLines={1} justifyContent={'center'}>
                        {property.title} ({property.year})
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

            {source === 'database' && (
                <Box maxW='xl' borderWidth='1px' borderRadius='lg' overflow='hidden' mt={5} mx={'auto'}>
                    <Card>
                        <CardHeader>
                            <Heading size='md'>Comments</Heading>
                        </CardHeader>
                        <CardBody>
                            <Stack divider={<StackDivider />} spacing='4'>
                                {comments.length === 0 ? (
                                    <Box>
                                        <Text>No comments available for this movie.</Text>
                                    </Box>
                                ) : (
                                    comments.map((comment) => (
                                        <Box key={comment.id} position="relative">
                                            <Text pt='1' fontSize='sm'>
                                                {comment.content}
                                            </Text>
                                            <Flex alignItems="center" mt={3}>
                                                <Avatar src={comment.member.imgUrl} size="xs" mr={2} />
                                                <Text color='gray.500' fontSize='xs'>
                                                    {comment.member.username} at {new Date(comment.createdAt).toLocaleDateString()}
                                                </Text>
                                                {(comment.member.id === memberProfile?.id || isAdmin) && (
                                                    <Button
                                                        size="xs"
                                                        colorScheme="red"
                                                        position="absolute"
                                                        top="0"
                                                        right="0"
                                                        onClick={() => handleDeleteComment(comment.id)}
                                                    >
                                                        X
                                                    </Button>
                                                )}
                                            </Flex>
                                        </Box>
                                    ))
                                )}
                            </Stack>
                        </CardBody>
                    </Card>

                    <Box mt={4} p={4} borderWidth='1px' borderRadius='lg'>
                        <Textarea
                            value={newComment}
                            onChange={handleCommentChange}
                            placeholder="Write your comment..."
                            size="sm"
                            mb={3}
                        />
                        <Button colorScheme="blue" size={"sm"} onClick={handleCommentSubmit}>
                            Submit
                        </Button>
                    </Box>
                </Box>
            )}
        </SidebarWithHeader>
    );
};

export default MovieDetails;
