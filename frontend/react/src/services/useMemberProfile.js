import { useState, useEffect } from 'react';
import { getMemberProfile } from "./client.js";
import {errorNotification} from "./notification.js";
import {useAuth} from "../components/context/AuthContext.jsx";

const useMemberProfile = () => {
    const { logout, member } = useAuth();
    const [memberProfile, setMemberProfile] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        if (member) {
            fetchMemberProfile(member.username);
        }
    }, [member]);

    const fetchMemberProfile = (username = member.username) => {
        setLoading(true);
        getMemberProfile(username)
            .then(res => {
                setMemberProfile(res.data);
            })
            .catch(err => {
                if (err.response && err.response.data) {
                    setError(err.response.data.message);
                    errorNotification(
                        err.code,
                        err.response.data.message
                    );
                } else {
                    setError("An error occurred while fetching member profile.");
                    errorNotification("Error", "An error occurred while fetching member profile.");
                }
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const memberId = memberProfile?.id;
    const memberRoles = member?.roles || [];
    const isAdmin = memberRoles.includes('ROLE_ADMIN');

    return {
        memberProfile,
        loading,
        error,
        fetchMemberProfile,
        isAdmin
    };
};

export default useMemberProfile;
