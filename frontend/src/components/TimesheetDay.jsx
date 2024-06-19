import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faMinus,
  faClock,
  faClipboard,
  faTrash,
} from "@fortawesome/free-solid-svg-icons";

export default function TimesheetDay(props) {
  return (
    <div className="border-1 flex min-h-full flex-col items-center justify-between rounded-xl shadow-lg">
      <div className="flex w-full flex-col items-center">
        <div className="flex w-full flex-col justify-center rounded-t-xl bg-blue-500 py-2 text-gray-100">
          <h1 className="text-center">{props.day}</h1>
          <h1 className="text-center">{props.date}</h1>
          <div className="flex flex-row justify-between p-2 pb-0">
            <FontAwesomeIcon icon={faClock} />
            <FontAwesomeIcon icon={faClipboard} />
            <FontAwesomeIcon icon={faTrash} />
          </div>
        </div>
        {/* <div className="w-full border-b-2 border-gray-800"></div> */}
        <hr className="flex bg-gray-400" />
        <div className="h-full w-full">
          {props.projects.map((project, index) => (
            <div>
              <div
                className="flex w-full flex-row justify-between p-2"
                // className="grid-row w-full p-2"
                // className="grid grid-cols-3 p-2"
                key={index}
              >
                <div key={index} className="w-1/12 text-start">
                  {project.hours}
                </div>
                <div key={index} className="w-10/12 text-center">
                  {project.project}
                </div>
                <div key={index} className="w-1/12 text-end text-red-500">
                  <FontAwesomeIcon icon={faMinus} />
                </div>
              </div>
              {index < props.projects.length - 1 && (
                <hr className="border-t-2 border-gray-500" />
              )}
            </div>
          ))}
        </div>
      </div>
      <form className="mb-2 w-full px-2">
        <div className="">
          <div className="mb-2 flex">
            <input
              className="mr-1 w-4/5 rounded-lg border border-blue-500 px-2"
              placeholder="Hours"
            />
            <input
              className="ml-1 w-full rounded-lg border border-blue-500 px-2"
              placeholder="Project"
            />
          </div>
        </div>
      </form>
    </div>
  );
}
