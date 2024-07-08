import React from "react";
import { useState } from "react";

export default function Welcome() {
  // TODO: Replace firstName with what is in AuthContext
  const [firstName, setFirstName] = useState("Justin");
  // TODO: Replace totalHours from API call
  const [totalHours, setTotalHours] = useState(16);
  // TODO: Repace weekEndDate and weekStartDate with API call
  const [weekStartDate, setWeekStartDate] = useState("Sun, July 7, 2024");
  const [weekEndDate, setWeekEndDate] = useState("Sun, July 14, 2024");

  return (
    <div className="dashboard-component flex h-3/6 w-full flex-grow">
      <div className="ml-6 flex w-8/12 flex-col items-start justify-center space-y-4">
        <div className="items-start text-3xl font-bold">
          Welcome, {firstName} 👋
        </div>
        <div className="items-start text-xl">
          {weekStartDate} - {weekEndDate}
        </div>
      </div>
      <div className="bg-custom-purple m-6 ml-0 flex w-4/12 flex-col items-start justify-center rounded-3xl px-8">
        <div className="text-lg font-semibold">Total worked hours:</div>
        <div className="text-2xl font-bold">{totalHours} hours</div>
      </div>
    </div>
  );
}
