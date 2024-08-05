import React, { useState, useEffect, useRef, useContext } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faXmark,
  faPenToSquare,
  faTriangleExclamation,
  faCircleExclamation,
} from "@fortawesome/free-solid-svg-icons";
import "../styles/Profile.css";
import axiosInstance from "../config/axiosConfig";
import { AuthContext, useAuth } from "../context/AuthContext";

export default function Profile() {
  const { authData, setAuthData } = useContext(AuthContext);

  const [displayName, setDisplayName] = useState({
    firstName: "",
    lastName: "",
  });

  const [userFormValues, setUserFormValues] = useState({
    firstName: "",
    lastName: "",
  });
  const [username, setUsername] = useState("");
  const [isUserFormSubmitSuccess, setIsUserFormSubmitSuccess] = useState(false);
  const [userSubmissionError, setUserSubmissionError] = useState(null);

  //password reset fields
  const [passwordFormValues, setPasswordFormValues] = useState({
    newPassword: "",
    confirmPassword: "",
  });
  const [isNewPasswordValid, setIsNewPasswordValid] = useState(false);
  const [isValidPasswords, setIsValidPasswords] = useState(false);

  //states used for buttons
  const [isEditingFirstName, setIsEditingFirstName] = useState(false);
  const [isEditingLastName, setIsEditingLastName] = useState(false);
  const [isUserFormChanged, setIsUserFormChanged] = useState(false);

  const [isPasswordSubmitSuccess, setIsPasswordSubmitSuccess] = useState();
  const [passwordSubmissionError, setPasswordSubmissionError] = useState(null);

  //states for focus on input fields.
  const firstNameInputRef = useRef(null);
  const lastNameInputRef = useRef(null);

  //time/timezone fields
  const [timeZone, setTimeZone] = useState("");
  const [currentTime, setCurrentTime] = useState(new Date());

  //TO-DO: get supervisor from api
  const [supervisor, setSupervisor] = useState({
    name: "Jane Doe",
  });

  useEffect(() => {
    setDisplayName({
      firstName: authData.user.firstName,
      lastName: authData.user.lastName,
    });
    setUserFormValues({
      firstName: authData.user.firstName,
      lastName: authData.user.lastName,
    });
  }, [authData.user.firstName, authData.user.lastName]);

  useEffect(() => {
    setUsername(authData.user.username);
  }, []);

  //timer update logic for time zone
  useEffect(() => {
    try {
      const tz = Intl.DateTimeFormat().resolvedOptions().timeZone;
      setTimeZone(tz);
    } catch (error) {
      setTimeZone("Unknown");
    }
    //update clock every second
    const timer = setInterval(() => {
      setCurrentTime(new Date());
    }, 1000);

    return () => clearInterval(timer);
  }, []);

  //validate passwords
  useEffect(() => {
    setIsValidPasswords(
      isNewPasswordValid &&
        passwordFormValues.newPassword === passwordFormValues.confirmPassword,
    );
  }, [
    passwordFormValues.newPassword,
    passwordFormValues.confirmPassword,
    isNewPasswordValid,
  ]);

  //password validation
  useEffect(() => {
    const passwordRegex =
      /^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()\-[\]~_+{}=;:'",<.>/?\\|`]).{8,}$/;
    const isPasswordValid = passwordRegex.test(passwordFormValues.newPassword);
    setIsNewPasswordValid(isPasswordValid);
  }, [passwordFormValues]);

  //update first name field
  const toggleEditFirstName = () => {
    setIsEditingFirstName((prevIsEditingFirstName) => !prevIsEditingFirstName);
    if (!isEditingFirstName) {
      setTimeout(() => firstNameInputRef.current.focus(), 0);
    }
  };

  //update last name field
  const toggleEditLastName = () => {
    setIsEditingLastName((prevIsEditingLastName) => !prevIsEditingLastName);
    if (!isEditingLastName) {
      setTimeout(() => lastNameInputRef.current.focus(), 0);
    }
  };

  const handleUserSubmit = (e) => {
    e.preventDefault();
    setIsUserFormSubmitSuccess(true);
    setUserSubmissionError(null);
    userFormValues.firstName = userFormValues.firstName.trim();
    userFormValues.lastName = userFormValues.lastName.trim();
    //If fields were being edited, disable edit mode after saving
    if (isEditingFirstName || isEditingLastName) {
      setIsEditingFirstName(false);
      setIsEditingLastName(false);
      setIsUserFormChanged(false);
    }
    axiosInstance
      .put(`/user/${authData.user.id}/details`, {
        firstName: userFormValues.firstName,
        lastName: userFormValues.lastName,
      })
      .then(() => {
        setAuthData((prevAuthData) => ({
          ...prevAuthData,
          user: {
            ...prevAuthData.user,
            firstName: userFormValues.firstName,
            lastName: userFormValues.lastName,
          },
        }));
        setDisplayName(userFormValues);
        setIsUserFormSubmitSuccess(true);
      })
      .catch((error) => {
        setUserSubmissionError(mapErrorCode(error));
      });
  };

  const mapErrorCode = (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 400:
          return "Fields don't meet requirements";
        case 403:
          return "Session expired";
        case 404:
          return "User does not exist";
        case 500:
          return "Internal Server Error";
        default:
          return "Other Error";
      }
    } else {
      return "Server Offline";
    }
  };

  const handlePasswordSubmit = (e) => {
    e.preventDefault();
    setIsPasswordSubmitSuccess(false);
    setPasswordSubmissionError(null);
    axiosInstance
      .put(`/user/${authData.user.id}/password`, {
        password: passwordFormValues.newPassword,
      })
      .then(() => {
        setPasswordFormValues({
          newPassword: "",
          confirmPassword: "",
        });

        setIsPasswordSubmitSuccess(true);
      })
      .catch((error) => {
        setPasswordSubmissionError(mapErrorCode(error));
      });
  };

  const handleUserChange = (e) => {
    setIsUserFormSubmitSuccess(false);
    const { name, value } = e.target;
    setUserFormValues((prevValues) => {
      const updatedFormValues = {
        ...prevValues,
        [name]: value,
      };
      setIsUserFormChanged(
        updatedFormValues.firstName != displayName.firstName ||
          updatedFormValues.lastName != displayName.lastName,
      );
      return updatedFormValues;
    });
  };

  const validateUser = () => {
    if (
      userFormValues.firstName.trim() !== "" &&
      userFormValues.lastName.trim() !== ""
    ) {
      return true;
    }
    return false;
  };

  const handlePasswordChange = (e) => {
    setIsPasswordSubmitSuccess();
    const { name, value } = e.target;
    setPasswordFormValues((prevValues) => {
      const updatedFormValues = {
        ...prevValues,
        [name]: value,
      };
      return updatedFormValues;
    });
  };

  return (
    <div className="full-page-component flex flex-grow flex-col justify-start space-y-8 p-12">
      <div className="w-half flex-1">
        <h1 className="items-center pb-7 text-4xl font-bold">
          Welcome, {displayName.firstName} {displayName.lastName}
        </h1>
        <div className="pb-10">Reports to: {supervisor.name}</div>
        <form onSubmit={handleUserSubmit} className="w-full space-y-9">
          <div className="entry">
            <label htmlFor="firstName" className="user-label">
              <span>First Name: </span>
            </label>
            <input
              ref={firstNameInputRef}
              className={`data mr-2 w-32 ${isEditingFirstName ? "border-custom-black" : "border-custom-white"}`}
              type="text"
              id="firstName"
              name="firstName"
              value={userFormValues.firstName}
              onChange={handleUserChange}
              readOnly={!isEditingFirstName}
              autoComplete="off"
              required
            ></input>
            <button
              type="button"
              onClick={toggleEditFirstName}
              className="edit-button mr-56"
            >
              <FontAwesomeIcon
                className={`w-4 ${isEditingFirstName ? "text-custom-red" : "text-custom-blue"}`}
                icon={isEditingFirstName ? faXmark : faPenToSquare}
              />
            </button>
          </div>
          <div className="entry">
            <label htmlFor="lastName" className="user-label">
              <span>Last Name: </span>
            </label>
            <input
              ref={lastNameInputRef}
              className={`data mr-2 w-32 ${isEditingLastName ? "border-custom-black" : "border-custom-white"}`}
              type="text"
              id="lastName"
              name="lastName"
              value={userFormValues.lastName}
              onChange={handleUserChange}
              readOnly={!isEditingLastName}
              autoComplete="off"
              required
            ></input>
            <button
              type="button"
              onClick={toggleEditLastName}
              className="edit-button mr-56"
            >
              <FontAwesomeIcon
                className={`w-4 ${isEditingLastName ? "text-custom-red" : "text-custom-blue"}`}
                icon={isEditingLastName ? faXmark : faPenToSquare}
              />
            </button>
          </div>
          <div className="entry">
            <label htmlFor="username" className="user-label">
              <span>Username: </span>
            </label>
            <span className="data border-custom-white">{username}</span>
          </div>
          <div className="entry">
            <label className="user-label">
              <span>Time: </span>
            </label>
            <span className="data border-custom-white">
              {currentTime.toLocaleTimeString()}
            </span>
          </div>
          <div className="entry">
            <label className="user-label">
              <span>Time zone: </span>
            </label>
            <span className="data border-custom-white">{timeZone}</span>
          </div>
          <button
            type="submit"
            className={`rounded-full p-2 px-4 text-custom-white ${isUserFormChanged && validateUser() ? "bg-custom-blue hover:bg-custom-blue-dark" : "bg-custom-disable"}`}
            disabled={!validateUser()}
          >
            Save
          </button>
          {isUserFormSubmitSuccess && (
            <div className="w-fit rounded-md bg-custom-green-light px-3 py-2 text-custom-green-dark">
              🎉 Successfully updated name
            </div>
          )}
          {userSubmissionError && (
            <div className="w-fit rounded-md bg-custom-red-light px-3 py-2">
              <span>
                <FontAwesomeIcon
                  className="mr-2"
                  icon={faTriangleExclamation}
                />
                {userSubmissionError}
              </span>
            </div>
          )}
        </form>
      </div>

      <div className="flex-1">
        <h1 className="items-center pb-5 text-2xl font-semibold">
          Password Reset
        </h1>
        <div className="pb-10">
          Password must be at least 8 characters long and contain at least one
          number, one special character, and one uppercase letter.
        </div>

        <form onSubmit={handlePasswordSubmit} className="w-full space-y-9">
          <div className="entry">
            <label htmlFor="newPassword" className="password-label">
              <span>New Password: </span>
            </label>
            <input
              className="data w-32 border-custom-black"
              type="password"
              id="newPassword"
              name="newPassword"
              value={passwordFormValues.newPassword}
              onChange={handlePasswordChange}
              autoComplete="off"
              required
            ></input>
          </div>

          <div className="entry">
            <label htmlFor="confirmPassword" className="password-label">
              <span>Confirm Password: </span>
            </label>
            <input
              className="data w-32 border-custom-black"
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              value={passwordFormValues.confirmPassword}
              onChange={handlePasswordChange}
              autoComplete="off"
              required
            ></input>
          </div>
          <button
            type="submit"
            className={`rounded-full p-2 px-4 text-custom-white ${
              isValidPasswords
                ? "bg-custom-blue hover:bg-custom-blue-dark"
                : "bg-custom-disable"
            }`}
            disabled={!isValidPasswords}
          >
            Reset
          </button>
          {isPasswordSubmitSuccess && (
            <div className="w-fit rounded-md bg-custom-green-light px-3 py-2 text-custom-green-dark">
              🎉 Successfully changed password
            </div>
          )}
          {passwordSubmissionError && (
            <div className="w-fit rounded-md bg-custom-red-light px-3 py-2">
              <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
              {passwordSubmissionError}
            </div>
          )}
          <div className="flex flex-col space-y-1 text-custom-red">
            {passwordFormValues.newPassword.length > 0 &&
              !isNewPasswordValid && (
                <span className="w-fit rounded-md bg-custom-red-light px-3 py-2 text-custom-red">
                  <FontAwesomeIcon
                    className="mr-2"
                    icon={faCircleExclamation}
                  />
                  Invalid password
                </span>
              )}
            {!(
              passwordFormValues.newPassword ===
              passwordFormValues.confirmPassword
            ) && (
              <span className="w-fit rounded-md bg-custom-red-light px-3 py-2 text-custom-red">
                <FontAwesomeIcon className="mr-2" icon={faCircleExclamation} />
                Passwords do not match
              </span>
            )}
          </div>
        </form>
      </div>
    </div>
  );
}
