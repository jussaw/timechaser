import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { getDateRange } from "../../utils/DateHelper";

export default function ManagerSidebar(props) {
  const [activeButton, setActiveButton] = useState(null);

  const handleCreateProject = () => {
    props.setIsCreateProject(true);
    setActiveButton("createProject");
  };

  const handleSubmissionSwitch = (index) => {
    props.setIsCreateProject(false);
    props.setCurrentSubmission(props.pendingApprovals[index]);
    setActiveButton(index);
  };

  return (
    <div className="full-page-component flex h-full w-2/12 flex-col items-start justify-start space-y-2 border-2 border-r px-2 py-4 text-xl">
      <button
        onClick={handleCreateProject}
        className={`h-fit w-full rounded-2xl bg-sky-400 p-2 font-semibold text-custom-white hover:bg-sky-500 hover:shadow-xl ${
          activeButton === "createProject" ? "shadow-inner" : ""
        }`}
      >
        <FontAwesomeIcon className="pr-2" icon={faPlus} />
        Create Project
      </button>
      {props.pendingApprovals.map((pendingApproval, index) => (
        <button
          key={index}
          onClick={() => handleSubmissionSwitch(index)}
          className={`h-fit w-full rounded-2xl px-3 py-1 text-start font-semibold hover:bg-indigo-200 hover:shadow-xl ${
            activeButton === index
              ? "bg-indigo-300 shadow-inner"
              : "bg-indigo-100"
          }`}
        >
          {pendingApproval.firstName} {pendingApproval.lastName}
          <br />
          {getDateRange(pendingApproval.weekOfYear, pendingApproval.year)}
        </button>
      ))}
    </div>
  );
}
