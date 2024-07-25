import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  useLocation,
} from "react-router-dom";
import SideBar from "./components/SideBar";
import Dashboard from "./pages/Dashboard";
import Sandbox from "./pages/Sandbox";
import Timesheet from "./pages/Timesheet";
import Login from "./pages/Login";
import Profile from "./pages/Profile";
import Manager from "./pages/Manager";
import Admin from "./pages/Admin";
import Holiday from "./pages/Holidays";
import { AuthProvider } from "./context/AuthContext";

function AppContent() {
  const location = useLocation();

  return (
    <div className="flex h-screen w-screen flex-col bg-custom-main-background p-4 font-sans text-custom-black">
      <div className="flex h-full w-full flex-row">
        {location.pathname !== "/" && <SideBar />}
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/timesheet" element={<Timesheet />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/holidays" element={<Holiday />} />
          <Route path="/manager" element={<Manager />} />
          <Route path="/admin" element={<Admin />} />
          <Route path="/sandbox" element={<Sandbox />} />
        </Routes>
      </div>
    </div>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <Router>
        <AppContent />
      </Router>
    </AuthProvider>
  );
}
