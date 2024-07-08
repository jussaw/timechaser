/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        "custom-blue": {
          DEFAULT: "#3B82F6",
          dark: "#1E3A8A",
        },
        "custom-purple": "#E9D5FF",
        "custom-gray": "#6B7280",
        "custom-brown": "#968586",
        "custom-khaki": "#B4A58C",
        "custom-white": "#F3F4F6",
        "custom-black": "#1F2937",
        "custom-red": "#EF4444",
        "custom-disable": "#9BA1B3",
        "custom-main-background": "#E5E7EB",
      },
    },
  },
  plugins: [],
};
