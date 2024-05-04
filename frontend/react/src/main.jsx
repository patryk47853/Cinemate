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

const {ToastContainer} = createStandaloneToast();

const router = createBrowserRouter([
    {
        path:"/",
        element: <Login/>
    },
    {
        path:"/sign-up",
        element: <SignUp/>
    },
    {
        path:"/home",
        element: <Text fontSize={"6xl"}>Dashboard</Text>
    },
    {
        path:"/home/members",
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
                    <RouterProvider router={router} />
                </AuthProvider>
                <ToastContainer/>
            </ChakraProvider>
        </React.StrictMode>,
    )
