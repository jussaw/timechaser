import React, { useState, useEffect } from "react";
import SubmissionDay from "./SubmissionDay";
import { getDateRange } from "../../utils/DateHelper";

export default function Submission(props) {
  const [totalHours, setTotalHours] = useState(0);

  useEffect(() => {
    let totalHours = 0;
    props.submission.timesheet.forEach((day) => {
      day.projects.forEach((project) => {
        totalHours += project.hours;
      });
    });
    setTotalHours(totalHours);
  }, []);

  const handleApprove = () => {
    // TODO: API request for approval of submission
  };

  return (
    <div className="flex flex-grow flex-col items-center space-y-4">
      <div className="universal-component-style flex flex-col items-center justify-center space-y-2 px-10 py-4 text-center text-3xl font-bold">
        <h1>
          {props.submission.firstName} {props.submission.lastName}
        </h1>
        <h1>
          {getDateRange(props.submission.weekOfYear, props.submission.year)}
        </h1>
      </div>
      <div className="full-page-component flex w-full flex-grow flex-col items-center justify-center space-y-4 p-4">
        <ul className="flex h-full w-full space-x-2">
          {props.submission.timesheet.map((submissionDay, index) => (
            <li key={index} className="w-full">
              <SubmissionDay submissionDay={submissionDay} />
            </li>
          ))}
        </ul>
        <div className="flex w-full items-center justify-between">
          <div className="w-4/12" />
          <button
            className="w-4/12 rounded-full bg-custom-blue py-1 text-custom-white shadow-lg hover:bg-custom-blue-dark active:shadow-inner"
            onClick={handleApprove}
          >
            Approve
          </button>
          <div className="w-4/12 px-4 text-xl">
            <div className="ml-auto w-fit rounded-2xl bg-gray-300 px-4 py-1 font-bold shadow-inner">
              Week Total Hours:
              <span className="ml-2">{totalHours}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
