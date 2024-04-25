import {Formik, Form, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import React from "react";
import {saveMember, updateMember} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";

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

// And now we can use these
const UpdateMemberForm = ({fetchMembers, initialValues, memberId}) => {
    return (
        <>
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
                onSubmit={(updatedMember, {setSubmitting}) => {
                    setSubmitting(true)
                    updateMember(memberId, updatedMember)
                        .then(res => {
                            console.log(res)
                            successNotification(
                                "Member updated",
                                `${updatedMember.username} was successfully updated`
                            )
                            fetchMembers();
                        }).catch(err => {
                        console.log(err)
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}
            >
                {({isValid, isSubmitting, dirty}) => (
                    <Form>
                        <Stack spacing={"24px"}>
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
                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default UpdateMemberForm;