import axios from "axios";

export const getMembers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/members`)
    } catch (e) {
        throw e;
    }
}

export const saveMember = async (member, token) => {
    try {
        const response = await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/members`,
            member,
            {
                headers: {
                    Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6InNraW5uYWFhYXlAZXhhbXBsZS5jb20iLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJpYXQiOjE3MTE2MDk0MDYsImV4cCI6MTcxMjgxOTAwNn0.SiJ01WqA6pHkEFwaUVYqFlzimCeQQxqMh2Gj8Id2m04`
                }
            }
        );
        return response.data;
    } catch (error) {
        throw error;
    }
};