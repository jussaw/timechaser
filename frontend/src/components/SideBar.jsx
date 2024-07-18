import React, { useState, useEffect, useRef } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faClockRotateLeft,
  faChartColumn,
  faStopwatch,
  faUser,
  faArrowRightFromBracket,
  faBusinessTime,
} from "@fortawesome/free-solid-svg-icons";
import "../styles/UniversalComponent.css";
import "../styles/Sidebar.css";

export default function SideBar({ className }) {
  const { authData, setAuthData } = useContext(AuthContext);
  const navigate = useNavigate();
  const location = useLocation();

  const renderSidebar = !["/"].includes(location.pathname);
  const renderDarkDashboard = location.pathname === "/dashboard";
  const renderDarkTimesheet = location.pathname === "/timesheet";
  const renderDarkProfile = location.pathname === "/profile";
  const renderDarkManager = location.pathname === "/manager";
  //TODO: get managerPendingSubmissions count from
  const [managerPendingSubmissionsCount, setManagerPendingSubmissionsCount] =
    useState(1);
  //TODO: get user role and confirm if manager
  const [isManager, setIsManager] = useState(true);

  const onLogOut = () => {
    setAuthData(null);
  };

  useEffect(() => {
    if (!authData) {
      navigate("/");
    }
  }, [authData, navigate]);

  useEffect(() => {
    if (authData) {
      const expired = authData.tokenExpiration * 1000;
      if (Date.now() >= expired) {
        setAuthData(null);
        navigate("/");
      }
    }
  }, []);

  return renderSidebar ? (
    <div
      className={`${className} flex flex-col items-center justify-between p-4`}
    >
      <div className="w-full">
        <Link
          className="flex h-20 w-full items-center justify-start px-4 py-16"
          to="/dashboard"
        >
          <FontAwesomeIcon
            className="text-4xl text-custom-blue"
            icon={faClockRotateLeft}
          />
          <div className="pl-2 text-3xl font-bold">TimeChaser</div>
        </Link>
        <Link
          className={
            renderDarkDashboard ? "sidebar-button-dark" : "sidebar-button"
          }
          to="/dashboard"
        >
          <FontAwesomeIcon className="sidebar-icon" icon={faChartColumn} />
          <div className="sidebar-button-icon"> Dashboard</div>
        </Link>
        <Link
          className={
            renderDarkTimesheet ? "sidebar-button-dark" : "sidebar-button"
          }
          to="/timesheet"
        >
          <FontAwesomeIcon className="sidebar-icon" icon={faStopwatch} />
          <div className="sidebar-button-icon"> Timesheet</div>
        </Link>
        <Link
          className={
            renderDarkProfile ? "sidebar-button-dark" : "sidebar-button"
          }
          to="/profile"
        >
          <FontAwesomeIcon className="sidebar-icon" icon={faUser} />
          <div className="sidebar-button-icon"> Profile</div>
        </Link>
        {/* TODO: set visible only if manager */}
        {isManager && (
          <Link
            className={
              renderDarkManager ? "sidebar-button-dark" : "sidebar-button"
            }
            to="/manager"
          >
            <FontAwesomeIcon className="sidebar-icon" icon={faBusinessTime} />
            <div className="sidebar-button-icon"> Manager</div>
            {managerPendingSubmissionsCount != 0 && (
              <span className="ml-auto flex h-6 w-6 items-center justify-center rounded-full bg-custom-red p-3.5 text-custom-white">
                {managerPendingSubmissionsCount}
              </span>
            )}
          </Link>
        )}
      </div>
      <Link className="sidebar-button" to="/" onClick={onLogOut}>
        <FontAwesomeIcon className="text-xl" icon={faArrowRightFromBracket} />
        <div className="sidebar-button-icon"> Logout</div>
      </Link>
    </div>
  ) : null;
}
