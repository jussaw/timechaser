import React from "react";
import TimeSheetDay from "../components/TimeSheetDay";

export default function Timesheet() {
  return (
    <div className="full-page-component flex flex-grow items-start justify-center">
      <div className="m-4 grid w-full grid-cols-7 rounded-xl">
        <TimeSheetDay
          date="1/1/2024"
          projects={[
            {
              project: "project1",
              hours: 2,
            },
            {
              project: "project2",
              hours: 1,
            },
            {
              project: "project3",
              hours: 5,
            },
          ]}
        />
        <TimeSheetDay
          date="1/2/2024"
          projects={[
            {
              project: "project1",
              hours: 1,
            },
            {
              project: "project2",
              hours: 7,
            },
          ]}
        />
        <TimeSheetDay
          date="1/3/2024"
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
        <TimeSheetDay
          date="1/4/2024"
          projects={[
            {
              project: "project3",
              hours: 8,
            },
          ]}
        />
        <TimeSheetDay
          date="1/5/2024"
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
        <TimeSheetDay
          date="1/6/2024"
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
        <TimeSheetDay
          date="1/7/2024"
          projects={[
            {
              project: "project2",
              hours: 8,
            },
          ]}
        />
      </div>
    </div>
  );
}
