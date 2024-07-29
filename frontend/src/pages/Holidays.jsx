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
          <Holiday holiday="NewYearsDay" />
          <Holiday holiday="MartinLutherKingJrDay" />
          <Holiday holiday="MemorialDay" />
          <Holiday holiday="Juneteenth" />
          <Holiday holiday="IndependenceDay" />
        </div>
        <div className="flex flex-col space-y-5">
          <Holiday holiday="LaborDay" />
          <Holiday holiday="ColumbusDay" />
          <Holiday holiday="VeteransDay" />
          <Holiday holiday="Thanksgiving" />
          <Holiday holiday="Christmas" />
          <Holiday holiday="Madeup" />
        </div>
      </div>
    </div>
  );
}
