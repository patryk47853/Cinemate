'use client'

import {
    Box,
    Button,
    Center,
    useColorModeValue,
    Heading,
    Text,
    Tag,
    Stack,
    Image, Spacer,
} from '@chakra-ui/react'

export default function Card({id, username, imgUrl}) {
    const IMAGE = imgUrl;

    return (
        <Center py={12}>
            <Box
                role={'group'}
                p={3}
                maxW={'160px'}
                w={'full'}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'lg'}
                pos={'relative'}
                zIndex={1}>
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
                        top: 5,
                        left: 0,
                        backgroundImage: `url(${IMAGE})`,
                        filter: 'blur(20px)',
                        zIndex: -1,
                    }}
                    _groupHover={{
                        _after: {
                            filter: 'blur(20px)',
                        },
                    }}>
                    <Image
                        rounded={'lg'}
                        height={135}
                        width={140}
                        objectFit={'cover'}
                        src={IMAGE}
                        alt="#"
                    />
                </Box>
                <Stack pt={9} align={'center'}>
                    <Tag size='md' colorScheme='blue' borderRadius='full'>
                        ID: {id}
                    </Tag>
                    <Heading fontSize={'l'} fontFamily={'body'} fontWeight={500}>
                        @{username}
                    </Heading>
                    <Spacer/>
                    <Spacer/>
                    <Stack direction={'row'} align={'center'}>
                        <Button colorScheme="blue" variant="outline" size="md">
                            View
                        </Button>
                    </Stack>
                </Stack>
            </Box>
        </Center>
    )
}