import { React, useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChevronLeft,
  faChevronRight,
  faCircleNotch,
} from "@fortawesome/free-solid-svg-icons";
import TimesheetDay from "../components/TimesheetDay";
import {
  getSundayOfCurrentWeek,
  getFormattedDate,
  getWeek,
} from "../utils/DateHelper";
import Loading from "../components/Loading";

export default function Timesheet() {
  const [isLoading, setIsLoading] = useState(true);
  const [currentWeek, setCurrentWeek] = useState([]);
  const [timesheet, setTimesheet] = useState([]);
  const [timesheetInput, setTimesheetInput] = useState({
    Sunday: {
      hours: "",
      project: "",
    },
    Monday: {
      hours: "",
      project: "",
    },
    Tuesday: {
      hours: "",
      project: "",
    },
    Wednesday: {
      hours: "",
      project: "",
    },
    Thursday: {
      hours: "",
      project: "",
    },
    Friday: {
      hours: "",
      project: "",
    },
    Saturday: {
      hours: "",
      project: "",
    },
  });

  useEffect(() => {
    setCurrentWeek(getWeek(getSundayOfCurrentWeek()));

    // TODO: Add API to gather current timesheet for when page loads
    // TODO: Set isLoading to false

    // TODO: Remove fetchData and fetchData call. Simulates API call
    const fetchData = async () => {
      // Simulate an API call delay
      await new Promise((resolve) => setTimeout(resolve, 2000));

      setTimesheet([
        {
          date: "1/1/2024",
          day: "Sunday",
          projects: [],
        },
        {
          date: "1/2/2024",
          day: "Monday",
          projects: [
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
          ],
        },
        {
          date: "1/3/2024",
          day: "Tuesday",
          projects: [
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
          ],
        },
        {
          date: "1/4/2024",
          day: "Wednesday",
          projects: [
            {
              project: "project3",
              hours: 8,
            },
          ],
        },
        {
          date: "1/5/2024",
          day: "Thursday",
          projects: [
            {
              project: "project2",
              hours: 2,
            },
            {
              project: "project3",
              hours: 6,
            },
          ],
        },
        {
          date: "1/6/2024",
          day: "Friday",
          projects: [
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
          ],
        },
        {
          date: "1/7/2024",
          day: "Saturday",
          projects: [],
        },
      ]);

      setIsLoading(false);
    };

    fetchData();
  }, []);

  useEffect(() => {
    // TODO: verification on the input fields
    // TODO: Try block, API call to submit times to user in try statement
    // TODO: Catch block, Errors and return out of function
    // TODO: Finally block, delete entries in timesheet input and
    // TODO: API GET request to update current timeSheet
    // console.log("timesheetinput", timesheetInput);
  }, [timesheetInput]);

  return (
    <div className="flex h-full w-full flex-col items-center">
      <div className="universal-component-style mb-4 flex items-center justify-center space-x-7 p-10 text-center text-3xl font-bold">
        <button>
          <FontAwesomeIcon
            className="text-blue-500 hover:text-blue-800"
            icon={faChevronLeft}
          />
        </button>
        <h1 className="">
          {isLoading ? (
            <></>
          ) : (
            <>
              {getFormattedDate(currentWeek[0])} -{" "}
              {getFormattedDate(currentWeek[6])}
            </>
          )}
        </h1>
        <button>
          <FontAwesomeIcon
            className="text-blue-500 hover:text-blue-800"
            icon={faChevronRight}
          />
        </button>
      </div>
      {isLoading ? (
        <div className="universal-component-style flex h-full w-full flex-col items-center justify-center rounded-xl p-4">
          <Loading size="5x" />
        </div>
      ) : (
        <div className="universal-component-style flex h-full w-full flex-col items-center rounded-xl p-4">
          <div className="flex h-full w-full grid-cols-7 space-x-2">
            {timesheet.map((timesheetDay, index) => (
              <TimesheetDay
                date={timesheetDay.date}
                day={timesheetDay.day}
                projects={timesheetDay.projects}
                timesheetInput={timesheetInput}
                setTimesheetInput={setTimesheetInput}
                key={index}
              />
            ))}
          </div>
          <div className="mt-4 flex w-full justify-center space-x-4">
            <button className="w-4/12 rounded-full bg-blue-500 py-1 text-gray-100 hover:bg-blue-800">
              Submit
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
