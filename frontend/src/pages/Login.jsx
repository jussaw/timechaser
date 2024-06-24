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
  const [usernameValid, setUsernameValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);
  const [usernameError, setUsernameError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [focusedField, setFocusedField] = useState(null);

  const usernameInputRef = useRef(null);

  useEffect(() => {
    usernameInputRef.current = document.getElementById("username");
    usernameInputRef.current.focus();
  }, []);

  useEffect(() => {
    const usernameRegex = /^[a-zA-Z0-9._-]{4,}$/;
    const passwordRegex =
      /^(?=.*[0-9])(?=.*[!@#$%^&!@*])(?=.*[A-Z])[a-zA-Z0-9!@#$%^&*]{8,}$/;
    const isUsernameValid = usernameRegex.test(formData.username);
    const isPasswordValid = passwordRegex.test(formData.password);

    setUsernameValid(isUsernameValid);
    setPasswordValid(isPasswordValid);

    if (!isUsernameValid) {
      setUsernameError(
        "Username must be at least 4 characters long and contain only letters, numbers, and !@#$%^&*()-=[]~_+{}.",
      );
    } else {
      setUsernameError("");
    }

    if (!isPasswordValid) {
      setPasswordError(
        "Password must be at least 8 characters long and contain at least one number, one uppercase letter, and one !@#$%^&*()-=[]~_+{}.",
      );
    } else {
      setPasswordError("");
    }
  }, [formData]);

  const handleFocus = (e) => {
    setFocusedField(e.target.name);
  };

  const handleBlur = () => {
    setFocusedField(null);
  };

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
      <div className="dashboard-component w-2/12 p-16 pb-2">
        <div className="flex h-full w-full flex-col items-center justify-between">
          <h1 className="mb-8 flex w-full items-center justify-center">
            <FontAwesomeIcon
              className="pr-2 text-3xl text-blue-500"
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
                onFocus={handleFocus}
                onBlur={handleBlur}
                autoComplete="true"
                required
                className={
                  usernameValid ? "auth-input-box" : "auth-input-box-disabled"
                }
              />
              {focusedField === "username" && usernameError && (
                <div className="auth-input-error">{usernameError}</div>
              )}
              <label className="auth-input-label">Password</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
                required
                className={
                  passwordValid ? "auth-input-box" : "auth-input-box-disabled"
                }
              />
              {focusedField === "password" && passwordError && (
                <div className="auth-input-error">{passwordError}</div>
              )}
              <button
                type="submit"
                disabled={!usernameValid || !passwordValid}
                className={
                  usernameValid && passwordValid
                    ? "auth-submit"
                    : "auth-submit-disabled"
                }
              >
                Log in
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
