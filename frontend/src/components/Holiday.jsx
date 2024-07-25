import React from "react";
import "../styles/UniversalComponent.css";

export default function Holiday(props) {
  const holidayList = {
    NewYearsDay: {
      name: "New Year's Day",
      date: "January 1",
      image: "src/assets/holidayImages/NewYearsDayImage.png",
    },
    MartinLutherKingJrDay: {
      name: "Martin Luther King Jr Day",
      date: "February 19",
      image: "src/assets/holidayImages/MartinLutherKingJrDayImage.png",
    },
    MemorialDay: {
      name: "Memorial Day",
      date: "May 27",
      image: "src/assets/holidayImages/MemorialDayImage.png",
    },
    Juneteenth: {
      name: "Juneteenth",
      date: "June 19",
      image: "src/assets/holidayImages/JuneteenthImage.png",
    },
    IndependenceDay: {
      name: "Independence Day",
      date: "July 4",
      image: "src/assets/holidayImages/IndependenceDayImage.png",
    },
    LaborDay: {
      name: "Labor Day",
      date: "September 2",
      image: "src/assets/holidayImages/LaborDayImage.png",
    },
    ColumbusDay: {
      name: "Columbus Day",
      date: "October 14",
      image: "src/assets/holidayImages/ColumbusDayImage.png",
    },
    VeteransDay: {
      name: "Veterans Day",
      date: "November 11",
      image: "src/assets/holidayImages/VeteransDayImage.png",
    },
    Thanksgiving: {
      name: "Thanksgiving",
      date: "November 28",
      image: "src/assets/holidayImages/ThanksgivingImage.png",
    },
    Christmas: {
      name: "Christmas",
      date: "December 25",
      image: "src/assets/holidayImages/ChristmasImage.png",
    },
  };
  return (
    <div
      className="relative h-full w-auto overflow-hidden rounded-lg bg-cover bg-center bg-no-repeat"
      style={{
        backgroundImage: `url(${holidayList[props.holiday].image})`,
        backgroundSize: `100% 100%`,
      }}
    >
      <div className="absolute inset-0 flex items-center justify-center bg-custom-black bg-opacity-5">
        <div className="text-l bg-opacity-0 text-center font-extrabold">
          <p>{holidayList[props.holiday].name}</p>
          <p>{holidayList[props.holiday].date}</p>
        </div>
      </div>
    </div>
  );
}
