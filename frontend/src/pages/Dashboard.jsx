import React from "react";
import PlaceholderComponent from "../components/PlaceholderComponent";
import "../styles/UniversalComponent.css";

export default function Dashboard({ className }) {
  return (
    <div className={`${className} mb-2 mr-2 mt-2 grid grid-rows-2`}>
      <div className="grid grid-cols-2">
        <div className="grid grid-rows-2">
          <PlaceholderComponent className="dashboard-component" />
          <PlaceholderComponent className="dashboard-component" />
        </div>
        <div className="grid grid-rows-2">
          <PlaceholderComponent className="dashboard-component" />
          <PlaceholderComponent className="dashboard-component" />
        </div>
      </div>
      <div className="grid grid-cols-2">
        <div className="grid grid-rows-2">
          <PlaceholderComponent className="dashboard-component" />
          <PlaceholderComponent className="dashboard-component" />
        </div>
        <div className="grid grid-rows-2">
          <PlaceholderComponent className="dashboard-component" />
          <PlaceholderComponent className="dashboard-component" />
        </div>
      </div>
    </div>
  );
}
