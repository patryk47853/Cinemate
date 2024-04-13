require('dotenv').config(); // Load the .env file

import axios from "axios";

const authToken = process.env.AUTH_TOKEN;

export const getMembers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/members`);
    } catch (e) {
        throw e;
    }
};

export const saveMember = async (member) => {
    try {
        const response = await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/members`,
            member,
            {
                headers: {
                    Authorization: `Bearer ${authToken}`
                }
            }
        );
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const deleteMember = async (id) => {
    try {
        const response = await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/members/${id}`,
            {
                headers: {
                    Authorization: `Bearer ${authToken}`
                }
            }
        );
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const updateMember = async (id, update) => {
    try {
        const response = await axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/members/${id}`,
            update,
            {
                headers: {
                    Authorization: `Bearer ${authToken}`
                }
            }
        );
        return response.data;
    } catch (error) {
        throw error;
    }
};
