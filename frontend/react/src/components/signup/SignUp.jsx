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
            navigate("/home");
        }
    });

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Image
                        src={"https://raw.githubusercontent.com/patryk47853/Cinemate/master/screenshots/logo4.png"}
                        boxSize={"150px"}
                        maxH={"130px"}
                        maxW={"200px"}
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
                    src={
                        'https://www.metrocinema.org/wp-content/uploads/2019/08/wp1945897.jpg'}
                />
            </Flex>
        </Stack>
    );
}

export default SignUp;
