import axios from "axios";

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getMembers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/members`,
            getAuthConfig()
        );
    } catch (e) {
        throw e;
    }
};

export const getMemberProfile = async (username) => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/members/profile/${username}`,
            getAuthConfig()
        );
    } catch (e) {
        throw e;
    }
};

export const saveMember = async (member) => {
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/members`,
            member
        )
    } catch (error) {
        throw error;
    }
};

export const deleteMember = async (id) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/members/${id}`,
            getAuthConfig()
        )
    } catch (error) {
        throw error;
    }
};

export const updateMember = async (id, update) => {
    try {
        return await axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/members/${id}`,
            update,
            getAuthConfig()
        )
    } catch (error) {
        throw error;
    }
};

export const login = async (usernameAndPassword) => {
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/login`,
            usernameAndPassword
        )
    } catch (error) {
        throw error;
    }
};
