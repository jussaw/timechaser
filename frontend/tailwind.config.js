/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        "custom-orange": "#FB923C",
        "custom-blue": {
          DEFAULT: "#3B82F6",
          dark: "#1E3A8A",
        },
        "custom-gray": "#9BA1B3",
        "custom-brown": "#968586",
        "custom-yellow": "#B4A58C",
        "custom-white": "#E1E5F2",
      },
    },
  },
  plugins: [],
};
