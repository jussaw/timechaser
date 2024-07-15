import React from "react";
import PlaceholderComponent from "../components/PlaceholderComponent";
import "../styles/UniversalComponent.css";
import HourAllocationChart from "../components/HourAllocationChart";
import Welcome from "../components/Welcome";
import PtoRemaining from "../components/PtoRemaining";
import star from "../assets/star.png";

export default function Sandbox() {
  return (
    <div className="flex w-6/12 flex-col space-y-4">
      <div className="flex h-full w-full flex-col space-y-4">
        <div className="flex flex-row space-x-4">
          <div
            className="dashboard-component h-6/12 relative w-full flex-grow items-center justify-center bg-cover bg-center"
            style={{ backgroundImage: `url(${star})` }}
          >
            <div className="text-lg font-semibold">Pto remaining:</div>
            <div className="ml-auto text-3xl font-semibold">10 hours</div>
          </div>
          <PlaceholderComponent />
        </div>
      </div>
    </div>
  );
}
