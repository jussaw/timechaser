import React, { useState, useEffect, useRef } from "react";
import axiosInstance from "../../config/axiosConfig";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";

export default function CreateProject() {
  const [project, setProject] = useState("");
  const [isProjectValid, setIsProjectValid] = useState("");
  const [successfulProject, setSuccessfulProject] = useState("");
  const [submissionError, setSubmissionError] = useState(null);
  const [isSubmissionSuccess, setIsSubmissionSuccess] = useState(false);

  const inputRef = useRef(null);

  useEffect(() => {
    inputRef.current.focus();
  }, []);

  useEffect(() => {
    if (project) {
      setIsProjectValid(true);
    } else {
      setIsProjectValid(false);
    }
  }, [project]);

  const mapErrorCode = (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 400:
          return "Required fields are not set";
        default:
          return "Internal Server Error";
      }
    } else {
      return "Server Offline";
    }
  };

  const handleChange = (e) => {
    setProject(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmissionError(null);
    setIsSubmissionSuccess(false);
    console.log("Sending POST");

    axiosInstance
      .post("/project", {
        name: project,
      })
      .then((response) => {
        console.log("Successful project creation");
        setSuccessfulProject(project);
        setIsSubmissionSuccess(true);
      })
      .catch((error) => {
        setSubmissionError(mapErrorCode(error));
        console.error("Error project Creation", error);
      });
  };

  return (
    <div className="full-page-component h-fit items-center justify-start space-y-6 px-12 py-9">
      <h1 className="w-full text-center text-3xl font-bold">
        Create New Project
      </h1>
      <form
        className="flex h-fit w-full flex-col items-center space-y-6"
        onSubmit={handleSubmit}
      >
        <div className="flex flex-col space-y-1">
          <label className="text-xl" htmlFor="project">
            Project Name
          </label>
          <input
            ref={inputRef}
            className="w-60 rounded-full border border-custom-blue bg-custom-white px-3 py-1"
            type="text"
            name="project"
            value={project}
            onChange={handleChange}
          />
        </div>
        <button
          className={
            isProjectValid
              ? `ml-auto mr-auto w-40 rounded-full bg-custom-blue py-1 text-xl text-custom-white`
              : "ml-auto mr-auto w-40 rounded-full bg-custom-gray py-1 text-xl text-custom-white"
          }
          type="submit"
          disabled={!isProjectValid}
        >
          Create
        </button>
      </form>
      {submissionError && (
        <div className="w-fit rounded-md bg-custom-red-light px-3 py-2 text-custom-red">
          <FontAwesomeIcon className="mr-2" icon={faTriangleExclamation} />
          {submissionError}
        </div>
      )}
      {isSubmissionSuccess && (
        <div>
          <div className="w-fit rounded-md bg-custom-green-light px-3 py-2 text-custom-green-dark">
            🎉 Successfully created project: {successfulProject}
          </div>
        </div>
      )}
    </div>
  );
}
