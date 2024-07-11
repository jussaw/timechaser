import React, { useState, useEffect } from "react";
import Select from "react-select";
import "../styles/Admin.css";

export default function Admin() {
  const [formValues, setFormValues] = useState({
    role: "",
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [isFormFieldsValid, setIsFormFieldsValid] = useState({
    role: false,
    firstName: false,
    lastName: false,
    username: false,
    password: false,
    confirmPassword: false,
  });
  const [isFormValid, setIsFormValid] = useState(false);
  const [focusedField, setFocusedField] = useState("");

  const formErrors = {
    firstName: "First name must not be empty",
    lastName: "Last name must not be empty",
    role: "Role must be selected",
    username: "Username must be at least 8 characters",
    password:
      "Password must be at least 8 characters long and contain at least one number, one uppercase letter, and one !@#$%^&*()-=[]~_+{}.",
    confirmPassword: "Passwords do not match",
  };
  const passwordRegex =
    /^(?=.*\d)(?=.*[A-Z])(?=.*[!@#$%^&*()\-[\]~_+{}]).{8,}$/;

  useEffect(() => {
    setIsFormFieldsValid({
      role: formValues["role"].trim() !== "",
      firstName: formValues["firstName"].trim() !== "",
      lastName: formValues["lastName"].trim() !== "",
      username: formValues["username"].length >= 8,
      password: passwordRegex.test(formValues["password"]),
      confirmPassword:
        passwordRegex.test(formValues["confirmPassword"]) &&
        formValues["confirmPassword"] === formValues["password"],
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
  }, [isFormFieldsValid]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleFocus = (e) => {
    setFocusedField(e.target.name);
  };

  const handleBlur = () => {
    setFocusedField("");
  };

  // TODO: Business logic for sending POST request to create user
  // TODO: Trim all fields before sending POST request
  const onSubmit = () => {};

  return (
    <div className="full-page-component flex flex-grow flex-col justify-start space-y-8 p-12">
      <h1 className="text-4xl font-bold">Create User: </h1>
      <form className="flex flex-grow flex-col justify-start space-y-8">
        <div className="flex w-full flex-col space-y-8">
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="role">
                Role:
              </label>
              <select
                className={
                  isFormFieldsValid["role"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                name="role"
                value={formValues.role}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              >
                <option value=""></option>
                <option value="admin">Admin</option>
                <option value="manager">Manager</option>
                <option value="user">User</option>
              </select>
              {/* <Select
                className={`basic-single ${
                  isFormFieldsValid["role"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }}`}
                classNamePrefix="select"
                defaultValue={"blue"}
                isDisabled={false}
                isLoading={false}
                isClearable={false}
                isRtl={false}
                isSearchable={false}
                name="role"
                options={["blue", "red", "orange"]}
              /> */}
            </div>
            {focusedField === "role" && !isFormFieldsValid["role"] && (
              <span className="admin-error">{formErrors["role"]}</span>
            )}
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="firstName">
                First Name:
              </label>
              <input
                className={
                  isFormFieldsValid["firstName"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="text"
                name="firstName"
                value={formValues.firstName}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
            {focusedField === "firstName" &&
              !isFormFieldsValid["firstName"] && (
                <span className="admin-error">{formErrors["firstName"]}</span>
              )}
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="lastName">
                Last Name:
              </label>
              <input
                className={
                  isFormFieldsValid["lastName"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="text"
                name="lastName"
                value={formValues.lastName}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
            {focusedField === "lastName" && !isFormFieldsValid["lastName"] && (
              <span className="admin-error">{formErrors["lastName"]}</span>
            )}
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="username">
                Username:
              </label>
              <input
                className={
                  isFormFieldsValid["username"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="text"
                name="username"
                value={formValues.username}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
            {focusedField === "username" && !isFormFieldsValid["username"] && (
              <span className="admin-error">{formErrors["username"]}</span>
            )}
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="password">
                Password:
              </label>
              <input
                className={
                  isFormFieldsValid["password"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="password"
                name="password"
                value={formValues.password}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
            {focusedField === "password" && !isFormFieldsValid["password"] && (
              <span className="admin-error">{formErrors["password"]}</span>
            )}
          </div>
          <div className="admin-label-input-error">
            <div className="admin-label-input">
              <label className="admin-label" htmlFor="confirmPassword">
                Confirm Password:
              </label>
              <input
                className={
                  isFormFieldsValid["confirmPassword"]
                    ? "admin-input"
                    : "admin-input-invalid"
                }
                type="password"
                name="confirmPassword"
                value={formValues.confirmPassword}
                onChange={handleChange}
                onFocus={handleFocus}
                onBlur={handleBlur}
              />
            </div>
            {focusedField === "confirmPassword" &&
              !isFormFieldsValid["confirmPassword"] && (
                <span className="admin-error">
                  {formErrors["confirmPassword"]}
                </span>
              )}
          </div>
        </div>
        <button
          className={`w-fit rounded-full p-2 px-8 text-custom-white ${
            isFormValid
              ? "bg-custom-blue hover:bg-custom-blue-dark"
              : "bg-custom-disable"
          }`}
          disabled={!isFormValid}
          onSubmit={onSubmit}
        >
          Create
        </button>
      </form>
      {/* TODO: Display error from API */}
    </div>
  );
}
