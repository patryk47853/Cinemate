import React from 'react'
import ReactDOM from 'react-dom/client'
import {ChakraProvider} from "@chakra-ui/react";
import {createStandaloneToast} from '@chakra-ui/react'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import './index.css'
import Login from "./components/login/Login.jsx";
import AuthProvider from "./components/context/AuthContext.jsx";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";
import SignUp from "./components/signup/SignUp.jsx";
import Member from "./Member.jsx";
import RandomQuote from "./RandomQuote.jsx";
import Movie from "./components/movie/Movie.jsx";
import MovieDetails from "./components/movie/MovieDetails.jsx";
import FavoriteMovies from "./components/favorites/FavoriteMovies.jsx";
import AdminRoute from "./components/shared/AdminRoute.jsx";

const {ToastContainer} = createStandaloneToast();

const router = createBrowserRouter([
    {
        path: "/",
        element: <Login/>
    },
    {
        path: "/sign-up",
        element: <SignUp/>
    },
    {
        path: "/home",
        element: <ProtectedRoute>
            <RandomQuote/>
        </ProtectedRoute>
    },
    {
        path: "/search",
        element: <AdminRoute>
            <Movie source="api" />
        </AdminRoute>
    },
    {
        path: "/search/:title/:year",
        element: <ProtectedRoute>
            <MovieDetails source="api"/>
        </ProtectedRoute>
    },
    {
        path: "/movies",
        element: <ProtectedRoute>
            <Movie source="database" />
        </ProtectedRoute>
    },
    {
        path: "/movies/:movieId",
        element: <ProtectedRoute>
            <MovieDetails source="database"/>
        </ProtectedRoute>
    },
    {
        path: "/favorites",
        element: <ProtectedRoute>
            <FavoriteMovies/>
        </ProtectedRoute>
    },
    {
        path: "/members",
        element: <ProtectedRoute>
            <Member/>
        </ProtectedRoute>
    }
])

ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <AuthProvider>
                    <RouterProvider router={router}/>
                </AuthProvider>
                <ToastContainer/>
            </ChakraProvider>
        </React.StrictMode>,
    )