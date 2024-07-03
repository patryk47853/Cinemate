import {
    Button, Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader, DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import UpdateMemberForm from "./UpdateMemberForm.jsx";

const UpdateMemberDrawer = ({ fetchMembers, initialValues, memberId, username}) => {
    const { isOpen, onOpen, onClose } = useDisclosure()

    return <>
        <Button colorScheme="blue" size="sm" onClick={onOpen}>
            View
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size="md">
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>{username}</DrawerHeader>

                <DrawerBody>
                    <UpdateMemberForm
                        fetchMembers={fetchMembers}
                        initialValues={initialValues}
                        memberId={memberId}
                        username={username}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button colorScheme='red' variant='solid'
                            onClick={onClose}>
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>
}

export default UpdateMemberDrawer;