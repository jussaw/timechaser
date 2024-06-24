import React, { useState, useEffect } from "react";
import "../styles/UserProfileComponent.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark, faPenToSquare } from "@fortawesome/free-solid-svg-icons";

export default function Profile() {
  //Separate display from form values so they can update independently
  const [displayName, setDisplayName] = useState({
    name: "",
  });
  const [formValues, setFormValues] = useState({
    username: "e400",
    name: "Jonah Leung",
  });
  const [timeZone, setTimeZone] = useState("");
  const [currentTime, setCurrentTime] = useState(new Date());
  const [supervisor, setSupervisor] = useState({
    name: "Emperor Pennoni",
  });

  const [isEditingName, setIsEditingName] = useState(false);
  //state for button changing from greyed out to clickable
  const [isFormChanged, setIsFormChanged] = useState(false);

  //name should come from backend, but for now stubbed to be formValues.name
  useEffect(() => {
    setDisplayName(formValues);
  }, []);

  //getting time zone from device
  useEffect(() => {
    try {
      const tz = Intl.DateTimeFormat().resolvedOptions().timeZone;

      // const tz = 2;
      setTimeZone(tz);
      console.log(tz);
    } catch (error) {
      console.error("Can't get the time zone", error);
      setTimeZone("Unknown");
    }
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
        updatedFormValues.username != displayName.username ||
          updatedFormValues.name != displayName.name,
      );
      return updatedFormValues;
    });
  };

  //business logic for submitting form
  const handleSubmit = (event) => {
    //stop page from reloading on submit
    event.preventDefault();
    setDisplayName(formValues);
    //If username was being edited, disable edit mode after saving
    if (isEditingName) {
      setIsEditingName(false);
      setIsFormChanged(false);
    }
  };

  const toggleEditName = () => {
    setFormValues((prevFormValues) => {
      const updatedFormValues = {
        ...prevFormValues,
        name: isEditingName ? displayName.name : prevFormValues.name,
      };
      setIsFormChanged(updatedFormValues.name !== displayName.name);
      return updatedFormValues;
    });
    setIsEditingName((prevIsEditingName) => !prevIsEditingName);

    // setIsEditingName(!isEditingName);
    // if (isEditingName) {
    //   setFormValues({
    //     ...formValues,
    //     name: displayName.name,
    //   });
    //   setIsEditingName(isEditingName);
  };

  return (
    <div className="user-info">
      <div className="welcome-display">
        <h1 className="items-center justify-center pb-5 text-2xl">
          Welcome, {displayName.name}
        </h1>
      </div>
      <div className="supervisor pb-10">Reports to: {supervisor.name}</div>
      <form onSubmit={handleSubmit} className="form">
        <div className="entry">
          <label htmlFor="name">
            <strong>Name: </strong>
          </label>
          <input
            className={`input ${isEditingName ? "editing border-2 border-solid border-custom-brown" : ""}`}
            type="text"
            id="name"
            name="name"
            value={formValues.name}
            onChange={handleInputChange}
            readOnly={!isEditingName}
          ></input>
          <button
            type="button"
            onClick={toggleEditName}
            className="edit-button"
          >
            <FontAwesomeIcon icon={isEditingName ? faXmark : faPenToSquare} />
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
          className={`submit-button ${isFormChanged ? "active bg-custom-blue" : "inactive bg-custom-gray"}`}
          disabled={!isFormChanged}
        >
          Save
        </button>
      </form>
    </div>
  );
}
