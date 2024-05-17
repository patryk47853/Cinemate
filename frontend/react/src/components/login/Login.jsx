import {
    Button,
    Checkbox,
    Flex,
    FormControl,
    FormLabel,
    Heading,
    Input,
    Link,
    Stack,
    Image, Box, Alert, AlertIcon,
} from '@chakra-ui/react';
import {Form, Formik, useField} from "formik";
import * as Yup from 'yup';
import React, {useEffect} from "react";
import {useAuth} from "../context/AuthContext.jsx";
import {errorNotification} from "../../services/notification.js";
import {useNavigate} from "react-router-dom";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const LoginForm = () => {
    const {login} = useAuth();
    const navigate = useNavigate();

    return (
        <Formik
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    username: Yup.string()
                        .required("E-mail is required"),
                    password: Yup.string()
                        .max(30, "Password can't be more than 30 characters")
                        .required("Password is required")
                })
            }
            initialValues={{username: '', password: ''}}
            onSubmit={(values, {setSubmitting}) => {
                setSubmitting(true);
                login(values).then(res => {
                    navigate("/home")
                    console.log("Successfully logged in: ");
                }).catch(err => {
                    errorNotification(
                        err.code,
                        err.response.data.message
                    )
                }).finally(() => {
                    setSubmitting(false);
                })
            }}>

            {({isValid, isSubmitting}) => (
                <Form>
                    <Stack spacing={5}>
                        <MyTextInput
                            label={"Username:"}
                            name={"username"}
                            type={"username"}
                            placeholder={"Username"}
                        />
                        <MyTextInput
                            label={"Password:"}
                            name={"password"}
                            type={"password"}
                            placeholder={"********"}
                        />

                        <Button type={"submit"} disabled={!isValid || !isSubmitting}>
                            Login
                        </Button>
                    </Stack>
                </Form>
            )}

        </Formik>
    )
}

const Login = () => {

    const { member } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if(member) {
            navigate("/home");
        }
    });

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'} mb={110}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Image
                        src={"https://raw.githubusercontent.com/patryk47853/Cinemate/master/screenshots/logo4.png"}
                        boxSize={"150px"}
                        maxH={"130px"}
                        maxW={"200px"}
                        alt={"Cinemate Logo"}
                        mx="auto"
                    />
                    <Heading fontSize={'2xl'} mb={15}>Sign In</Heading>
                    <LoginForm/>
                    <Link href={"/sign-up"}>
                        Don't have an account? <span style={{ color: "mediumblue" }}>Sign up</span>
                    </Link>
                </Stack>
            </Flex>
            <Flex flex={1}>
                <Image
                    alt={'Login Image'}
                    objectFit={'cover'}
                    src={
                        'https://www.metrocinema.org/wp-content/uploads/2019/08/wp1945897.jpg'}
                />
            </Flex>
        </Stack>
    );
}

export default Login;