import { Wrap, WrapItem, Spinner, Text } from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/SiderBar.jsx";
import { useEffect, useState } from "react";
import { getMembers } from "./services/client.js";
import Card from "./components/Card.jsx";
import DrawerForm from "./components/DrawerForm.jsx";
import {errorNotification} from "./services/notification.js";

const App = () => {
    const [members, setMembers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchMembers = () => {
        setLoading(true);
        getMembers()
            .then(res => {
                setMembers(res.data);
            })
            .catch(err => {
                setError(err.response.data.message)
                errorNotification(
                    err.code,
                    err.response.data.message
                )
            })
            .finally(() => {
                setLoading(false);
            });
    };

    useEffect(() => {
        fetchMembers();
    }, []);

    if (loading) {
        return (
            <SidebarWithHeader>
                <Spinner />
            </SidebarWithHeader>
        );
    }

    if(err) {
        return (
            <SidebarWithHeader>
                <DrawerForm fetchMembers={fetchMembers} />
                <Text mt={5}>There was an error, please try again</Text>
            </SidebarWithHeader>
        );
    }

    if (members.length <= 0) {
        return (
            <SidebarWithHeader>
                <DrawerForm fetchMembers={fetchMembers} />
                <Text mt={5}>No members available!</Text>
            </SidebarWithHeader>
        );
    }

    return (
        <SidebarWithHeader>
            <DrawerForm fetchMembers={fetchMembers} />
            <Wrap spacing={"30px"}>
                {members.map((member, index) => (
                    <WrapItem key={index}>
                        <Card {...member} />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    );
};

export default App;