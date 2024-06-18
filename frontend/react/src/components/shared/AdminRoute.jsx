import {useEffect} from "react";
import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";

const AdminRoute = ({ children }) => {
    const { isMemberAuthenticated, isAdmin } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (!isMemberAuthenticated() || !isAdmin()) {
            navigate("/"); // Redirect to home if not authenticated or not an admin
        }
    });

    return isMemberAuthenticated() && isAdmin() ? children : null;
};

export default AdminRoute;
