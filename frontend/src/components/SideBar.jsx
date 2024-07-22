import React, { useState, useEffect, useContext } from "react";
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
  faUserTie,
} from "@fortawesome/free-solid-svg-icons";
import "../styles/UniversalComponent.css";
import "../styles/Sidebar.css";

export default function SideBar() {
  const { authData, setAuthData } = useContext(AuthContext);
  const navigate = useNavigate();
  const location = useLocation();

  const [roles, setRoles] = useState({
    isAdmin: false,
    isManager: false,
    isEmployee: false,
  });

  //TODO: get managerPendingSubmissions count from API call
  const [managerPendingSubmissionsCount, setManagerPendingSubmissionsCount] =
    useState(1);

  const renderDarkDashboard = location.pathname === "/dashboard";
  const renderDarkTimesheet = location.pathname === "/timesheet";
  const renderDarkProfile = location.pathname === "/profile";
  const renderDarkManager = location.pathname === "/manager";
  const renderDarkAdmin = location.pathname === "/admin";

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

  useEffect(() => {
    if (authData) {
      setRoles({
        isAdmin: false,
        isManager: false,
        isEmployee: false,
      });
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
      className={
        "sidebar-component flex flex-col items-center justify-between p-4"
      }
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
        {roles.isManager && (
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
        {roles.isAdmin && (
          <Link
            className={
              renderDarkAdmin ? "sidebar-button-dark" : "sidebar-button"
            }
            to="/admin"
          >
            <FontAwesomeIcon className="sidebar-icon" icon={faUserTie} />
            <div className="sidebar-button-icon"> Admin</div>
          </Link>
        )}
      </div>
      <Link className="sidebar-button" to="/" onClick={onLogOut}>
        <FontAwesomeIcon
          className="sidebar-icon"
          icon={faArrowRightFromBracket}
        />
        <div className="sidebar-button-icon"> Logout</div>
      </Link>
    </div>
  );
}
