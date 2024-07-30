import React from "react";
import PlaceholderComponent from "../components/PlaceholderComponent";
import HourAllocationChart from "../components/HourAllocationChart";
import Welcome from "../components/Welcome";
import PtoRemaining from "../components/PtoRemaining";
import PaydayCountdown from "../components/PaydayCountdown";
import ManagerMessage from "../components/ManagerMessage";
import NewsFromCompany from "../components/NewsFromCompany";
import UpcomingHolidays from "../components/UpcomingHolidays";
export default function Dashboard() {
  return (
    <div className="flex flex-grow flex-row space-x-4">
      <div className="flex w-6/12 flex-col space-y-4">
        <div className="flex h-full w-full flex-col space-y-4">
          <Welcome />
          <NewsFromCompany />
        </div>
        <div className="flex h-full w-full flex-row space-x-4">
          <PlaceholderComponent />
        </div>
      </div>
      <div className="flex w-6/12 flex-col space-y-4">
        <div className="flex h-full w-full flex-col space-y-4">
          <div className="flex h-2/6 flex-row space-x-4">
            <PtoRemaining />
            <PaydayCountdown />
          </div>
          <div className="flex h-4/6 flex-row space-x-4">
            <HourAllocationChart />
            <PlaceholderComponent />
          </div>
        </div>
        <div className="flex h-full w-full flex-col space-y-4">
          <ManagerMessage />
          <UpcomingHolidays />
        </div>
      </div>
    </div>
  );
}
