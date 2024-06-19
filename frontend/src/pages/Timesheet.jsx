import React from "react";
import TimesheetDay from "../components/TimesheetDay";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChevronLeft,
  faChevronRight,
} from "@fortawesome/free-solid-svg-icons";

export default function Timesheet() {
  return (
    <div className="flex h-full flex-col items-center">
      <div className="universal-component-style mb-4 flex items-center justify-center space-x-7 p-10 text-center text-3xl font-bold">
        <button>
          <FontAwesomeIcon
            className="text-blue-500 hover:text-blue-800"
            icon={faChevronLeft}
          />
        </button>
        <h1 className="">1/1/2024 - 1/7/2024</h1>
        <button>
          <FontAwesomeIcon
            className="text-blue-500 hover:text-blue-800"
            icon={faChevronRight}
          />
        </button>
      </div>
      <div className="universal-component-style flex h-full flex-col items-center rounded-xl p-4">
        <div className="flex h-full space-x-2">
          <TimesheetDay date="1/1/2024" day="Sunday" projects={[]} />
          <TimesheetDay
            date="1/2/2024"
            day="Monday"
            projects={[
              {
                project: "project1",
                hours: 2.3,
              },
              {
                project: "project2",
                hours: 1,
              },
              {
                project: "project3",
                hours: 3,
              },
              {
                project: "project4",
                hours: 1,
              },
              {
                project: "project5",
                hours: 1,
              },
            ]}
          />
          <TimesheetDay
            date="1/3/2024"
            day="Tuesday"
            projects={[
              {
                project: "project1",
                hours: 6,
              },
              {
                project: "project2",
                hours: 1,
              },
              {
                project: "project3",
                hours: 1,
              },
            ]}
          />
          <TimesheetDay
            date="1/4/2024"
            day="Wednesday"
            projects={[
              {
                project: "project3",
                hours: 8,
              },
            ]}
          />
          <TimesheetDay
            date="1/5/2024"
            day="Thursday"
            projects={[
              {
                project: "project2",
                hours: 2,
              },
              {
                project: "project3",
                hours: 6,
              },
            ]}
          />
          <TimesheetDay
            date="1/6/2024"
            day="Friday"
            projects={[
              {
                project: "project1",
                hours: 3,
              },
              {
                project: "project2",
                hours: 3,
              },
              {
                project: "project3",
                hours: 2,
              },
            ]}
          />
          <TimesheetDay date="1/7/2024" day="Saturday" projects={[]} />
        </div>
        <div className="mt-4 flex w-full justify-center space-x-4">
          <button className="w-1/5 rounded-full bg-orange-400 py-1 text-gray-100 hover:bg-orange-600">
            Clear
          </button>
          <button className="w-1/5 rounded-full bg-blue-500 py-1 text-gray-100 hover:bg-blue-800">
            Submit
          </button>
        </div>
      </div>
    </div>
  );
}
