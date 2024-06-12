import React from "react";

export default function TimeSheetDay(props) {
  return (
    <div className="mx-1 flex h-full flex-col items-center justify-between rounded-xl border border-gray-800 py-2 shadow-lg">
      <div className="flex w-full flex-col items-center">
        <div>{props.date}</div>{" "}
        <div className="my-2 w-full border-b-2 border-gray-800"></div>
        <div className="w-full">
          {props.projects.map((project, index) => (
            <div className="flex w-full flex-row justify-between">
              <div key={index} className="flex-1">
                {project.hours}
              </div>
              <div key={index} className="flex-1">
                {project.project}
              </div>
              <div key={index} className="flex-1">
                X
              </div>
            </div>
          ))}
        </div>
      </div>
      <div className="m-2 mb-0 flex w-11/12">
        <input
          className="mr-1 mt-2 w-3/5 rounded-lg border border-blue-500 px-2"
          placeholder="project"
        />
        <input
          className="ml-1 mt-2 w-2/5 rounded-lg border border-blue-500 px-2"
          placeholder="hours"
        />
      </div>
    </div>
  );
}
