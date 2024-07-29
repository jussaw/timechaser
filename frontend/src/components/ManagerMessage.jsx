import React, { useState, useEffect } from "react";

export default function ManagerMessage() {
  const [message, setMessage] = useState(null);

  //TODO: Set message from API
  useEffect(() => {
    setMessage("Taco Tuesday at Tacos Selene 🌮🌮🌮  ");
  }, []);

  return (
    <div className="dashboard-component flex h-full w-full flex-col bg-custom-white">
      <h1 className="w-full p-4 pb-0 text-center text-2xl font-semibold">
        Message from Manager
      </h1>
      <div className="m-6 flex h-full rounded-3xl bg-gray-200 p-4 px-6 text-start text-lg font-medium shadow-inner">
        {message ? message : "No message from manager"}
      </div>
    </div>
  );
}
