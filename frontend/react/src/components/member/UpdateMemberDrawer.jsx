import {
    Button, Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader, DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import CreateMemberForm from "./CreateMemberForm.jsx";
import UpdateMemberForm from "./UpdateMemberForm.jsx";

const AddIcon = () => "+";
const UpdateMemberDrawer = ({ fetchMembers, initialValues, memberId}) => {
    const { isOpen, onOpen, onClose } = useDisclosure()

    return <>
        <Button colorScheme="blue" size="sm" onClick={onOpen}>
            View
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size="md">
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Update member</DrawerHeader>

                <DrawerBody>
                    <UpdateMemberForm
                        fetchMembers={fetchMembers}
                        initialValues={initialValues}
                        memberId={memberId}
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