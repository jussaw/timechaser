import React from "react";
import { holidayList } from "../Holiday";
import Holiday from "../Holiday";

export default function UpcomingHolidays() {
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  const twoWeeksLater = new Date(today);
  twoWeeksLater.setDate(today.getDate() + 14);

  //array of upcoming holidays
  const upcomingHolidays = Object.values(holidayList).filter((holiday) => {
    return holiday.date >= today && holiday.date <= twoWeeksLater;
  });

  return upcomingHolidays.length > 0 ? (
    <div className="dashboard-component flex h-full w-full flex-col bg-custom-white">
      <h1 className="w-full p-4 pb-0 text-center text-2xl font-semibold">
        Upcoming Holidays
      </h1>
      <div className="m-6 flex h-full justify-center space-x-12">
        {upcomingHolidays.map((holiday, index) => (
          <Holiday key={index} holiday={holiday.name} />
        ))}
      </div>
    </div>
  ) : null;
}
