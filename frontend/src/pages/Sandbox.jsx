import React from "react";
import "../styles/UniversalComponent.css";

export default function Sandbox() {
  return (
    <div className="flex flex-grow">
      {/* Comment out this div */}
      <div className="full-page-component flex-grow items-center justify-center">
        <div className="flex h-full items-center justify-center">Sandbox</div>
      </div>
    </div>
  );
}
