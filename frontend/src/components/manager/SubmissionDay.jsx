import React, { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faClock, faClipboard } from "@fortawesome/free-solid-svg-icons";

export default function SubmissionDay(props) {
  const [totalHours, setTotalHours] = useState(0);

  useEffect(() => {
    let totalHours = 0;
    props.submissionDay.projects.forEach((project) => {
      totalHours += project.hours;
    });
    setTotalHours(totalHours);
  }, []);

  return (
    <div className="flex h-full flex-col items-center rounded-xl shadow-lg">
      <div className="flex w-full flex-col rounded-t-xl bg-custom-blue py-2 text-center text-lg text-custom-white">
        <h2 className="font-semibold">{props.submissionDay.day}</h2>
        <h2 className="">{props.submissionDay.date}</h2>
        <hr className="border-t-1 m-1 border-custom-white" />
        <div className="flex flex-row justify-between p-2 pb-0">
          <FontAwesomeIcon icon={faClock} className="w-2/12 text-center" />
          <FontAwesomeIcon icon={faClipboard} className="w-10/12 text-center" />
        </div>
      </div>
      <ul className="flex w-full flex-col">
        {props.submissionDay.projects.map((project, index) => (
          <li className="flex w-full flex-col items-center" key={index}>
            <div className="flex h-10 w-full flex-grow items-center justify-center p-2">
              <span className="w-2/12 text-center">{project.hours}</span>
              <span className="w-10/12 text-center">{project.project}</span>
            </div>
            <hr className="w-11/12 border-t-2 bg-gradient-to-r from-custom-white via-custom-black to-transparent px-2" />
          </li>
        ))}
      </ul>
      <span className="mb-2 mt-auto w-36 rounded-xl bg-gray-300 px-3 py-1 text-center font-semibold shadow-inner">
        Total Hours: {totalHours}
      </span>
    </div>
  );
}
