import React, { useState, useEffect, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark, faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import "../styles/Profile.css";

export default function Profile() {
  const [isFormChanged, setIsFormChanged] = useState(false);

  const [displayName, setDisplayName] = useState({
    firstName: "",
    lastName: "",
  });
  const [formValues, setFormValues] = useState({
    username: "",
    firstName: "",
    lastName: "",
  });
  const [timeZone, setTimeZone] = useState("");
  const [currentTime, setCurrentTime] = useState(new Date());
  const [supervisor, setSupervisor] = useState({
    name: "",
  });
  const [showPasswordForm, setShowPasswordForm] = useState(false);
  const [passwordResetValues, setPasswordResetValues] = useState({
    currentPassword: "",
    newPassword: "",
    confirmPassword: "",
  });
  const [isEditingFirstName, setIsEditingFirstName] = useState(false);
  const [isEditingLastName, setIsEditingLastName] = useState(false);
  const firstNameInputRef = useRef(null);
  const lastNameInputRef = useRef(null);

  useEffect(() => {
    // Mock data initialization
    setDisplayName({ firstName: "John", lastName: "Doe" });
    setFormValues({ username: "johndoe", firstName: "John", lastName: "Doe" });
    setSupervisor({ name: "Jane Doe" });

    // Getting time zone from device
    try {
      const tz = Intl.DateTimeFormat().resolvedOptions().timeZone;
      setTimeZone(tz);
    } catch (error) {
      console.error("Can't get the time zone", error);
      setTimeZone("Unknown");
    }

    // Update clock every second
    const timer = setInterval(() => {
      setCurrentTime(new Date());
    }, 1000);

    return () => clearInterval(timer);
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setDisplayName(formValues);
  };

  const toggleEditFirstName = () => {
    setIsEditingFirstName((prevIsEditingFirstName) => !prevIsEditingFirstName);
    if (!isEditingFirstName) {
      setTimeout(() => firstNameInputRef.current.focus(), 0);
    }
  };

  const toggleEditLastName = () => {
    setIsEditingLastName((prevIsEditingLastName) => !prevIsEditingLastName);
    if (!isEditingLastName) {
      setTimeout(() => lastNameInputRef.current.focus(), 0);
    }
  };

  const togglePasswordForm = () => {
    setShowPasswordForm(!showPasswordForm);
  };

  const handlePasswordInputChange = (e) => {
    const { name, value } = e.target;
    setPasswordResetValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }));
  };

  const handlePasswordSubmit = (event) => {
    event.preventDefault();
    // Handle password reset logic here (e.g., send data to backend)
    console.log("Password reset values:", passwordResetValues);
    // Clear form inputs after submission
    setPasswordResetValues({
      currentPassword: "",
      newPassword: "",
      confirmPassword: "",
    });
    // Hide the password reset form after submission
    setShowPasswordForm(false);
  };

  return (
    <div className="full-page-component container flex h-full w-full flex-grow pl-10 pt-10">
      <div className="flex-1">
        <form className="leftForm">
          <h2>Left form</h2>
          <label>Name</label>
          <br />
          <label>Name</label>
          <br />
          <label>Name</label>
          <br />
          <label>Name</label>
          <br />
          <label>Name</label>
          <br />
        </form>
      </div>
      <div className="flex-1">
        <form className="leftForm">
          <h2>Left form</h2>
          <label>Name</label>
          <br />
          <label>Name</label>
          <br />
          <label>Name</label>
          <br />
          <label>Name</label>
          <br />
          <label>Name</label>
          <br />
        </form>
      </div>
    </div>
  );
}
