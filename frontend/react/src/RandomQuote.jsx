import React, { useState, useEffect } from "react";
import {Box, Button, Card, CardBody, CardFooter, Heading, Stack, Text, Image} from "@chakra-ui/react";
import SidebarWithHeader from "./components/shared/SiderBar.jsx";

const RandomQuote = () => {
    const [quote, setQuote] = useState("");
    const [actor, setActor] = useState("");
    const [quoteFrom, setQuoteFrom] = useState("");

    useEffect(() => {
        const fetchRandomQuote = async () => {
            const url = 'https://movie-and-tv-shows-quotes.p.rapidapi.com/quotes/random/quote';
            const options = {
                method: 'GET',
                headers: {
                    'X-RapidAPI-Key': '66508187a8msh4c698168924e38ap1d8e3bjsn9a5fc201b135',
                    'X-RapidAPI-Host': 'movie-and-tv-shows-quotes.p.rapidapi.com'
                }
            };

            try {
                const response = await fetch(url, options);
                const data = await response.json();
                setQuote(data.quote);
                setActor(data.actor);
                setQuoteFrom(data.from);
            } catch (error) {
                console.error(error);
            }
        };

        fetchRandomQuote();
    }, []);

    return (
        <SidebarWithHeader>
        <Card
            direction={{ base: 'column', sm: 'row' }}
            overflow='hidden'
            variant='outline'
        >
            <Image
                objectFit='cover'
                maxW={{ base: '100%', sm: '200px' }}
                src='https://images.unsplash.com/photo-1667489022797-ab608913feeb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw5fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=800&q=60'
                alt='Caffe Latte'
            />

            <Stack>
                <CardBody>
                    <Heading size='md'></Heading>

                    <Text fontSize="lg" fontWeight="bold" mb="2">
                        Quote of the Day
                    </Text>
                    <hr />
                </CardBody>

                <CardFooter>
                    <Button variant='solid' colorScheme='blue'>
                        <Text>"{quote}"</Text>
                        {actor && (
                            <Text fontSize="sm" fontStyle="italic" mt="2">
                                - {actor} in {quoteFrom}
                            </Text>
                        )}
                    </Button>
                </CardFooter>
            </Stack>
        </Card>
        </SidebarWithHeader>
    );
};

export default RandomQuote;
