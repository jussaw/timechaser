import React, { useState, useEffect } from "react";
import "../styles/UserProfileComponent.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark, faPenToSquare } from "@fortawesome/free-solid-svg-icons";

export default function Profile() {
  //Separate display from form values so they can update independently
  const [displayName, setDisplayName] = useState({
    firstName: "",
    lastName: "",
  });
  //TO-DO: get fields from API
  const [formValues, setFormValues] = useState({
    username: "e400",
    firstName: "Jonah",
    lastName: "Leung",
  });
  const [timeZone, setTimeZone] = useState("");
  const [currentTime, setCurrentTime] = useState(new Date());
  //TO-DO: split apart supervisor name? Depends on API
  const [supervisor, setSupervisor] = useState({
    name: "Emperor Pennoni",
  });

  //states used for buttons
  const [isEditingFirstName, setIsEditingFirstName] = useState(false);
  const [isEditingLastName, setIsEditingLastName] = useState(false);
  const [isFormChanged, setIsFormChanged] = useState(false);

  //name should come from backend, but for now stubbed to be formValues.name
  useEffect(() => {
    setDisplayName(formValues);
  }, []);

  //getting time zone from device
  useEffect(() => {
    try {
      const tz = Intl.DateTimeFormat().resolvedOptions().timeZone;
      setTimeZone(tz);
      console.log(tz);
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
  const handleSubmit = (event) => {
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

  //update first name field
  const toggleEditFirstName = () => {
    setFormValues((prevFormValues) => {
      const updatedFormValues = {
        ...prevFormValues,
        firstName: isEditingFirstName
          ? displayName.firstName
          : prevFormValues.firstName,
      };
      setIsFormChanged(
        updatedFormValues.firstName !== displayName.firstName ||
          updatedFormValues.lastName !== displayName.lastName,
      );
      return updatedFormValues;
    });
    setIsEditingFirstName((prevIsEditingFirstName) => !prevIsEditingFirstName);
  };

  //update last name field
  const toggleEditLastName = () => {
    setFormValues((prevFormValues) => {
      const updatedFormValues = {
        ...prevFormValues,
        lastName: isEditingLastName
          ? displayName.lastName
          : prevFormValues.lastName,
      };
      setIsFormChanged(
        updatedFormValues.firstName !== displayName.firstName ||
          updatedFormValues.lastName !== displayName.lastName,
      );
      return updatedFormValues;
    });
    setIsEditingLastName((prevIsEditingLastName) => !prevIsEditingLastName);
  };

  return (
    <div className="user-info">
      <div className="welcome-display">
        <h1 className="items-center justify-center pb-5 text-2xl">
          Welcome, {displayName.firstName} {displayName.lastName}
        </h1>
      </div>
      <div className="supervisor pb-10">Reports to: {supervisor.name}</div>
      <form onSubmit={handleSubmit} className="form">
        <div className="entry">
          <label htmlFor="firstName">
            <strong>First Name: </strong>
          </label>
          <input
            className={`input bg-custom-white ${isEditingFirstName ? "editing border-2 border-solid border-custom-brown" : ""}`}
            type="text"
            id="firstName"
            name="firstName"
            value={formValues.firstName}
            onChange={handleInputChange}
            readOnly={!isEditingFirstName}
          ></input>
          <button
            type="button"
            onClick={toggleEditFirstName}
            className="edit-button"
          >
            <FontAwesomeIcon
              icon={isEditingFirstName ? faXmark : faPenToSquare}
            />
          </button>
        </div>
        <div className="entry">
          <label htmlFor="lastName">
            <strong>Last Name: </strong>
          </label>
          <input
            className={`input bg-custom-white ${isEditingLastName ? "editing border-2 border-solid border-custom-brown" : ""}`}
            type="text"
            id="lastName"
            name="lastName"
            value={formValues.lastName}
            onChange={handleInputChange}
            readOnly={!isEditingLastName}
          ></input>
          <button
            type="button"
            onClick={toggleEditLastName}
            className="edit-button"
          >
            <FontAwesomeIcon
              icon={isEditingLastName ? faXmark : faPenToSquare}
            />
          </button>
        </div>
        <div className="entry">
          <label htmlFor="username">
            <strong>Username: </strong>
          </label>
          <input
            className="username bg-custom-white"
            type="text"
            id="username"
            name="username"
            value={formValues.username}
            readOnly
          ></input>
        </div>
        <div className="entry">
          <label>
            <strong>Time zone: </strong>
          </label>
          <span className="timeZone">{timeZone}</span>
        </div>
        <div className="entry">
          <label>
            <strong>Current Time: </strong>
          </label>
          <span className="currentTime">
            {currentTime.toLocaleTimeString()}
          </span>
        </div>
        <button
          type="submit"
          className={`submit-button rounded-md p-2 text-custom-white ${isFormChanged ? "active bg-custom-blue" : "inactive bg-custom-gray"}`}
          disabled={!isFormChanged}
        >
          Save
        </button>
      </form>
    </div>
  );
}
