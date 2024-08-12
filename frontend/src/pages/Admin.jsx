import React, { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faTriangleExclamation,
  faCircleExclamation,
} from "@fortawesome/free-solid-svg-icons";
import axiosInstance from "../config/axiosConfig";
import "../styles/Admin.css";
import Select from "react-select";

export default function Admin() {
  const [allUsers, setAllUsers] = useState(null);
  const [selectedUser, setSelectedUser] = useState(null);

  const roleOptions = [
    { value: "1", label: "Admin" },
    { value: "2", label: "Manager" },
    { value: "3", label: "Employee" },
  ];

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
  const [selectedCreateOption, setSelectedCreateOption] = useState(null);
  const [isFormValid, setIsFormValid] = useState(false);
  const [submissionError, setSubmissionError] = useState(null);
  const [submissionUsername, setSubmissionUsername] = useState(null);
  const [isSubmissionSuccess, setIsSubmissionSuccess] = useState(false);
  const [focusedField, setFocusedField] = useState("");

  //delete form fields
  const [selectedDeleteOption, setSelectedDeleteOption] = useState(null);
  const [isDeleteSuccess, setIsDeleteSuccess] = useState(false);
  const [deleteError, setDeleteError] = useState(null);

  const handleDeleteChange = (option) => {
    setSelectedDeleteOption(option);
  };

  const handleRoleChange = (option) => {
    setformData({
      ...formData,
      role: option.label,
      id: option.value,
    });
    setSelectedCreateOption(option);
  };

  const handleDeleteSubmit = (event) => {
    event.preventDefault();
    setDeleteError(null);
    setSelectedUser(selectedDeleteOption.label);
    axiosInstance
      .delete(`user/${selectedDeleteOption.value}`)
      .then(() => {
        setAllUsers((prevOptions) =>
          prevOptions.filter(
            (option) => option.value !== selectedDeleteOption.value,
          ),
        );
        setSelectedDeleteOption(null);
        setIsDeleteSuccess(true);
      })
      .catch((error) => {
        mapDeleteErrorCode(error);
      });
    // Perform your submit action here
  };

  const customCreateStylesValid = {
    control: (provided, state) => ({
      ...provided,
      width: "14rem", // equivalent to w-56
      borderRadius: "9999px", // equivalent to rounded-full
      borderColor: "#EF4444",
      backgroundColor: "#F3F4F6", // equivalent to bg-custom-white
      padding: "0 0.25rem",
      fontSize: "1.125rem", // equivalent to text-lg
      boxShadow: state.isFocused ? "0 0 0 2px rgba(239, 68, 68, 1)" : "none", // focus:ring-2 focus:ring-custom-blue
      outline: "none", // focus:outline-none
      height: "2.5rem", // Adjust height to match other form fields
      display: "flex", // Ensure proper vertical alignment
      alignItems: "center", // Center align the content vertically
    }),
    menu: (provided) => ({
      ...provided,
      borderRadius: "0.375rem", // default rounded corners for menu
      marginTop: "0.2rem", // default margin for menu
    }),
    menuList: (provided) => ({
      ...provided,
      padding: 0, // default padding for menu list
    }),
    option: (provided) => ({
      ...provided,
      padding: "0.5rem 1rem", // default padding for options
    }),
    singleValue: (provided) => ({
      ...provided,
      color: "#000", // default color for single value
      height: "100%", // Make sure it takes full height of the control
      display: "flex",
      alignItems: "center", // Center align the selected value vertically
    }),
    placeholder: (provided) => ({
      ...provided,
      color: "#6B7280", // Tailwind's gray-500 for placeholder color (adjust as needed)
      display: "flex",
      alignItems: "center",
    }),
  };

  const customCreateStylesInvalid = {
    control: (provided, state) => ({
      ...provided,
      width: "14rem", // equivalent to w-56
      borderRadius: "9999px", // equivalent to rounded-full
      borderColor: "#3B82F6", // equivalent to border-custom-blue
      backgroundColor: "#F3F4F6", // equivalent to bg-custom-white
      padding: "0 0.25rem",
      fontSize: "1.125rem", // equivalent to text-lg
      boxShadow: state.isFocused ? "0 0 0 2px rgba(59, 130, 246, 0.5)" : "none", // focus:ring-2 focus:ring-custom-blue
      outline: "none", // focus:outline-none
      height: "2.5rem", // Adjust height to match other form fields
      display: "flex", // Ensure proper vertical alignment
      alignItems: "center", // Center align the content vertically
    }),
    menu: (provided) => ({
      ...provided,
      borderRadius: "0.375rem", // default rounded corners for menu
      marginTop: "0.2rem", // default margin for menu
    }),
    menuList: (provided) => ({
      ...provided,
      padding: 0, // default padding for menu list
    }),
    option: (provided) => ({
      ...provided,
      padding: "0.5rem 1rem", // default padding for options
    }),
    singleValue: (provided) => ({
      ...provided,
      color: "#000", // default color for single value
      height: "100%", // Make sure it takes full height of the control
      display: "flex",
      alignItems: "center", // Center align the selected value vertically
    }),
    placeholder: (provided) => ({
      ...provided,
      color: "#6B7280", // Tailwind's gray-500 for placeholder color (adjust as needed)
      display: "flex",
      alignItems: "center",
    }),
  };

  const customDeleteStyles = {
    control: (provided, state) => ({
      ...provided,
      width: "14rem", // equivalent to w-56
      borderRadius: "9999px", // equivalent to rounded-full
      borderColor: "#3B82F6", // equivalent to border-custom-blue
      backgroundColor: "#F3F4F6", // equivalent to bg-custom-white
      padding: "0 0.25rem",
      fontSize: "1.125rem", // equivalent to text-lg
      boxShadow: state.isFocused ? "0 0 0 2px rgba(59, 130, 246, 0.5)" : "none", // focus:ring-2 focus:ring-custom-blue
      outline: "none", // focus:outline-none
      height: "2.5rem", // Adjust height to match other form fields
      display: "flex", // Ensure proper vertical alignment
      alignItems: "center", // Center align the content vertically
    }),
    menu: (provided) => ({
      ...provided,
      borderRadius: "0.375rem", // default rounded corners for menu
      marginTop: "0.2rem", // default margin for menu
    }),
    menuList: (provided) => ({
      ...provided,
      padding: 0, // default padding for menu list
    }),
    option: (provided) => ({
      ...provided,
      padding: "0.5rem 1rem", // default padding for options
    }),
    singleValue: (provided) => ({
      ...provided,
      color: "#000", // default color for single value
      height: "100%", // Make sure it takes full height of the control
      display: "flex",
      alignItems: "center", // Center align the selected value vertically
    }),
    placeholder: (provided) => ({
      ...provided,
      color: "#6B7280", // Tailwind's gray-500 for placeholder color (adjust as needed)
      display: "flex",
      alignItems: "center",
    }),
  };

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

  useEffect(() => {
    axiosInstance
      .get("/user/all")
      .then((response) => {
        const formattedOptions = response.data.map((user) => ({
          value: user.id, // Use unique identifier as the value
          label: `${user.firstName} ${user.lastName}`,
        }));
        setAllUsers(formattedOptions);
      })
      .catch((error) => {
        mapGetAllErrorCode(error);
      });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setformData({
      ...formData,
      [name]: name === "username" ? value.replace(/\s+/g, "") : value,
    });
  };

  const handleFocus = (e) => {
    setFocusedField(e.target.name);
  };

  const handleBlur = () => {
    setFocusedField("");
  };

  const mapCreateErrorCode = (error) => {
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

  //TODO: confirm with backend what response codes for findAll endpoint
  const mapGetAllErrorCode = (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 500:
        default:
          setSubmissionError("Internal Server Error");
          break;
      }
    } else {
      setSubmissionError("Server Offline");
    }
  };

  const mapDeleteErrorCode = (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 400:
          setDeleteError("Invalid option selected");
          break;
        case 404:
          setDeleteError("User does not exist");
          break;
        default:
          setDeleteError("Internal Server Error");
          break;
      }
    } else {
      setDeleteError("Server Offline");
    }
  };

  const handleCreateSubmit = (e) => {
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
        const newUserId = response.data.id;

        axiosInstance
          .post(`/user/${newUserId}/role`, {
            roleId: parseInt(formData.id),
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
          });
        //update delete users list to show new user
        setAllUsers((prevUsers) => [
          ...prevUsers,
          {
            value: newUserId,
            label: `${formData.firstName} ${formData.lastName}`,
          },
        ]);
        //reset role dropdown to default 'select...' option
        setSelectedCreateOption(null);
      })
      .catch((error) => {
        mapCreateErrorCode(error);
      });
  };

  return (
    <div className="full-page-component flex flex-grow">
      <div className="flex w-1/2 flex-col space-y-8 p-12">
        <h1 className="text-4xl font-bold">Create User</h1>
        <form
          className="flex flex-grow flex-col justify-start space-y-8"
          onSubmit={handleCreateSubmit}
        >
          <div className="flex w-full flex-col space-y-8">
            <div className="admin-label-input-error">
              <div className="admin-label-input">
                <label className="admin-label" htmlFor="role">
                  Role:
                </label>
                <Select
                  id="role"
                  options={roleOptions}
                  styles={
                    isFormDataValid["role"]
                      ? customCreateStylesInvalid
                      : customCreateStylesValid
                  }
                  name="role"
                  value={selectedCreateOption}
                  onChange={handleRoleChange}
                  onFocus={handleFocus}
                  onBlur={handleBlur}
                />
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
            <div className="w-fit rounded-md bg-custom-green-light px-3 py-2 text-custom-green-dark">
              🎉 Successfully created user: {submissionUsername}
            </div>
          )}
        </form>
      </div>
      <div className="flex w-1/2 flex-col space-y-8 p-12">
        <h1 className="text-4xl font-bold">Delete User</h1>
        <form
          className="flex flex-grow flex-col justify-start space-y-8"
          onSubmit={handleDeleteSubmit}
        >
          <div className="flex w-full flex-col space-y-8">
            <div className="admin-label-input-error">
              <div className="admin-label-input">
                <label className="admin-label" htmlFor="deleteUser">
                  User ID:
                </label>
                <Select
                  id="deleteUser"
                  options={allUsers}
                  styles={customDeleteStyles}
                  name="deleteUser"
                  value={selectedDeleteOption}
                  onChange={handleDeleteChange}
                />
              </div>
            </div>
          </div>
          <button
            className={`w-fit rounded-full p-2 px-8 text-custom-white ${
              selectedDeleteOption
                ? "bg-custom-blue hover:bg-custom-blue-dark"
                : "bg-custom-disable"
            }`}
            type="submit"
            disabled={!selectedDeleteOption}
          >
            Delete
          </button>
          {isDeleteSuccess && (
            <div className="w-fit rounded-md bg-custom-green-light px-3 py-2 text-custom-green-dark">
              🎉 Successfully deleted user {selectedUser}
            </div>
          )}
          {submissionError && (
            <span className="admin-error">
              <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
              {submissionError}
            </span>
          )}
        </form>
      </div>
    </div>
  );
}
