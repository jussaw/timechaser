import axios from "axios";

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_SPRING_API_URL,
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

axiosInstance.interceptors.request.use(
  (config) => {
    if (!config.headers["Skip-Auth"]) {
      const token = JSON.parse(localStorage.getItem("authData"))["token"];
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

export default axiosInstance;
