import { faCircleNotch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";

export default function Loading(props) {
  return (
    <div>
      <FontAwesomeIcon
        className="text-blue-500"
        icon={faCircleNotch}
        spin
        size={props.size}
      />
    </div>
  );
}
