import React, { useState, useEffect, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";
import axiosInstance from "../config/axiosConfig";
import Select from "react-select";
import "../styles/Admin.css";

export default function Admin() {
  // TODO: Delete if not using react-select
  const { authData, setAuthData } = useContext(AuthContext);

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
  const [submissionError, setSubmissionError] = useState(null);
  const [isFormValid, setIsFormValid] = useState(false);
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
  // TODO: Delete if not using react-select
  const roleOptions = [
    { value: 1, label: "Admin", color: "#00B8D9", isFixed: true },
    { value: 2, label: "Manager", color: "#0052CC" },
    { value: 3, label: "Employee", color: "#5243AA" },
  ];

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

  const handleSubmit = (e) => {
    e.preventDefault();

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
            console.log("Successful role assignment");
            // TODO: Set success message
          })
          .catch((error) => {
            setSubmissionError(error);
            console.error("Error role assignment", error);
          });
      })
      .catch((error) => {
        setSubmissionError(error);
        console.error("Error user creation", error);
      });
  };

  return (
    <div className="full-page-component flex flex-grow flex-col justify-start space-y-8 p-12">
      <h1 className="text-4xl font-bold">Create User: </h1>
      {/* TODO: Display success message when form is successfully submitted */}
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
        {/* TODO: Refactor rendering form error */}
        {focusedField === "role" && !isFormDataValid["role"] && (
          <span className="admin-error">
            <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
            {formErrors["role"]}
          </span>
        )}
        {focusedField === "firstName" && !isFormDataValid["firstName"] && (
          <span className="admin-error">
            <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
            {formErrors["firstName"]}
          </span>
        )}
        {focusedField === "lastName" && !isFormDataValid["lastName"] && (
          <span className="admin-error">
            <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
            {formErrors["lastName"]}
          </span>
        )}
        {focusedField === "username" && !isFormDataValid["username"] && (
          <span className="admin-error">
            <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
            {formErrors["username"]}
          </span>
        )}
        {focusedField === "password" && !isFormDataValid["password"] && (
          <span className="admin-error">
            <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
            {formErrors["password"]}
          </span>
        )}
        {focusedField === "confirmPassword" &&
          !isFormDataValid["confirmPassword"] && (
            <span className="admin-error">
              <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
              {formErrors["confirmPassword"]}
            </span>
          )}
      </form>
      {/* TODO: Display error from API */}
    </div>
  );
}
