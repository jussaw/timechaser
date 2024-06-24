import React, { createContext, useState, useContext } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [jwtToken, setJwtToken] = useState(null);

  // Function to set JWT token
  const login = (token) => {
    setJwtToken(token);
  };

  // Function to remove JWT token (logout)
  const logout = () => {
    setJwtToken(null);
  };

  return (
    <AuthContext.Provider
      value={{
        jwtToken,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

// Custom hook to use the auth context
export const useAuth = () => useContext(AuthContext);
