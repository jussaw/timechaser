import React, { useState, useEffect, useRef, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faClockRotateLeft,
  faTriangleExclamation,
} from "@fortawesome/free-solid-svg-icons";
import axiosInstance from "../config/axiosConfig";
import { jwtDecode } from "jwt-decode";
import "../styles/Auth.css";

export default function Login() {
  const navigate = useNavigate();
  const { authData, setAuthData } = useContext(AuthContext);
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });
  const [error, setError] = useState(null);
  const usernameRef = useRef(null);

  useEffect(() => {
    usernameRef.current = document.getElementById("username");
    usernameRef.current.focus();
  }, []);

  useEffect(() => {
    if (authData) {
      if (authData.roles[0].id === 1) {
        navigate("/admin");
      } else {
        navigate("/dashboard");
      }
    }
  }, [authData, navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({ ...prevState, [name]: value }));
  };

  const mapErrorCode = (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          setError("Incorrect credentials");
          break;
        case 403:
          setError("Unauthorized");
          break;
        default:
          setError("Internal Server Error");
      }
    } else {
      setError("Server Offline");
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError(null);

    axiosInstance
      .post(
        "/auth/login",
        {
          username: formData.username,
          password: formData.password,
        },
        {
          headers: {
            "Skip-Auth": true,
          },
        },
      )
      .then((response) => {
        setAuthData((prevAuthData) => ({
          ...prevAuthData,
          ...response.data,
          tokenExpiration: jwtDecode(response.data.token).exp,
        }));
      })
      .catch((error) => {
        mapErrorCode(error);
      });
  };

  return (
    <div className="flex flex-grow items-center justify-center">
      <div className="dashboard-component p-16 pb-2">
        <div className="flex h-full w-full flex-col items-center justify-between">
          <h1 className="mb-8 flex w-full items-center justify-center">
            <FontAwesomeIcon
              className="pr-2 text-3xl text-custom-blue"
              icon={faClockRotateLeft}
            />
            <label className="auth-greeting-label">Welcome!</label>
          </h1>
          <div className="w-12/12 flex h-full flex-col items-center justify-between pb-5">
            <form onSubmit={handleSubmit} className="flex w-full flex-col">
              <label className="auth-input-label">Username</label>
              <input
                type="text"
                id="username"
                name="username"
                value={formData.username}
                onChange={handleChange}
                autoComplete="true"
                required
                className="auth-input-box"
              />
              <label className="auth-input-label">Password</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                required
                className="auth-input-box"
              />
              <button type="submit" className="auth-submit">
                Log in
              </button>
              {error && (
                <span className="auth-error">
                  <FontAwesomeIcon
                    className="mr-2"
                    icon={faTriangleExclamation}
                  />
                  {error}
                </span>
              )}
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
