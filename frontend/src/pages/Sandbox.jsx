import React, { useState } from "react";

function PasswordField({ label }) {
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div>
      <label>{label}:</label>
      <input
        type={showPassword ? "text" : "password"}
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={togglePasswordVisibility}>
        {showPassword ? "Hide" : "Show"}
      </button>
    </div>
  );
}

function PasswordForm() {
  return (
    <form>
      <PasswordField label="Password 1" />
      <PasswordField label="Password 2" />
      <PasswordField label="Password 3" />
      {/* Add more PasswordField components as needed */}
      <button type="submit">Submit</button>
    </form>
  );
}

export default PasswordForm;
