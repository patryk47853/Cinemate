import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
import { Alert, AlertIcon, Box, Button, FormLabel, Input, Stack } from "@chakra-ui/react";
import React from "react";
import { updateMember } from "../../services/client.js";
import { errorNotification, successNotification } from "../../services/notification.js";

const MyTextInput = ({ label, isAdmin, ...props }) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input {...field} {...props} readOnly={!isAdmin}/>
            {meta.touched && meta.error ? (
                <Alert status="error" mt={2}>
                    <AlertIcon />
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const UpdateMemberForm = ({ fetchMembers, initialValues, memberId, isAdmin }) => {
    return (
        <Formik
            initialValues={initialValues}
            validationSchema={Yup.object({
                username: Yup.string()
                    .max(15, 'Must be 15 characters or less')
                    .required('Required'),
                email: Yup.string()
                    .email('Invalid email address')
                    .required('Required')
            })}
            onSubmit={(updatedMember, { setSubmitting }) => {
                updateMember(memberId, updatedMember)
                    .then(res => {
                        successNotification(
                            "Member updated",
                            `${updatedMember.username} was successfully updated`
                        );
                        fetchMembers();
                    })
                    .catch(err => {
                        errorNotification(
                            err.code,
                            err.response.data.message
                        );
                    })
                    .finally(() => {
                        setSubmitting(false);
                    });
            }}
        >
            {({ isValid, isSubmitting, dirty }) => (
                <Form>
                    <Stack spacing="24px">
                        <MyTextInput
                            label="Username:"
                            name="username"
                            type="text"
                            placeholder="Username"
                        />

                        <MyTextInput
                            label="E-mail:"
                            name="email"
                            type="email"
                            placeholder="E-mail"
                        />

                        {isAdmin && (
                            <Button
                                type="submit"
                                disabled={!dirty || !isValid || isSubmitting}
                            >
                                Submit
                            </Button>
                        )}
                    </Stack>
                </Form>
            )}
        </Formik>
    );
};

export default UpdateMemberForm;
