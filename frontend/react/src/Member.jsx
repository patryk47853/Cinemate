import React, {useState, useEffect} from "react";
import {Wrap, WrapItem, Spinner, Text, Button, Input, Flex, useDisclosure} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/SiderBar.jsx";
import {getMembers} from "./services/client.js";
import Card from "./components/member/Card.jsx";
import CreateMemberDrawer from "./components/member/CreateMemberDrawer.jsx";
import {errorNotification} from "./services/notification.js";

const Member = () => {
    const [members, setMembers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");
    const [searchTerm, setSearchTerm] = useState("");
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);

    const fetchMembers = () => {
        setLoading(true);
        getMembers()
            .then(res => {
                setMembers(res.data);
            })
            .catch(err => {
                setError(err.response.data.message);
                errorNotification(
                    err.code,
                    err.response.data.message
                );
            })
            .finally(() => {
                setLoading(false);
            });
    };

    useEffect(() => {
        fetchMembers();
    }, []);

    const openDrawer = () => {
        setIsDrawerOpen(true);
    };

    const closeDrawer = () => {
        setIsDrawerOpen(false);
    };


    const handleSearch = () => {
        if (searchTerm.trim() === "") {
            fetchMembers();
        } else {
            const filteredMembers = members.filter(member =>
                member.username.toLowerCase().includes(searchTerm.toLowerCase())
            );
            setMembers(filteredMembers);
        }
    };

    return (
        <SidebarWithHeader>
            <CreateMemberDrawer fetchMembers={fetchMembers}/>
            <Flex align="center" mb={9}>
                <Input
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    placeholder="Search for members"
                    size="md"
                />
                <Button ml={3} colorScheme="blue" onClick={handleSearch}>
                    Search
                </Button>
                <Button ml={3} colorScheme="green" onClick={openDrawer}>
                    + Member
                </Button>
            </Flex>
            <CreateMemberDrawer
                fetchMembers={fetchMembers}
                isOpen={isDrawerOpen}
                onClose={closeDrawer}
            />
            {loading && <Spinner/>}
            {err && <Text mt={5}>Error: {err}</Text>}
            {!loading && !err && members.length === 0 && (
                <Text mt={5}>No members found</Text>
            )}
            {!loading && !err && members.length > 0 && (
                <Wrap spacing={"30px"}>
                    {members.map((member) => (
                        <WrapItem key={member.id}>
                            <Card {...member} fetchMembers={fetchMembers}/>
                        </WrapItem>
                    ))}
                </Wrap>
            )}
        </SidebarWithHeader>
    );
};

export default Member;
