import React, { useState, useEffect, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faMinus,
  faClock,
  faClipboard,
  faTrash,
  faPlus,
  faXmark,
} from "@fortawesome/free-solid-svg-icons";

export default function TimesheetDay(props) {
  const [isHovered, setIsHovered] = useState(false);
  const [showInputs, setShowInputs] = useState(false);
  const [formData, setFormData] = useState({
    project: null,
    hours: null,
  });
  const hoverRef = useRef(null);

  useEffect(() => {
    const handleMouseEnter = () => setIsHovered(true);
    const handleMouseLeave = () => setIsHovered(false);

    const node = hoverRef.current;
    if (node) {
      node.addEventListener("mouseenter", handleMouseEnter);
      node.addEventListener("mouseleave", handleMouseLeave);

      // Clean up event listeners on component unmount
      return () => {
        node.removeEventListener("mouseenter", handleMouseEnter);
        node.removeEventListener("mouseleave", handleMouseLeave);
      };
    }
  }, [hoverRef.current]);

  const handlePlusClick = () => {
    setShowInputs(true);
  };

  const handleMinusClick = () => {
    setShowInputs(false);
    props.setTimesheetInput((prevState) => ({
      ...prevState,
      [props.day]: {
        ...prevState[props.day],
        hours: "",
        project: "",
      },
    }));
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    props.setTimesheetInput((prevState) => ({
      ...prevState,
      [props.day]: {
        ...prevState[props.day],
        [name]: value,
      },
    }));
  };

  return (
    <div
      ref={hoverRef}
      className="flex h-full w-full flex-col items-center justify-between rounded-xl shadow-lg"
    >
      <div className="flex w-full flex-col items-center">
        <div className="flex w-full flex-col justify-center rounded-t-xl bg-custom-blue py-2 text-custom-white">
          <h2 className="text-center text-lg">{props.day}</h2>
          <h2 className="text-center text-lg">{props.date}</h2>
          <hr className="border-t-1 m-1 border-custom-white" />
          <div className="flex flex-row justify-between p-2 pb-0">
            <FontAwesomeIcon icon={faClock} />
            <FontAwesomeIcon icon={faClipboard} />
            <FontAwesomeIcon icon={faTrash} />
          </div>
        </div>
        <ul className="h-full w-full">
          {props.projects.map((project, index) => (
            <li key={index}>
              <div className="flex w-full flex-row justify-between p-2">
                <div className="w-1/12 text-start">{project.hours}</div>
                <div className="w-10/12 text-center">{project.project}</div>
                <div className="text-custom-red w-1/12 text-end">
                  <FontAwesomeIcon icon={faMinus} />
                </div>
              </div>
              <div className="flex justify-center">
                <hr className="via-custom-black w-11/12 border-t-2 bg-gradient-to-r from-custom-white to-transparent px-2" />
              </div>
            </li>
          ))}
          <div className="flex w-full justify-center">
            {isHovered && !showInputs && (
              <button onClick={handlePlusClick} className="mt-2">
                <FontAwesomeIcon className="text-custom-gray" icon={faPlus} />
              </button>
            )}
            {showInputs && (
              <form className="mt-3 w-full px-2">
                <div className="mb-2 flex">
                  <input
                    className="text-md mr-1 w-3/12 rounded-lg border border-custom-blue bg-custom-white px-1"
                    id="hours"
                    name="hours"
                    value={props.timesheetInput[props.day].hours}
                    onChange={handleChange}
                  />
                  <input
                    className="text-md mr-1 w-8/12 rounded-lg border border-custom-blue bg-custom-white px-1"
                    id="project"
                    name="project"
                    value={props.timesheetInput[props.day].project}
                    onChange={handleChange}
                  />
                  <button
                    onClick={handleMinusClick}
                    className="text-custom-red ml-2 w-1/12 items-center text-end"
                  >
                    <FontAwesomeIcon icon={faXmark} />
                  </button>
                </div>
              </form>
            )}
          </div>
        </ul>
      </div>
    </div>
  );
}
