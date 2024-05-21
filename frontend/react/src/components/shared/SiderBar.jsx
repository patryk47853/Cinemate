import React, {useState, useEffect} from 'react';
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
    FiCompass,
    FiHome,
    FiMenu, FiSearch,
    FiSettings,
    FiStar,
    FiTrendingUp, FiUsers
} from 'react-icons/fi';
import {getUserProfile, useAuth} from "../context/AuthContext.jsx";
import {getMemberProfile, getMembers} from "../../services/client.js";
import {errorNotification} from "../../services/notification.js";

const LinkItems = [
    { name: 'Home', icon: FiHome, to: '/home' },
    { name: 'Explore', icon: FiSearch, to: '/search' },
    { name: 'Favourites', icon: FiStar, to: '/favourites' },
    { name: 'Community', icon: FiUsers, to: '/members' },
];

export default function SidebarWithHeader({children}) {
    const {isOpen, onOpen, onClose} = useDisclosure();
    return (
        <Box minH="100vh" bg={useColorModeValue('gray.100', 'gray.900')}>
            <SidebarContent
                onClose={() => onClose}
                display={{base: 'none', md: 'block'}}
            />
            <Drawer
                autoFocus={false}
                isOpen={isOpen}
                placement="left"
                onClose={onClose}
                returnFocusOnClose={false}
                onOverlayClick={onClose}
                size="full">
                <DrawerContent>
                    <SidebarContent onClose={onClose}/>
                </DrawerContent>
            </Drawer>
            {/* mobilenav */}
            <MobileNav onOpen={onOpen}/>
            <Box ml={{base: 0, md: 60}} p="4">
                {children}
            </Box>
        </Box>
    );
}

const SidebarContent = ({onClose, ...rest}) => {
    return (
        <Box
            transition="3s ease"
            bg={useColorModeValue('white', 'gray.900')}
            borderRight="1px"
            borderRightColor={useColorModeValue('gray.200', 'gray.700')}
            w={{base: 'full', md: 60}}
            pos="fixed"
            h="full"
            {...rest}>
            <Flex h="90" alignItems="center" mx="4" justifyContent="space-between">
                <Image
                    mt={1}
                    boxSize='90px'
                    src='https://raw.githubusercontent.com/patryk47853/Cinemate/master/screenshots/logo.png'
                    maxH={"70px"}
                    mx='auto'
                />
                <CloseButton display={{ base: 'flex', md: 'none' }} onClick={onClose} />
            </Flex>
            {LinkItems.map((link) => (
                <Link key={link.name} to={link.to} style={{ textDecoration: 'none' }}>
                    <NavItem icon={link.icon}>
                        {link.name}
                    </NavItem>
                </Link>
            ))}
        </Box>
    );
};

const NavItem = ({icon, children, ...rest}) => {
    return (
        // <Link href="frontend/react/src/components/shared#" style={{textDecoration: 'none'}} _focus={{boxShadow: 'none'}}>
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
                {...rest}>
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
        // </Link>
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
            .then(res => {
                setUserProfile(res.data);
            })
            .catch(err => {
                if (err.response && err.response.data) {
                    setError(err.response.data.message);
                    errorNotification(
                        err.code,
                        err.response.data.message
                    );
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
            {...rest}>
            <IconButton
                display={{ base: 'flex', md: 'none' }}
                onClick={onOpen}
                variant="outline"
                aria-label="open menu"
                icon={<FiMenu />}
            />

            <Text
                display={{ base: 'flex', md: 'none' }}
                fontSize="2xl"
                fontFamily="monospace"
                fontWeight="bold">
                Logo
            </Text>

            <HStack spacing={{ base: '0', md: '6' }}>
                <IconButton
                    size="lg"
                    variant="ghost"
                    aria-label="open menu"
                    icon={<FiBell />}
                />
                <Flex alignItems={'center'}>
                    <Menu>
                        <MenuButton
                            py={2}
                            transition="all 0.3s"
                            _focus={{ boxShadow: 'none' }}>
                            <HStack>
                                {userProfile && userProfile.imgUrl && (
                                    <Avatar
                                        size={'sm'}
                                        src={userProfile.imgUrl}
                                    />
                                )}
                                <VStack
                                    display={{ base: 'none', md: 'flex' }}
                                    alignItems="flex-start"
                                    spacing="1px"
                                    ml="2">
                                    <Text fontSize="sm">{member?.username}</Text>
                                </VStack>
                                <Box display={{ base: 'none', md: 'flex' }}>
                                    <FiChevronDown />
                                </Box>
                            </HStack>
                        </MenuButton>
                        <MenuList
                            bg={useColorModeValue('white', 'gray.900')}
                            borderColor={useColorModeValue('gray.200', 'gray.700')}>
                            <MenuItem>Profile</MenuItem>
                            <MenuDivider />
                            <MenuItem onClick={logout}>
                                Sign out
                            </MenuItem>
                        </MenuList>
                    </Menu>
                </Flex>
            </HStack>
        </Flex>
    );
};
