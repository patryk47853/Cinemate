import React from 'react';
import { Box, Center, Image, Stack, Tag, Heading, useColorModeValue } from "@chakra-ui/react";

const MovieCard = ({ movie: { Year, Poster, Title } }) => {
    return (
        <Center py={7}>
            <Box
                role={'group'}
                p={3}
                maxW={'160px'}
                w={'full'}
                h={'240px'}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'lg'}
                pos={'relative'}
                zIndex={1}
            >
                <Box
                    rounded={'lg'}
                    mt={-12}
                    pos={'relative'}
                    height={'120px'}
                    _after={{
                        transition: 'all .3s ease',
                        content: '""',
                        w: 'full',
                        h: 'full',
                        pos: 'absolute',
                        top: 3,
                        left: 0,
                        backgroundImage: `url(${Poster})`,
                        filter: 'blur(20px)',
                        zIndex: -1,
                    }}
                    _groupHover={{
                        _after: {
                            filter: 'blur(20px)',
                        },
                    }}
                >
                    <Image
                        rounded={'lg'}
                        height={135}
                        width={140}
                        objectFit={'cover'}
                        src={Poster !== "N/A" ? Poster : "https://via.placeholder.com/400"}
                        alt={Title}
                    />
                </Box>
                <Box p={5}>
                    <Stack pt={4} align={'center'} p={3}>
                        <Tag size='md' colorScheme='blue' borderRadius='full'>
                            Year: {Year}
                        </Tag>
                        <Heading fontSize={'l'} fontFamily={'body'} fontWeight={500}>
                            {Title}
                        </Heading>
                    </Stack>
                </Box>
            </Box>
        </Center>
    );
}

export default MovieCard;