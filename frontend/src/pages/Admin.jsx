import React, { useState, useEffect } from "react";
import "../styles/Admin.css";

export default function Admin() {
  const [formValues, setFormValues] = useState({
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    confirmPassword: "",
    role: "",
  });
  const [isFormFieldsValid, setIsFormFieldsValid] = useState({
    firstName: false,
    lastName: false,
    username: false,
    password: false,
    confirmPassword: false,
    role: false,
  });
  const [formErrors, setFormErrors] = useState({
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    confirmPassword: "",
    role: "",
  });
  const [isFormValid, setIsFormValid] = useState(false);

  useEffect(() => {
    // Set each individual field validity to true
    // TODO: Update qualifiers for username, password, and confirmPassword
    setIsFormFieldsValid({
      firstName: formValues["firstName"].trim() !== "",
      lastName: formValues["lastName"].trim() !== "",
      username: formValues["username"].trim() !== "",
      password: formValues["password"].trim() !== "",
      confirmPassword: formValues["confirmPassword"].trim() !== "",
      role: formValues["role"].trim() !== "",
    });
  }, [formValues]);

  useEffect(() => {
    let valid = true;
    for (let key in isFormFieldsValid) {
      if (!isFormFieldsValid[key]) {
        valid = false;
        break;
      }
    }
    setIsFormValid(valid);
    // TODO: Set errors for unqualified fields
  }, [isFormFieldsValid]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  return (
    <div className="full-page-component flex flex-grow flex-col justify-start space-y-10 p-12">
      <h1 className="text-4xl font-bold">Create User: </h1>
      <form className="flex flex-grow flex-col justify-start space-y-10">
        <div className="flex flex-col space-y-10">
          <div className="admin-input-label-box">
            <label className="admin-label-box" htmlFor="">
              First Name:
            </label>
            <input
              className="admin-input-box"
              type="text"
              name="firstName"
              value={formValues.firstName}
              onChange={handleChange}
            />
          </div>
          <div className="admin-input-label-box">
            <label className="admin-label-box" htmlFor="">
              Last Name:
            </label>
            <input
              className="admin-input-box"
              type="text"
              name="lastName"
              value={formValues.lastName}
              onChange={handleChange}
            />
          </div>
          <div className="admin-input-label-box">
            <label className="admin-label-box" htmlFor="">
              Role:
            </label>
            <input
              className="admin-input-box"
              type="text"
              name="role"
              value={formValues.role}
              onChange={handleChange}
            />
          </div>
          <div className="admin-input-label-box">
            <label className="admin-label-box" htmlFor="">
              Username:
            </label>
            <input
              className="admin-input-box"
              type="text"
              name="username"
              value={formValues.username}
              onChange={handleChange}
            />
          </div>
          <div className="admin-input-label-box">
            <label className="admin-label-box" htmlFor="">
              Password:
            </label>
            <input
              className="admin-input-box"
              type="password"
              name="password"
              value={formValues.password}
              onChange={handleChange}
            />
          </div>
          <div className="admin-input-label-box">
            <label className="admin-label-box" htmlFor="">
              Confirm Password:
            </label>
            <input
              className="admin-input-box"
              type="password"
              name="confirmPassword"
              value={formValues.confirmPassword}
              onChange={handleChange}
            />
          </div>
        </div>
        <button
          className={`w-fit rounded-full p-3 px-10 text-custom-white ${
            isFormValid
              ? "bg-custom-blue hover:bg-custom-blue-dark"
              : "bg-custom-disable"
          }`}
          disabled={!isFormValid}
        >
          Create
        </button>
      </form>
      {/* TODO: Display error from API */}
    </div>
  );
}
