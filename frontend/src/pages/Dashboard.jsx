import React from "react";
import PlaceholderComponent from "../components/PlaceholderComponent";
import HourAllocationChart from "../components/HourAllocationChart";
import Welcome from "../components/Welcome";
import PtoRemaining from "../components/PtoRemaining";

export default function Dashboard() {
  return (
    <div className="flex flex-grow flex-row space-x-4">
      <div className="flex w-6/12 flex-col space-y-4">
        <div className="flex h-full w-full flex-col space-y-4">
          <Welcome />
          <PlaceholderComponent />
        </div>
        <div className="flex h-full w-full flex-row space-x-4">
          <PlaceholderComponent />
        </div>
      </div>
      <div className="flex w-6/12 flex-col space-y-4">
        <div className="flex h-full w-full flex-col space-y-4">
          <div className="flex h-2/6 flex-row space-x-4">
            <PtoRemaining />
            <PlaceholderComponent />
          </div>
          <div className="flex h-4/6 flex-row space-x-4">
            <HourAllocationChart />
            <PlaceholderComponent />
          </div>
        </div>
        <div className="flex h-full w-full flex-col space-y-4">
          <PlaceholderComponent />
          <PlaceholderComponent />
        </div>
      </div>
    </div>
  );
}
