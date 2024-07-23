import React, { useState, useEffect, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMoon } from "@fortawesome/free-solid-svg-icons";

export default function PtoRemaining() {
  const { authData, setAuthData } = useContext(AuthContext);
  const [hours, setHours] = useState(10);

  //TODO: retrieve pto from APi call
  useEffect(() => {
    if (authData) {
    }
  }, [authData]);

  return (
    <div className="dashboard-component flex w-full items-center justify-center bg-custom-white">
      <FontAwesomeIcon
        className="-rotate-12 text-9xl text-blue-300"
        icon={faMoon}
      />
      <div className="flex flex-col items-end space-y-2 rounded-3xl text-3xl">
        <div className="font-semibold">PTO remaining:</div>
        <div className="font-bold">{hours} hours</div>
      </div>
    </div>
  );
}
