import React, { useState, useEffect, useContext } from "react";
import { AuthContext, useAuth } from "../../context/AuthContext";
import {
  getSundayOfCurrentWeek,
  getDateAfterXDays,
  getWelcomeFormattedDate,
  getWeekNumberAndYear,
  getFirstAndLastDayOfWeek,
} from "../../utils/DateHelper";

export default function Welcome() {
  const { authData, setAuthData } = useContext(AuthContext);
  const [firstName, setFirstName] = useState(authData.user.firstName);
  // TODO: Replace totalHours from API call
  const [workedHours, setWorkedHours] = useState(16);
  const [weekStartDate, setWeekStartDate] = useState(null);
  const [weekEndDate, setWeekEndDate] = useState(null);

  useEffect(() => {
    // const sunday = getSundayOfCurrentWeek();
    // setWeekStartDate(getWelcomeFormattedDate(sunday));
    // setWeekEndDate(getWelcomeFormattedDate(getDateAfterXDays(sunday, 7)));
    const { weekNumber, year } = getWeekNumberAndYear(new Date());
    const { startDate, endDate } = getFirstAndLastDayOfWeek(weekNumber, year);
    setWeekStartDate(getWelcomeFormattedDate(startDate));
    setWeekEndDate(getWelcomeFormattedDate(endDate));
  }, []);

  useEffect(() => {
    setFirstName(authData.user.firstName);
  }, [authData.user.firstName]);

  return (
    <div className="dashboard-component flex h-3/6 w-full flex-grow">
      <div className="ml-6 flex w-8/12 flex-col items-start justify-center space-y-4">
        <div className="items-start text-3xl font-bold">
          Welcome, {firstName} 👋
        </div>
        <div className="items-start text-xl">
          {console.log(weekStartDate)}
          {console.log(weekEndDate)}
          {weekStartDate} - {weekEndDate}
        </div>
      </div>
      <div className="m-6 ml-0 flex w-4/12 flex-col items-start justify-center rounded-3xl bg-custom-purple px-8">
        <div className="text-2xl font-semibold">Worked hours:</div>
        <div className="text-3xl font-bold">{workedHours} hours</div>
      </div>
    </div>
  );
}
