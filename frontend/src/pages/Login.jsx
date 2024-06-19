import React from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faClockRotateLeft } from "@fortawesome/free-solid-svg-icons";
import "../styles/Auth.css";

export default function Login() {
  return (
    // TODO: Add Login Business Logic
    <div className="flex flex-grow items-center justify-center">
      <div className="dashboard-component p-16 pb-2">
        <div className="flex h-full flex-col items-center justify-between">
          <h1 className="mb-8 flex w-full items-center justify-center">
            <FontAwesomeIcon
              className="pr-2 text-3xl text-blue-500"
              icon={faClockRotateLeft}
            />
            <label className="auth-greeting-label">Welcome!</label>
          </h1>
          <div className="flex h-full w-full flex-col items-center justify-between pb-5">
            <form className="flex flex-col">
              <label className="auth-input-label">Username</label>
              <input
                type="text"
                autoComplete="true"
                className="auth-input-box"
              />
              <label className="auth-input-label">Password</label>
              <input type="password" className="auth-input-box" />
              <button className="auth-input-submit">Log in</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
