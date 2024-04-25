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

const AddIcon = () => "+";
const CreateMemberDrawer = ({ fetchMembers }) => {
    const { isOpen, onOpen, onClose } = useDisclosure()

    return <>
        <Button colorScheme='blue' variant='solid'
            leftIcon={<AddIcon/>}
            onClick={onOpen}
        >
            Add member
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size="md">
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Create new account</DrawerHeader>

                <DrawerBody>
                    <CreateMemberForm
                        fetchMembers={fetchMembers}
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

export default CreateMemberDrawer