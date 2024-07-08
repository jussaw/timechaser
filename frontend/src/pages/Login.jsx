import React from "react";
import { useState, useEffect, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faClockRotateLeft } from "@fortawesome/free-solid-svg-icons";
import "../styles/Auth.css";

export default function Login() {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const usernameRef = useRef(null);

  useEffect(() => {
    usernameRef.current = document.getElementById("username");
    usernameRef.current.focus();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    console.log("submitted");

    // TODO: Add API call to login and store JWT in context. If successful, redirect to dashboard. If error, display error.
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
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
