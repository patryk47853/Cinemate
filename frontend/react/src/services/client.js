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
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            title: movie.Title,
            rating: parseFloat(movie.imdbRating),
            description: movie.Plot,
            imgUrl: movie.Poster,
            awards: movie.Awards,
            year: movie.Year,
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

const API_URL = `http://www.omdbapi.com?apikey=5f07f8b0`;

export const fetchMoviesFromAPI = async (title) => {
    const response = await fetch(`${API_URL}&s=${title}`);
    const data = await response.json();
    if (response.ok) {
        return data.Search;
    } else {
        throw new Error("Failed to fetch movies from API");
    }
};

export const fetchMoviesFromDatabase = async () => {
    const DATABASE_URL = `${import.meta.env.VITE_API_BASE_URL}/movies/all`;
    try {
        const response = await fetch(DATABASE_URL, getAuthConfig());
        if (!response.ok) {
            throw new Error("Failed to fetch movies from the database");
        }
        return await response.json();
    } catch (error) {
        throw new Error("Failed to fetch movies from the database: " + error.message);
    }
};

export const fetchMovieFromDatabase = async (movieId) => {
    const DATABASE_URL = `${import.meta.env.VITE_API_BASE_URL}/movies/${movieId}`;
    try {
        const response = await fetch(DATABASE_URL, getAuthConfig());
        if (!response.ok) {
            throw new Error("Failed to fetch movies from the database");
        }
        return await response.json();
    } catch (error) {
        throw new Error("Failed to fetch movies from the database: " + error.message);
    }
};

export const addMovieToFavorites = async (memberId, movieId) => {
    try {
        return await axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/members/${memberId}/favorites/${movieId}`,
            getAuthConfig()
        )
    } catch (error) {
        throw error;
    }
};

export const removeMovieFromFavorites = async (memberId, movieId) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/members/${memberId}/favorites/${movieId}`,
            getAuthConfig()
        )
    } catch (error) {
        throw error;
    }
};

export const getCommentsForMovie = async (movieId) => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/comments/movie/${movieId}`,
            getAuthConfig()
        )
    } catch (error) {
        throw error;
    }
};

export const addComment = async (commentData) => {
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/comments/add`,
            commentData,
            getAuthConfig()
        )
    } catch (error) {
        throw error;
    }
};

export const deleteComment = async (commentId) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/comments/${commentId}`,
            getAuthConfig()
        );
    } catch (error) {
        throw error;
    }
};

export const getQuoteOfTheDay = async () => {
    try {
        return await axios.get(
            `https://zenquotes.io/api/today`,
            getAuthConfig()
        )
    } catch (error) {
        throw error;
    }
};





