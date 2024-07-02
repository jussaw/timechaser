import React, { useState, useEffect, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark, faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import "../styles/Profile.css";

export default function Profile() {
  //Separate display from form values so they can update independently
  const [displayName, setDisplayName] = useState({
    firstName: "",
    lastName: "",
  });
  //TO-DO: get fields from API
  const [formValues, setFormValues] = useState({
    username: "e40040000",
    firstName: "Jonah",
    lastName: "Leung",
  });
  const [timeZone, setTimeZone] = useState("");
  const [currentTime, setCurrentTime] = useState(new Date());
  //TO-DO: split apart supervisor name? Depends on API
  const [supervisor, setSupervisor] = useState({
    name: "Emperor Pennoni",
  });
  //password reset fields
  const [showPasswordForm, setShowPasswordForm] = useState(false);
  const toggleShowPasswordForm = () => {
    setShowPasswordForm(!showPasswordForm);
  };
  //states used for buttons
  const [isEditingFirstName, setIsEditingFirstName] = useState(false);
  const [isEditingLastName, setIsEditingLastName] = useState(false);
  const [isFormChanged, setIsFormChanged] = useState(false);

  const firstNameInputRef = useRef(null);
  const lastNameInputRef = useRef(null);

  //name should come from backend, but for now stubbed to be formValues.name
  useEffect(() => {
    setDisplayName(formValues);
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

  //update view when editing the form
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    //need an anonymous function here to update view immediately instead of next react refresh
    setFormValues((prevValues) => {
      const updatedFormValues = {
        ...prevValues,
        [name]: value,
      };
      setIsFormChanged(
        updatedFormValues.firstName != displayName.firstName ||
          updatedFormValues.lastName != displayName.lastName,
      );
      return updatedFormValues;
    });
  };

  /*TO-DO: send info to backend
  business logic for submitting form*/
  const handleSubmitLeft = (event) => {
    //stop page from reloading on submit
    event.preventDefault();
    setDisplayName(formValues);
    //If fields were being edited, disable edit mode after saving
    if (isEditingFirstName || isEditingLastName) {
      setIsEditingFirstName(false);
      setIsEditingLastName(false);
      setIsFormChanged(false);
    }
  };

  const handleSubmitRight = (event) => {
    event.preventDefault();
  };

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
  //TODO: refactor all colors to fit palette
  return (
    <div className="full-page-component user-info flex h-full w-full flex-grow pl-10 pt-10">
      <div className="left w-half flex-1">
        <h1 className="block items-center pb-5 text-2xl">
          Welcome, {displayName.firstName} {displayName.lastName}
        </h1>
        <div className="supervisor pb-10">Reports to: {supervisor.name}</div>
        <form onSubmit={handleSubmitLeft} className="form w-full space-y-9">
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
              value={formValues.firstName}
              onChange={handleInputChange}
              readOnly={!isEditingFirstName}
              autoComplete="off"
            ></input>
            <button
              type="button"
              onClick={toggleEditFirstName}
              className="edit-button mr-56"
            >
              {/* TODO change colors to custom color palette */}
              <FontAwesomeIcon
                className={
                  isEditingFirstName ? "text-red-500" : "text-blue-500"
                }
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
              value={formValues.lastName}
              onChange={handleInputChange}
              readOnly={!isEditingLastName}
              autoComplete="off"
            ></input>
            <button
              type="button"
              onClick={toggleEditLastName}
              className="edit-button mr-56"
            >
              {/* TODO change colors to custom color palette */}
              <FontAwesomeIcon
                className={isEditingLastName ? "text-red-500" : "text-blue-500"}
                icon={isEditingLastName ? faXmark : faPenToSquare}
              />
            </button>
          </div>
          <div className="entry">
            <label htmlFor="username" className="left-label">
              <strong>Username: </strong>
            </label>
            <span className="data border-custom-white">
              {formValues.username}
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
            className={`submit-button rounded-full p-2 px-4 text-custom-white ${isFormChanged ? "active bg-custom-blue" : "inactive bg-custom-gray"}`}
            disabled={!isFormChanged}
          >
            Save
          </button>
          <button
            type=""
            className="pwd-button my-10 text-custom-blue"
            onClick={toggleShowPasswordForm}
          >
            Reset Password
          </button>
        </form>
      </div>
      <div className="right flex-1">
        <form onSubmit={handleSubmitRight} className="form w-full space-y-9">
          <div className="entry">
            <label htmlFor="currentPassword" className="right-label">
              <strong>Current Password: </strong>
            </label>
            <input
              className="data w-32 border-gray-800"
              type="text"
              id="currentPassword"
              name="currentPassword"
              value={formValues.firstName}
              onChange={handleInputChange}
            ></input>
            {/* TODO change colors to custom color palette */}
          </div>
          <div className="entry">
            <label htmlFor="lastName" className="left-label">
              <strong>Last Name: </strong>
            </label>
            <input
              ref={lastNameInputRef}
              className="data mr-2 w-32 border-gray-800"
              type="text"
              id="lastName"
              name="lastName"
              value={formValues.lastName}
              onChange={handleInputChange}
              readOnly={!isEditingLastName}
              autoComplete="off"
            ></input>
            <button
              type="button"
              onClick={toggleEditLastName}
              className="edit-button mr-56"
            >
              {/* TODO change colors to custom color palette */}
              <FontAwesomeIcon
                className={isEditingLastName ? "text-red-500" : "text-blue-500"}
                icon={isEditingLastName ? faXmark : faPenToSquare}
              />
            </button>
          </div>
          <div className="entry">
            <label htmlFor="username" className="left-label">
              <strong>Username: </strong>
            </label>
            <span className="data border-custom-white">
              {formValues.username}
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
            className={`submit-button rounded-full p-2 px-4 text-custom-white ${isFormChanged ? "active bg-custom-blue" : "inactive bg-custom-gray"}`}
            disabled={!isFormChanged}
          >
            Save
          </button>
          <button
            type=""
            className="pwd-button my-10 text-custom-blue"
            onClick={toggleShowPasswordForm}
          >
            Reset Password
          </button>
        </form>
      </div>
    </div>
  );
}
