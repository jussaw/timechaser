import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SideBar from "./components/SideBar";
import Dashboard from "./pages/Dashboard";
import Sandbox from "./pages/Sandbox";
import Timesheet from "./pages/Timesheet";
import Login from "./pages/Login";
import "./styles/UniversalComponent.css";
import Profile from "./pages/Profile";
import Manager from "./pages/Manager";
import { AuthProvider } from "./context/AuthContext";

export default function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="flex h-screen w-screen flex-col bg-custom-main-background p-4 font-sans text-custom-black">
          <div className="flex h-full w-full flex-row">
            <SideBar className="sidebar-component" />
            <Routes>
              <Route path="/" element={<Login />} />
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/timesheet" element={<Timesheet />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/manager" element={<Manager />} />

              {/* TODO: Delete Later. Only for Sandboxing */}
              <Route path="/sandbox" element={<Sandbox />} />
            </Routes>
          </div>
        </div>
      </Router>
    </AuthProvider>
  );
}
