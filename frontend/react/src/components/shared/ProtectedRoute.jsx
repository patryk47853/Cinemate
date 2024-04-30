import {useEffect} from "react";
import {useNavigate} from "react-router-dom";

const ProtectedRoute = ({ children }) => {
    const isMemberAuthenticated = false;
    const navigate = useNavigate();

    useEffect(() => {
        if(!isMemberAuthenticated) {
            navigate("/")
        }
    })

    return isMemberAuthenticated ? children : "";
}

export default ProtectedRoute;