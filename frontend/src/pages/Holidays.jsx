import React from "react";
import "../styles/UniversalComponent.css";
import Holiday from "../components/Holiday";

export default function Holidays() {
  return (
    <div className="full-page-component flex-grow items-center justify-center space-y-10 p-12">
      <h1 className="justify-start space-y-8 pb-4 text-4xl font-bold">
        2024 Holiday Calendar
      </h1>
      <div className="flex flex-grow flex-row space-x-20">
        <div className="flex flex-col space-y-5">
          <Holiday className="text-custom-white" holiday="NewYearsDay" />
          <Holiday
            className="text-custom-white"
            holiday="MartinLutherKingJrDay"
          />
          <Holiday className="text-custom-white" holiday="MemorialDay" />
          <Holiday className="text-custom-white" holiday="Juneteenth" />
          <Holiday className="text-custom-white" holiday="IndependenceDay" />
        </div>
        <div className="flex flex-col space-y-5">
          <Holiday className="text-custom-black" holiday="LaborDay" />
          <Holiday className="text-custom-white" holiday="ColumbusDay" />
          <Holiday className="text-custom-white" holiday="VeteransDay" />
          <Holiday className="text-custom-white" holiday="Thanksgiving" />
          <Holiday className="text-custom-black" holiday="Christmas" />
        </div>
      </div>
    </div>
  );
}
