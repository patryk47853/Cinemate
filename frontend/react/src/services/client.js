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

export const addToLibrary = (movie) => {
    // Assuming movie is the JSON response you provided
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            title: movie.Title,
            rating: parseFloat(movie.imdbRating),
            description: movie.Plot,
            imgUrl: movie.Poster,
            categories: movie.Genre,
            director: movie.Director,
            actors: movie.Actors
        })
    };

    fetch(`${import.meta.env.VITE_API_BASE_URL}/movies`, requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add movie to library');
            }
            console.log('Movie added to library successfully');
        })
        .catch(error => {
            console.error('Error adding movie to library:', error);
        });
};

