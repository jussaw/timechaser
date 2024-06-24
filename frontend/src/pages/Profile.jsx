import React, { useState, useEffect } from "react";
import UserProfileComponent from "../components/UserProfileComponent";

export default function Profile() {
  return (
    <div className="full-page-component flex-grow">
      <div className="flex h-full pl-10 pt-10">
        <UserProfileComponent />
      </div>
    </div>
  );
}
