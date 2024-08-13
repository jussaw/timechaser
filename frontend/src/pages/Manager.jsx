import React, { useState, useEffect } from "react";
import "../styles/UniversalComponent.css";
import CreateProject from "../components/manager/CreateProject";
import Submission from "../components/manager/Submission";
import ManagerSidebar from "../components/manager/ManagerSidebar";

export default function Manager() {
  const [pendingApprovals, setPendingApprovals] = useState([]);
  const [isCreateProject, setIsCreateProject] = useState(true);
  const [currentSubmission, setCurrentSubmission] = useState(null);

  // TODO: Replace with API call
  useEffect(() => {
    setPendingApprovals(examplePendingApprovals);
  }, []);

  return (
    <div className="flex flex-grow space-x-4">
      <ManagerSidebar
        pendingApprovals={pendingApprovals}
        setIsCreateProject={setIsCreateProject}
        setCurrentSubmission={setCurrentSubmission}
      />
      {isCreateProject ? (
        <div className="flex h-full flex-grow items-center justify-center">
          <CreateProject />
        </div>
      ) : (
        <Submission submission={currentSubmission} />
      )}
    </div>
  );
}

// TODO: Delete after implementing API call
const examplePendingApprovals = [
  {
    timesheetId: 1,
    firstName: "Bob",
    lastName: "Smith",
    weekOfYear: 1,
    year: 2024,
    timesheet: [
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
    ],
  },
  {
    timesheetId: 2,
    firstName: "Jane",
    lastName: "Doe",
    weekOfYear: 1,
    year: 2024,
    timesheet: [
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
    ],
  },
  {
    timesheetId: 3,
    firstName: "George",
    lastName: "Washington",
    weekOfYear: 2,
    year: 2024,
    timesheet: [
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
    ],
  },
];
