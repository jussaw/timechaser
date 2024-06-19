import React from "react";
import PlaceholderComponent from "../components/PlaceholderComponent";
import "../styles/UniversalComponent.css";

export default function Dashboard() {
  return (
    <div className="m-4 ml-4 flex flex-grow flex-row space-x-4">
      <div className="flex w-7/12 flex-col space-y-4">
        <div className="flex h-full w-full flex-row space-x-4">
          <PlaceholderComponent />
          <PlaceholderComponent />
        </div>
        <div className="flex h-full w-full flex-row space-x-4">
          <PlaceholderComponent />
        </div>
      </div>
      <div className="flex w-5/12 flex-col space-y-4">
        <div className="flex h-full w-full flex-col space-y-4">
          <PlaceholderComponent />
          <PlaceholderComponent />
        </div>
        <div className="flex h-full w-full flex-col space-y-4">
          <PlaceholderComponent />
          <PlaceholderComponent />
        </div>
      </div>
    </div>
  );
}
