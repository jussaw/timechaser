import React from "react";
import "../styles/UniversalComponent.css";
import Holiday from "../components/Holiday";

export default function Holidays() {
  return (
    <div className="flex flex-grow flex-row">
      <div className="full-page-component flex-grow items-center justify-center p-12">
        <div className="justify-start space-y-8 pb-4 text-4xl font-bold">
          2024 Calendar
        </div>
        <div className="flex flex-grow flex-row space-x-4">
          <div className="flex w-2/12 flex-col space-y-4">
            <div className="h-36 w-56 text-custom-white">
              <Holiday holiday="NewYearsDay" />
            </div>
            <div className="h-36 w-56 text-custom-white">
              <Holiday holiday="MartinLutherKingJrDay" />
            </div>
            <div className="h-36 w-56 text-custom-white">
              <Holiday holiday="MemorialDay" />
            </div>
            <div className="h-36 w-56 text-custom-white">
              <Holiday holiday="Juneteenth" />
            </div>
            <div className="h-36 w-56 text-custom-white">
              <Holiday holiday="IndependenceDay" />
            </div>
          </div>
          <div className="flex w-6/12 flex-col space-y-4">
            <div className="h-36 w-56 text-custom-black">
              <Holiday holiday="LaborDay" />
            </div>
            <div className="h-36 w-56 text-custom-white">
              <Holiday holiday="ColumbusDay" />
            </div>
            <div className="h-36 w-56 text-custom-white">
              <Holiday holiday="VeteransDay" />
            </div>
            <div className="h-36 w-56 text-custom-white">
              <Holiday holiday="Thanksgiving" />
            </div>
            <div className="h-36 w-56 text-custom-black">
              <Holiday holiday="Christmas" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
