import React, { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSackDollar } from "@fortawesome/free-solid-svg-icons";

export default function PaydayCountdown() {
  const [days, setDays] = useState();

  useEffect(() => {
    setDays(daysUntilNextPayday());
  }, []);

  return (
    <div className="dashboard-component flex w-full items-center justify-center space-x-2">
      <FontAwesomeIcon
        className="text-8xl text-green-500"
        icon={faSackDollar}
      />
      <div className="flex flex-col items-end space-y-2 rounded-3xl">
        <div className="text-3xl font-semibold">Next payday in:</div>
        <div className="rounded-3xl bg-custom-purple p-2 px-4 text-3xl font-bold">
          {days === 0 ? "Today!" : `${days} days`}
        </div>
      </div>
    </div>
  );
}

// Hardcoded payday is 15th and 30th of every month
const daysUntilNextPayday = () => {
  const today = new Date();
  const day = today.getDate();
  const month = today.getMonth();
  const year = today.getFullYear();

  let nextDate;

  if (day <= 15) {
    nextDate = new Date(year, month, 15);
  } else if (day <= 30) {
    nextDate = new Date(year, month, 30);
  } else {
    nextDate = new Date(year, month + 1, 15);
  }

  const oneDay = 24 * 60 * 60 * 1000;
  const daysUntil = Math.round((nextDate - today) / oneDay);

  return daysUntil;
};
