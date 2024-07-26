import React from "react";
import "../styles/UniversalComponent.css";

export default function Holiday(props) {
  const holidayList = {
    NewYearsDay: {
      name: "New Year's Day",
      date: "January 1",
      image: "src/assets/holidayImages/NewYearsDay.png",
    },
    MartinLutherKingJrDay: {
      name: "Martin Luther King Jr. Day",
      date: "February 19",
      image: "src/assets/holidayImages/MartinLutherKingJrDay.png",
    },
    MemorialDay: {
      name: "Memorial Day",
      date: "May 27",
      image: "src/assets/holidayImages/MemorialDay.png",
    },
    Juneteenth: {
      name: "Juneteenth",
      date: "June 19",
      image: "src/assets/holidayImages/Juneteenth.png",
    },
    IndependenceDay: {
      name: "Independence Day",
      date: "July 4",
      image: "src/assets/holidayImages/IndependenceDay.png",
    },
    LaborDay: {
      name: "Labor Day",
      date: "September 2",
      image: "src/assets/holidayImages/LaborDay.png",
    },
    ColumbusDay: {
      name: "Columbus Day",
      date: "October 14",
      image: "src/assets/holidayImages/ColumbusDay.png",
    },
    VeteransDay: {
      name: "Veterans Day",
      date: "November 11",
      image: "src/assets/holidayImages/VeteransDay.png",
    },
    Thanksgiving: {
      name: "Thanksgiving",
      date: "November 28",
      image: "src/assets/holidayImages/Thanksgiving.png",
    },
    Christmas: {
      name: "Christmas",
      date: "December 25",
      image: "src/assets/holidayImages/Christmas.png",
    },
  };
  return (
    <div
      className={`${props.className} relative h-20 w-56 overflow-hidden rounded-lg bg-cover bg-center bg-no-repeat`}
      style={{
        backgroundImage: `url(${holidayList[props.holiday].image})`,
        backgroundSize: `100% 100%`,
      }}
    >
      {console.log(props)}
      <div className="absolute inset-0 flex items-center justify-center bg-custom-black bg-opacity-5">
        <div className="text-l bg-opacity-0 text-center font-extrabold">
          <p>{holidayList[props.holiday].name}</p>
          <p>{holidayList[props.holiday].date}</p>
        </div>
      </div>
    </div>
  );
}
