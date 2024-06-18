import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMinus } from "@fortawesome/free-solid-svg-icons";

export default function TimesheetDay(props) {
  return (
    <div className="border-1 flex min-h-full flex-col items-center justify-between rounded-xl shadow-lg">
      <div className="flex w-full flex-col items-center">
        <div className="flex w-full flex-col justify-center rounded-t-xl bg-blue-500 py-2 text-gray-100">
          <h1 className="text-center">{props.day}</h1>
          <h1 className="text-center">{props.date}</h1>
        </div>
        {/* <div className="w-full border-b-2 border-gray-800"></div> */}
        <div className="flex bg-gray-400"></div>
        <div className="h-full w-full">
          {props.projects.map((project, index) => (
            <div>
              <div
                className="flex w-full flex-row justify-between p-2"
                key={index}
              >
                <div className="flex flex-row">
                  <div key={index} className="mr-2">
                    {project.hours}
                  </div>
                  <div key={index} className="">
                    {project.project}
                  </div>
                </div>
                <div key={index} className="text-red-500">
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
