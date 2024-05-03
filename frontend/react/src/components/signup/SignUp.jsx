import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import React, {useEffect} from "react";
import {Flex, Heading, Image, Link, Stack} from "@chakra-ui/react";
import CreateMemberForm from "../shared/CreateMemberForm.jsx";

const SignUp = () => {
    const { member } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if(member) {
            navigate("/home/members");
        }
    });

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Image
                        src={"https://raw.githubusercontent.com/patryk47853/Cinemate/master/screenshots/cinemate_logo.png"}
                        boxSize={"150px"}
                        alt={"Cinemate Logo"}
                        mx="auto"
                    />
                    <Heading fontSize={'2xl'} mb={15}>Sign Up</Heading>
                    <CreateMemberForm/>
                    <Link href={"/"}>
                        Have an account? <span style={{ color: "mediumblue" }}>Sign in</span>
                    </Link>
                </Stack>
            </Flex>
            <Flex flex={1}>
                <Image
                    alt={'Login Image'}
                    objectFit={'scale-down'}
                    src={
                        'https://image.tmdb.org/t/p/original/hxcHfW0o5QhY6slqGc7dkEkvo6U.jpg'}
                />
            </Flex>
        </Stack>
    );
}

export default SignUp;
