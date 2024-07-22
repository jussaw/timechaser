import React, { useState, useEffect, useContext } from "react";
import { AuthContext } from "../context/AuthContext";

import crescent from "../assets/crescent.png";

export default function PtoRemaining() {
  const { authData, setAuthData } = useContext(AuthContext);
  const [hours, setHours] = useState(10);
  //TODO: retrieve pto from APi call
  useEffect(() => {
    if (authData) {
    }
  }, [authData]);
  return (
    <div
      className="dashboard-component h-6/12 relative w-full flex-grow items-center justify-center bg-custom-white bg-cover bg-center"
      style={{
        backgroundImage: `url(${crescent})`,
        backgroundSize: "50%",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "left",
        backgroundColor: "#F3F4F6",
      }}
    >
      <div className="m-4 ml-6 flex w-9/12 flex-col rounded-3xl">
        <div className="text-lg font-semibold">Pto remaining:</div>
        <div className="ml-auto text-3xl font-semibold">{hours} hours</div>
      </div>
    </div>
  );
}
