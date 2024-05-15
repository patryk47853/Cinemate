import {createContext, useContext, useEffect, useState} from "react";
import {login as performLogin} from "../../services/client.js";
import {jwtDecode} from "jwt-decode";

const AuthContext = createContext({});

const AuthProvider = ({ children }) => {
    const [member, setMember] = useState(null);

    useEffect(() => {
        let token = localStorage.getItem("access_token");
        if(token) {
            token = jwtDecode(token);
            setMember({
                username: token.sub,
                roles: token.scopes
            });
        }
    }, []);

    const login = async (usernameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(usernameAndPassword).then(res => {
                const jwtToken = res.headers["authorization"];
                localStorage.setItem("access_token", jwtToken)

                const decodedToken = jwtDecode(jwtToken);

                setMember({
                    username: decodedToken.sub,
                    roles: decodedToken.scopes
                });
                resolve(res);
            }).catch(err => {
                reject(err);
            })
        })
    }

    const logout = () => {
        localStorage.removeItem("access_token")
        setMember(null);
    }

    const isMemberAuthenticated = () => {
        const token = localStorage.getItem("access_token");
        if(!token) {
            return false;
        }
        const{ exp: expiration} = jwtDecode(token);
        if(Date.now() > expiration * 1000) {
            logout();
            return false;
        }
        return true;
    }

    return (
        <AuthContext.Provider value={{
            member,
            login,
            logout,
            isMemberAuthenticated
        }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext);

// client.js

export const getUserProfile = async (username) => {
    try {
        // Assuming you have an API endpoint to fetch user profile information
        const response = await fetch(`/members/profile/${username}`); // Adjust the endpoint as per your API design
        if (response.ok) {
            return await response.json();
        }
    } catch (error) {
        throw error;
    }
};

export default AuthProvider;