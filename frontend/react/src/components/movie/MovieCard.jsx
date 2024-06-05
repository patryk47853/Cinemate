import React from 'react';
import { Box, Center, Image, Stack, Tag, Heading, useColorModeValue, Button, Tooltip } from "@chakra-ui/react";
import { Link } from 'react-router-dom';

const MovieCard = ({ movie: { Year, Poster, Title, imdbID}, source }) => {
    const maxTitleLength = 25;

    return (
        <Center py={12}>
            <Box
                role={'group'}
                p={3}
                maxW={'220px'}
                w={'full'}
                h={'340px'}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'lg'}
                pos={'relative'}
                zIndex={1}
            >
                <Box
                    rounded={'lg'}
                    mt={-20}
                    pos={'relative'}
                    height={'222px'}
                    _after={{
                        transition: 'all .3s ease',
                        content: '""',
                        w: 'full',
                        h: 'full',
                        pos: 'absolute',
                        top: 6,
                        left: 0,
                        backgroundImage: `url(${Poster})`,
                        filter: 'blur(15px)',
                        zIndex: -1,
                    }}
                    _groupHover={{
                        _after: {
                            filter: 'blur(10px)',
                        },
                    }}
                >
                    <Image
                        rounded={'lg'}
                        height={250}
                        width={280}
                        objectFit={'cover'}
                        src={Poster !== "N/A" ? Poster : "https://via.placeholder.com/800"}
                        alt={Title}
                    />
                </Box>
                <Box p={5} position="relative" height="100%">
                    <Stack pt={4} align={'center'} p={4}>
                        <Tag size='md' colorScheme='blue' borderRadius='full'>
                            Year: {Year}
                        </Tag>
                        <Tooltip label={Title} aria-label="Title tooltip">
                            <Heading
                                fontSize={'medium'}
                                fontFamily={'body'}
                                fontWeight={600}
                                _hover={{ textDecoration: "underline", cursor: "pointer" }}
                                textAlign={"center"}
                            >
                                {Title.length > maxTitleLength ? `${Title.slice(0, maxTitleLength)}...` : Title}
                            </Heading>
                        </Tooltip>
                    </Stack>
                </Box>
                <Stack mt="auto" align="center" position="absolute" bottom={2} left={0} right={0}>
                    <Link to={source === 'api' ? `/search/${Title}/${Year}` : `/movies/${imdbID}`}>
                        <Button colorScheme={"purple"} size={"md"}>
                            Details
                        </Button>
                    </Link>
                </Stack>
            </Box>
        </Center>
    );
}

export default MovieCard;
