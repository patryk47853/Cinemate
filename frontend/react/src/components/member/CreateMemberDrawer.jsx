import React, { useState } from "react";
import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay
} from "@chakra-ui/react";
import CreateMemberForm from "../shared/CreateMemberForm.jsx";

const CreateMemberDrawer = ({ fetchMembers, isOpen, onClose }) => {
    return (
        <Drawer isOpen={isOpen} onClose={onClose} size="md">
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Create new account</DrawerHeader>

                <DrawerBody>
                    <CreateMemberForm fetchMembers={fetchMembers} />
                </DrawerBody>

                <DrawerFooter>
                    <Button colorScheme="red" variant="solid" onClick={onClose}>
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    );
};

export default CreateMemberDrawer;
