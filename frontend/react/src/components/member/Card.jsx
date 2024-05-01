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
    Image,
    Spacer,
    useDisclosure,
    AlertDialogFooter,
    AlertDialogBody,
    AlertDialogHeader,
    AlertDialogContent,
    AlertDialog, AlertDialogOverlay,
} from '@chakra-ui/react'

import {useRef} from "react";
import {deleteMember} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import UpdateMemberDrawer from "./UpdateMemberDrawer.jsx";

export default function Card({id, username, email, imgUrl, fetchMembers}) {
    const IMAGE = imgUrl;
    const {isOpen, onOpen, onClose} = useDisclosure()
    const cancelRef = useRef()

    return (
        <Center py={12}>
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
                        top: 3,
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
                <Box p={5}>
                <Stack pt={4} align={'center'} p={3}>
                    <Tag size='md' colorScheme='blue' borderRadius='full'>
                        ID: {id}
                    </Tag>
                    <Heading fontSize={'l'} fontFamily={'body'} fontWeight={500}>
                        @{username}
                    </Heading>
                </Stack>
                </Box>
                <Stack direction={'row'} justify={'center'} spacing={2}>
                    <Stack>
                        <UpdateMemberDrawer initialValues={{username, email}}
                                            memberId={id}
                                            fetchMembers={fetchMembers}
                        />
                    </Stack>
                    <Stack>
                        <Button colorScheme="red" size="sm" onClick={onOpen}>
                            Delete
                        </Button>

                        <AlertDialog
                            isOpen={isOpen}
                            leastDestructiveRef={cancelRef}
                            onClose={onClose}
                        >
                            <AlertDialogOverlay>
                                <AlertDialogContent>
                                    <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                                        Delete member: @{username}
                                    </AlertDialogHeader>

                                    <AlertDialogBody>
                                        Are you sure? You can't undo this action afterwards.
                                    </AlertDialogBody>

                                    <AlertDialogFooter>
                                        <Button ref={cancelRef} onClick={onClose}>
                                            Cancel
                                        </Button>
                                        <Button colorScheme='red' onClick={() => {
                                            deleteMember(id).then(res => {
                                                console.log(res)
                                                successNotification(
                                                    'Member deleted',
                                                    `${username} was successfully deleted`
                                                )
                                                fetchMembers()
                                            }).catch(err => {
                                                console.log(err)
                                                errorNotification(
                                                    err.code,
                                                    err.response.data.message
                                                )
                                            }).finally(() => {
                                                onClose()
                                            })
                                        }} ml={3}>
                                            Delete
                                        </Button>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialogOverlay>
                        </AlertDialog>
                    </Stack>
                </Stack>
            </Box>
        </Center>
    )
}