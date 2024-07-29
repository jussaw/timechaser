import React, { useState, useEffect, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSackDollar } from "@fortawesome/free-solid-svg-icons";

export default function PaydayCountdown() {
  const { authData, setAuthData } = useContext(AuthContext);
  const [days, setDays] = useState(10);

  // TODO: setDays from API call
  useEffect(() => {
    if (authData) {
    }
  }, [authData]);

  return (
    <div className="dashboard-component flex w-full items-center justify-center space-x-2">
      <FontAwesomeIcon
        className="text-8xl text-green-500"
        icon={faSackDollar}
      />
      <div className="flex flex-col items-end space-y-2 rounded-3xl">
        <div className="text-3xl font-semibold">Next payday in:</div>
        <div className="rounded-3xl bg-custom-purple p-2 px-4 text-3xl font-bold">
          {days} days
        </div>
      </div>
    </div>
  );
}
