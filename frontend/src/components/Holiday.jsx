import React from "react";
import "../styles/UniversalComponent.css";

const baseYear = new Date().getFullYear();

const holidayList = {
  NewYearsDay: {
    key: "NewYearsDay",
    name: "New Year's Day",
    date: new Date(baseYear, 0, 1), // January 1
    image: "src/assets/holidayImages/NewYearsDay.png",
  },
  MartinLutherKingJrDay: {
    key: "MartinLutherKingJrDay",
    name: "Martin Luther King Jr. Day",
    date: new Date(baseYear, 1, 19), // February 19
    image: "src/assets/holidayImages/MartinLutherKingJrDay.png",
  },
  MemorialDay: {
    key: "MemorialDay",
    name: "Memorial Day",
    date: new Date(baseYear, 4, 27), // May 27
    image: "src/assets/holidayImages/MemorialDay.png",
  },
  Juneteenth: {
    key: "Juneteenth",
    name: "Juneteenth",
    date: new Date(baseYear, 5, 19), // June 19
    image: "src/assets/holidayImages/Juneteenth.png",
  },
  IndependenceDay: {
    key: "IndependenceDay",
    name: "Independence Day",
    date: new Date(baseYear, 6, 4), // July 4
    image: "src/assets/holidayImages/IndependenceDay.png",
  },
  LaborDay: {
    key: "LaborDay",
    name: "Labor Day",
    date: new Date(baseYear, 8, 2), // September 2
    image: "src/assets/holidayImages/LaborDay.png",
  },
  ColumbusDay: {
    key: "ColumbusDay",
    name: "Columbus Day",
    date: new Date(baseYear, 9, 14), // October 14
    image: "src/assets/holidayImages/ColumbusDay.png",
  },
  VeteransDay: {
    key: "VeteransDay",
    name: "Veterans Day",
    date: new Date(baseYear, 10, 11), // November 11
    image: "src/assets/holidayImages/VeteransDay.png",
  },
  Thanksgiving: {
    key: "Thanksgiving",
    name: "Thanksgiving",
    date: new Date(baseYear, 10, 28), // November 28
    image: "src/assets/holidayImages/Thanksgiving.png",
  },
  Christmas: {
    key: "Christmas",
    name: "Christmas",
    date: new Date(baseYear, 11, 25), // December 25
    image: "src/assets/holidayImages/Christmas.png",
  },
};

function formatDateAsMonthDay(date) {
  const options = { month: "long", day: "numeric" };
  return date.toLocaleDateString("en-US", options);
}

export default function Holiday(props) {
  return (
    <div
      className={
        props.holiday === "Christmas" || props.holiday === "LaborDay"
          ? "relative h-28 w-80 overflow-hidden rounded-lg bg-cover bg-center bg-no-repeat text-custom-black"
          : "relative h-28 w-80 overflow-hidden rounded-lg bg-cover bg-center bg-no-repeat text-custom-white"
      }
      style={{
        backgroundImage: `url(${holidayList[props.holiday].image})`,
        backgroundSize: `100% 100%`,
      }}
    >
      <div className="absolute inset-0 flex items-center justify-center bg-custom-black bg-opacity-5">
        <div className="bg-opacity-0 text-center text-2xl font-extrabold">
          <p>{holidayList[props.holiday].name}</p>
          <p>{formatDateAsMonthDay(holidayList[props.holiday].date)}</p>
        </div>
      </div>
    </div>
  );
}

export { holidayList };
