import React, { useState, useEffect, useContext } from "react";
import crescent from "../assets/crescent.png";

export default function PtoRemaining() {
  const [hours, setHours] = useState();
  //TODO: retrieve pto from APi call
  useEffect(() => {
    if (authData) {
      authData.roles.forEach((role) => {
        switch (role.id) {
          case 1:
            setRoles((prevState) => ({ ...prevState, ["isAdmin"]: true }));
            break;
          case 2:
            setRoles((prevState) => ({ ...prevState, ["isManager"]: true }));
            break;
          case 3:
            setRoles((prevState) => ({ ...prevState, ["isEmployee"]: true }));
            break;
        }
      });
    }
  }, [authData]);
  return (
    <div
      className="dashboard-component h-6/12 relative w-full flex-grow items-center justify-center bg-cover bg-center"
      style={{
        backgroundImage: `url(${crescent})`,
        backgroundSize: "50%",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "left",
      }}
    >
      <div className="m-4 ml-6 flex w-9/12 flex-col rounded-3xl">
        <div className="text-lg font-semibold">Pto remaining:</div>
        <div className="ml-auto text-3xl font-semibold">10 hours</div>
      </div>
    </div>
  );
}
