import React, { createContext, useState, useEffect, useContext } from "react";
import { Navigate } from "react-router-dom";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authData, setAuthData] = useState(() => {
    const storedAuthData = localStorage.getItem("authData");
    return storedAuthData ? JSON.parse(storedAuthData) : null;
  });

  useEffect(() => {
    if (authData) {
      localStorage.setItem("authData", JSON.stringify(authData));
    } else {
      localStorage.removeItem("authData");
    }
  }, [authData]);

  return (
    <AuthContext.Provider value={{ authData, setAuthData }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
