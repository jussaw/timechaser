import React, { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faTriangleExclamation,
  faCircleExclamation,
} from "@fortawesome/free-solid-svg-icons";
import axiosInstance from "../config/axiosConfig";
import "../styles/Admin.css";

export default function Admin() {
  const [formData, setformData] = useState({
    role: "",
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [isFormDataValid, setIsFormDataValid] = useState({
    role: false,
    firstName: false,
    lastName: false,
    username: false,
    password: false,
    confirmPassword: false,
  });
  const [isFormValid, setIsFormValid] = useState(false);
  const [submissionError, setSubmissionError] = useState(null);
  const [submissionUsername, setSubmissionUsername] = useState(null);
  const [isSubmissionSuccess, setIsSubmissionSuccess] = useState(false);
  const [focusedField, setFocusedField] = useState("");

  const formErrors = {
    firstName: "First name must not be empty",
    lastName: "Last name must not be empty",
    role: "Role must be selected",
    username: "Username must be at least 8 characters",
    password:
      "Password must be at least 8 characters and contain at least one lowercase letter, one uppercase letter, one number, and one special character",
    confirmPassword: "Passwords do not match",
  };

  const passwordRegex =
    /^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()\-[\]~_+{}=;:'",<.>/?\\|`]).{8,}$/;

  useEffect(() => {
    setIsFormDataValid({
      role: formData["role"].trim() !== "",
      firstName: formData["firstName"].trim() !== "",
      lastName: formData["lastName"].trim() !== "",
      username: formData["username"].length >= 8,
      password: passwordRegex.test(formData["password"]),
      confirmPassword:
        passwordRegex.test(formData["confirmPassword"]) &&
        formData["confirmPassword"] === formData["password"],
    });
  }, [formData]);

  useEffect(() => {
    let valid = true;
    for (let key in isFormDataValid) {
      if (!isFormDataValid[key]) {
        valid = false;
        break;
      }
    }
    setIsFormValid(valid);
  }, [isFormDataValid]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setformData({
      ...formData,
      [name]: value,
    });
  };

  const handleFocus = (e) => {
    setFocusedField(e.target.name);
  };

  const handleBlur = () => {
    setFocusedField("");
  };

  const mapErrorCode = (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 400:
          setSubmissionError("A required field is missing");
          break;
        case 500:
          setSubmissionError("This username is already in use");
          break;
        default:
          setSubmissionError("Internal Server Error");
          break;
      }
    } else {
      setSubmissionError("Server Offline");
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmissionError(null);
    setIsSubmissionSuccess(false);

    axiosInstance
      .post("/user", {
        username: formData.username.trim(),
        password: formData.password,
        firstName: formData.firstName.trim(),
        lastName: formData.lastName.trim(),
      })
      .then((response) => {
        console.log("Successful user creation");
        const newUserId = response.data.id;

        axiosInstance
          .post(`/user/${newUserId}/role`, {
            roleId: parseInt(formData.role),
          })
          .then(() => {
            setSubmissionUsername(formData.username);
            setIsSubmissionSuccess(true);
            setformData({
              role: "",
              firstName: "",
              lastName: "",
              username: "",
              password: "",
              confirmPassword: "",
            });
          })
          .catch((error) => {
            console.error("Error role assignment", error);
          });
      })
      .catch((error) => {
        mapErrorCode(error);
        console.error("Error user creation", error.message);
      });
  };

  return (
    <div className="full-page-component flex flex-grow flex-col justify-start space-y-8 p-12">
      <h1 className="text-4xl font-bold">Create User</h1>
      <form
        className="flex flex-grow flex-col justify-start space-y-8"
        onSubmit={handleSubmit}
      >
        <div className="flex w-full flex-col space-y-8">
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="role">
                Role:
              </label>
              <select
                className={
                  isFormDataValid["role"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                name="role"
                value={formData.role}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              >
                <option label="" value=""></option>
                <option label="Admin" value="1">
                  Admin
                </option>
                <option label="Manager" value="2">
                  Manager
                </option>
                <option label="Employee" value="3">
                  User
                </option>
              </select>
            </div>
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="firstName">
                First Name:
              </label>
              <input
                className={
                  isFormDataValid["firstName"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="text"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="lastName">
                Last Name:
              </label>
              <input
                className={
                  isFormDataValid["lastName"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="text"
                name="lastName"
                value={formData.lastName}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="username">
                Username:
              </label>
              <input
                className={
                  isFormDataValid["username"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="text"
                name="username"
                value={formData.username}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="password">
                Password:
              </label>
              <input
                className={
                  isFormDataValid["password"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="confirmPassword">
                Confirm Password:
              </label>
              <input
                className={
                  isFormDataValid["confirmPassword"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="password"
                name="confirmPassword"
                value={formData.confirmPassword}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
          </div>
        </div>
        <button
          className={`w-fit rounded-full p-2 px-8 text-custom-white ${
            isFormValid
              ? "bg-custom-blue hover:bg-custom-blue-dark"
              : "bg-custom-disable"
          }`}
          type="submit"
          disabled={!isFormValid}
        >
          Create
        </button>
        {focusedField && !isFormDataValid[focusedField] && (
          <span className="admin-error">
            <FontAwesomeIcon className="mr-2" icon={faCircleExclamation} />
            {formErrors[focusedField]}
          </span>
        )}
        {submissionError && (
          <span className="admin-error">
            <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
            {submissionError}
          </span>
        )}
        {isSubmissionSuccess && (
          <div className="bg-custom-green-light text-custom-green-dark w-fit rounded-md px-3 py-2">
            🎉 Successfully created user: {submissionUsername}
          </div>
        )}
      </form>
    </div>
  );
}
