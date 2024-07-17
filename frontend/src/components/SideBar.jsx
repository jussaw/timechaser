import React, { useEffect, useContext } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faClockRotateLeft,
  faChartColumn,
  faStopwatch,
  faUser,
  faArrowRightFromBracket,
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
          <FontAwesomeIcon className="text-xl" icon={faChartColumn} />
          <div className="sidebar-button-icon"> Dashboard</div>
        </Link>
        <Link
          className={
            renderDarkTimesheet ? "sidebar-button-dark" : "sidebar-button"
          }
          to="/timesheet"
        >
          <FontAwesomeIcon className="text-xl" icon={faStopwatch} />
          <div className="sidebar-button-icon"> Timesheet</div>
        </Link>
        <Link
          className={
            renderDarkProfile ? "sidebar-button-dark" : "sidebar-button"
          }
          to="/profile"
        >
          <FontAwesomeIcon className="text-xl" icon={faUser} />
          <div className="sidebar-button-icon"> Profile</div>
        </Link>
      </div>
      <Link className="sidebar-button" to="/" onClick={onLogOut}>
        <FontAwesomeIcon className="text-xl" icon={faArrowRightFromBracket} />
        <div className="sidebar-button-icon"> Logout</div>
      </Link>
    </div>
  ) : null;
}
