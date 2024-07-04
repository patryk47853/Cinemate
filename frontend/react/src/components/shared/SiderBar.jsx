import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import {
    Avatar,
    Box,
    CloseButton,
    Drawer,
    DrawerContent,
    Flex,
    HStack,
    Icon,
    IconButton,
    Menu,
    MenuButton,
    MenuDivider,
    MenuItem,
    MenuList,
    Text,
    useColorModeValue,
    useDisclosure,
    VStack,
    Image
} from '@chakra-ui/react';

import {
    FiBell,
    FiChevronDown,
    FiHome, FiImage,
    FiMenu, FiSearch,
    FiStar, FiUsers
} from 'react-icons/fi';
import { useAuth } from "../context/AuthContext.jsx";
import { getMemberProfile } from "../../services/client.js";
import { errorNotification } from "../../services/notification.js";

const LinkItems = [
    { name: 'Home', icon: FiHome, to: '/home' },
    { name: 'Movies', icon: FiImage, to: '/movies' },
    { name: 'Favorites', icon: FiStar, to: '/favorites' },
    { name: 'Community', icon: FiUsers, to: '/members' },
];

export default function SidebarWithHeader({ children }) {
    const { isOpen, onOpen, onClose } = useDisclosure();
    return (
        <Box minH="100vh" bg={useColorModeValue('gray.100', 'gray.900')}>
            <SidebarContent onClose={() => onClose} display={{ base: 'none', md: 'block' }} />
            <Drawer
                autoFocus={false}
                isOpen={isOpen}
                placement="left"
                onClose={onClose}
                returnFocusOnClose={false}
                onOverlayClick={onClose}
                size="full"
            >
                <DrawerContent>
                    <SidebarContent onClose={onClose} />
                </DrawerContent>
            </Drawer>
            <MobileNav onOpen={onOpen} />
            <Box ml={{ base: 0, md: 60 }} p="4">
                {children}
            </Box>
        </Box>
    );
}

const SidebarContent = ({ onClose, ...rest }) => {
    const { isAdmin } = useAuth();
    return (
        <Box
            transition="3s ease"
            bg={useColorModeValue('white', 'gray.900')}
            borderRight="1px"
            borderRightColor={useColorModeValue('gray.200', 'gray.700')}
            w={{ base: 'full', md: 60 }}
            pos="fixed"
            h="full"
            {...rest}
        >
            <Flex h="90" alignItems="center" mx="4" justifyContent="space-between">
                <Image
                    mt={1}
                    boxSize="90px"
                    src="https://raw.githubusercontent.com/patryk47853/Cinemate/master/screenshots/logo.png"
                    maxH={"70px"}
                    mx="auto"
                />
                <CloseButton display={{ base: 'flex', md: 'none' }} onClick={onClose} />
            </Flex>
            {LinkItems.map((link, index) => {
                if (link.name === 'Movies' && isAdmin()) {
                    return (
                        <React.Fragment key={link.name}>
                            <Link key="Explore" to="/search" style={{ textDecoration: 'none' }}>
                                <NavItem icon={FiSearch}>
                                    Explore
                                </NavItem>
                            </Link>
                            <Link key={link.name} to={link.to} style={{ textDecoration: 'none' }}>
                                <NavItem icon={link.icon}>
                                    {link.name}
                                </NavItem>
                            </Link>
                        </React.Fragment>
                    );
                }
                return (
                    <Link key={link.name} to={link.to} style={{ textDecoration: 'none' }}>
                        <NavItem icon={link.icon}>
                            {link.name}
                        </NavItem>
                    </Link>
                );
            })}
        </Box>
    );
};

const NavItem = ({ icon, children, ...rest }) => {
    return (
        <Flex
            align="center"
            p="4"
            mx="4"
            borderRadius="lg"
            role="group"
            cursor="pointer"
            _hover={{
                bg: 'cyan.400',
                color: 'white',
            }}
            {...rest}
        >
            {icon && (
                <Icon
                    mr="4"
                    fontSize="16"
                    _groupHover={{
                        color: 'white',
                    }}
                    as={icon}
                />
            )}
            {children}
        </Flex>
    );
};

const MobileNav = ({ onOpen, ...rest }) => {
    const { logout, member } = useAuth();
    const [userProfile, setUserProfile] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        if (member) {
            fetchMemberProfile(member.username);
        }
    }, [member]);

    const fetchMemberProfile = (username = member.username) => {
        setLoading(true);
        getMemberProfile(username)
            .then((res) => {
                setUserProfile(res.data);
            })
            .catch((err) => {
                if (err.response && err.response.data) {
                    setError(err.response.data.message);
                    errorNotification(err.code, err.response.data.message);
                } else {
                    setError("An error occurred while fetching user profile.");
                    errorNotification("Error", "An error occurred while fetching user profile.");
                }
            })
            .finally(() => {
                setLoading(false);
            });
    };

    return (
        <Flex
            ml={{ base: 0, md: 60 }}
            px={{ base: 4, md: 4 }}
            height="20"
            alignItems="center"
            bg={useColorModeValue('white', 'gray.900')}
            borderBottomWidth="1px"
            borderBottomColor={useColorModeValue('gray.200', 'gray.700')}
            justifyContent={{ base: 'space-between', md: 'flex-end' }}
            {...rest}
        >
            <IconButton
                display={{ base: 'flex', md: 'none' }}
                onClick={onOpen}
                variant="outline"
                aria-label="open menu"
                icon={<FiMenu />}
            />

            <Text display={{ base: 'flex', md: 'none' }} fontSize="2xl" fontFamily="monospace" fontWeight="bold">
                Logo
            </Text>

            <HStack spacing={{ base: '0', md: '6' }}>
                <IconButton size="lg" variant="ghost" aria-label="open menu" icon={<FiBell />} />
                <Flex alignItems={'center'}>
                    <Menu>
                        <MenuButton py={2} transition="all 0.3s" _focus={{ boxShadow: 'none' }}>
                            <HStack>
                                {userProfile && userProfile.imgUrl && (
                                    <Avatar size={'sm'} src={userProfile.imgUrl} />
                                )}
                                <VStack display={{ base: 'none', md: 'flex' }} alignItems="flex-start" spacing="1px" ml="2">
                                    <Text fontSize="sm">{member?.username}</Text>
                                </VStack>
                                <Box display={{ base: 'none', md: 'flex' }}>
                                    <FiChevronDown />
                                </Box>
                            </HStack>
                        </MenuButton>
                        <MenuList
                            bg={useColorModeValue('white', 'gray.900')}
                            borderColor={useColorModeValue('gray.200', 'gray.700')}
                        >
                            <MenuItem>Profile</MenuItem>
                            <MenuDivider />
                            <MenuItem onClick={logout}>Sign out</MenuItem>
                        </MenuList>
                    </Menu>
                </Flex>
            </HStack>
        </Flex>
    );
};
