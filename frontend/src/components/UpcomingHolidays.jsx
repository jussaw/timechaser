// UpcomingHolidays.jsx
import React from "react";
import { holidayList } from "./Holiday";
import Holiday from "./Holiday";

export default function UpcomingHolidays() {
  const today = new Date();
  const twoWeeksLater = new Date(today);
  twoWeeksLater.setDate(today.getDate() + 14);

  //array of upcoming holidays
  const upcomingHolidays = Object.values(holidayList).filter((holiday) => {
    return holiday.date >= today && holiday.date <= twoWeeksLater;
  });

  return (
    <div className="dashboard-component flex h-1/2 w-full items-center justify-center bg-custom-white">
      <div className="h-full w-full">
        <h1 className="">Upcoming Holidays</h1>
        {console.log(upcomingHolidays)}
        {upcomingHolidays.length > 0 ? (
          upcomingHolidays.map((holiday, index) => (
            <Holiday key={index} holiday={holiday.name} />
          ))
        ) : (
          <p>No upcoming holidays in the next two weeks.</p>
        )}
      </div>
    </div>
  );
}
