import React, { useState, useEffect, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark, faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import "../styles/Profile.css";

export default function Profile() {
  const [displayName, setDisplayName] = useState({
    firstName: "",
    lastName: "",
  });
  //TO-DO: get fields from API
  const [userFormValues, setuserFormValues] = useState({
    username: "e40040000",
    firstName: "John",
    lastName: "Doe",
  });
  const [isUserValid, setIsUserValid] = useState(false);

  //time/timezone fields
  const [timeZone, setTimeZone] = useState("");
  const [currentTime, setCurrentTime] = useState(new Date());
  //TO-DO: split apart supervisor name? Depends on API
  const [supervisor, setSupervisor] = useState({
    name: "Jane Doe",
  });

  //password reset fields
  const [passwordFormValues, setpasswordFormValues] = useState({
    currentPassword: "",
    newPassword: "",
    confirmPassword: "",
  });
  const [currentPasswordValid, setCurrentPasswordValid] = useState(false);
  const [newPasswordValid, setNewPasswordValid] = useState(false);
  const [passwordError, setPasswordError] = useState(false);

  //states used for buttons
  const [isEditingFirstName, setIsEditingFirstName] = useState(false);
  const [isEditingLastName, setIsEditingLastName] = useState(false);
  const [isLeftFormChanged, setIsLeftFormChanged] = useState(false);

  //states for focus on input fields
  const firstNameInputRef = useRef(null);
  const lastNameInputRef = useRef(null);

  //TODO: get name values from API
  useEffect(() => {
    setDisplayName(userFormValues);
  }, []);
  //getting time zone from device
  useEffect(() => {
    try {
      const tz = Intl.DateTimeFormat().resolvedOptions().timeZone;
      setTimeZone(tz);
    } catch (error) {
      console.error("Can't get the time zone", error);
      setTimeZone("Unknown");
    }
    //update clock every second
    const timer = setInterval(() => {
      setCurrentTime(new Date());
    }, 1000);

    return () => clearInterval(timer);
  }, []);

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

  //TO-DO: send info to backend business logic for submitting form
  const handleUserSubmit = (e) => {
    //stop page from reloading on submit
    e.preventDefault();
    userFormValues.firstName = userFormValues.firstName.trim();
    userFormValues.lastName = userFormValues.lastName.trim();
    setDisplayName(userFormValues);
    //If fields were being edited, disable edit mode after saving
    if (isEditingFirstName || isEditingLastName) {
      setIsEditingFirstName(false);
      setIsEditingLastName(false);
      setIsLeftFormChanged(false);
    }
  };

  //TODO: implement business logic for sending new password to backend
  const handlePasswordSubmit = (e) => {
    e.preventDefault();
    //currentPasswordValid used here
  };

  //password validation
  useEffect(() => {
    const passwordRegex =
      /^(?=.*[0-9])(?=.*[!@#$%^&!@*])(?=.*[A-Z])[a-zA-Z0-9!@#$%^&*]{8,}$/;
    const isPasswordValid = passwordRegex.test(passwordFormValues.newPassword);

    setNewPasswordValid(isPasswordValid);

    if (!isPasswordValid) {
      setPasswordError(
        "Password must be at least 8 characters long and contain at least one number, one uppercase letter, and one !@#$%^&*()-=[]~_+{}.",
      );
    } else {
      setPasswordError("");
    }
  }, [passwordFormValues]);

  const handleUserChange = (e) => {
    const { name, value } = e.target;
    setuserFormValues((prevValues) => {
      const updatedFormValues = {
        ...prevValues,
        [name]: value.trim(),
      };
      setIsLeftFormChanged(
        updatedFormValues.firstName != displayName.firstName ||
          updatedFormValues.lastName != displayName.lastName,
      );
      return updatedFormValues;
    });
  };

  const handlePasswordChange = (e) => {
    const { name, value } = e.target;
    setpasswordFormValues((prevValues) => {
      const updatedFormValues = {
        ...prevValues,
        [name]: value,
      };

      return updatedFormValues;
    });
  };

  function validateUser() {
    if (
      userFormValues.firstName.trim() !== "" &&
      userFormValues.lastName.trim() !== ""
    )
      return true;
    return false;
  }
  function validatePasswords() {
    return (
      newPasswordValid &&
      passwordFormValues.newPassword === passwordFormValues.confirmPassword
    );
  }

  //TODO: refactor all colors to fit palette
  return (
    <div className="full-page-component user-info flex h-full w-full flex-grow px-28 py-20">
      <div className="w-half flex-1">
        <h1 className="items-center pb-5 text-2xl">
          Welcome, {displayName.firstName} {displayName.lastName}
        </h1>
        <div className="pb-10">Reports to: {supervisor.name}</div>
        <form onSubmit={handleUserSubmit} className="w-full space-y-9">
          <div className="entry">
            <label htmlFor="firstName" className="left-label">
              <strong>First Name: </strong>
            </label>
            <input
              ref={firstNameInputRef}
              className={`data mr-2 w-32 ${isEditingFirstName ? "border-gray-800" : "border-custom-white"}`}
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
              {/* TODO change colors to custom color palette */}
              <FontAwesomeIcon
                className={`w-4 ${isEditingFirstName ? "text-red-500" : "text-custom-blue"}`}
                icon={isEditingFirstName ? faXmark : faPenToSquare}
              />
            </button>
          </div>
          <div className="entry">
            <label htmlFor="lastName" className="left-label">
              <strong>Last Name: </strong>
            </label>
            <input
              ref={lastNameInputRef}
              className={`data mr-2 w-32 ${isEditingLastName ? "border-gray-800" : "border-custom-white"}`}
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
                className={`w-4 ${isEditingLastName ? "text-red-500" : "text-custom-blue"}`}
                icon={isEditingLastName ? faXmark : faPenToSquare}
              />
            </button>
          </div>
          <div className="entry">
            <label htmlFor="username" className="left-label">
              <strong>Username: </strong>
            </label>
            <span className="data border-custom-white">
              {userFormValues.username}
            </span>
          </div>
          <div className="entry">
            <label className="left-label">
              <strong>Time: </strong>
            </label>
            <span className="data border-custom-white">
              {currentTime.toLocaleTimeString()}
            </span>
          </div>
          <div className="entry">
            <label className="left-label">
              <strong>Time zone: </strong>
            </label>
            <span className="data border-custom-white">{timeZone}</span>
          </div>
          <button
            type="submit"
            className={`rounded-full p-2 px-4 text-custom-white ${isLeftFormChanged && validateUser() ? "bg-custom-blue hover:bg-custom-blue-dark" : "bg-custom-gray"}`}
            disabled={!validateUser()}
          >
            Save
          </button>
        </form>
      </div>

      <div className="right flex-1">
        <h1 className="items-center pb-5 text-2xl">Password Reset</h1>
        <div className="pb-10">
          Password must be at least 8 characters long and contain at least one
          number, one special character, and one uppercase letter.
        </div>

        <form onSubmit={handlePasswordSubmit} className="w-full space-y-9">
          <div className="entry">
            <label htmlFor="currentPassword" className="right-label">
              <strong>Current Password: </strong>
            </label>
            <input
              className="data w-32 border-gray-800"
              type="password"
              id="currentPassword"
              name="currentPassword"
              required
            ></input>
          </div>
          <div className="entry">
            <label htmlFor="newPassword" className="right-label">
              <strong>New Password: </strong>
            </label>
            <input
              className="data w-32 border-gray-800"
              type="password"
              id="newPassword"
              name="newPassword"
              onChange={handlePasswordChange}
              autoComplete="off"
              required
            ></input>
          </div>

          <div className="entry">
            <label htmlFor="confirmPassword" className="right-label">
              <strong>Confirm Password: </strong>
            </label>
            <input
              className="data w-32 border-gray-800"
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              onChange={handlePasswordChange}
              autoComplete="off"
              required
            ></input>
          </div>

          <button
            type="submit"
            className={`rounded-full p-2 px-4 text-custom-white ${
              validatePasswords()
                ? "bg-custom-blue hover:bg-custom-blue-dark"
                : "bg-custom-gray"
            }`}
            disabled={!validatePasswords()}
          >
            Reset
          </button>
          <div className="text-red-500">
            {passwordFormValues.newPassword.length > 0 && !newPasswordValid && (
              <>
                <span>Invalid Password</span>
                <br />
              </>
            )}
            {!(
              passwordFormValues.newPassword ===
              passwordFormValues.confirmPassword
            ) && <span>Passwords do not match</span>}
          </div>
        </form>
      </div>
    </div>
  );
}
