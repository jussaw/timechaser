import axios from "axios";

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

// TODO: Add JWT to request
// Request interceptor for adding JWT
// axiosInstance.interceptors.request.use(
//   (config) => {
//     // Retrieve token from localStorage, cookies, or wherever you store it
//     const token = localStorage.getItem('accessToken');
//     if (token) {
//       config.headers.Authorization = `Bearer ${token}`;
//     }
//     return config;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );

// TODO: implement
// Response interceptor for refreshing token on 401 responses
// axiosInstance.interceptors.response.use(
//   (response) => {
//     // Handle and refresh token if expired, for example
//     return response;
//   },
//   (error) => {
//     // Handle 401 Unauthorized responses
//     if (error.response.status === 401) {
//       // Logic to handle token refresh or logout user
//     }
//     return Promise.reject(error);
//   }
// );

export default axiosInstance;
