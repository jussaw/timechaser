# TimeChaser Front End

Welcome to the front end of TimeChaser, a time tracker application designed for employers to use with their employees. This project is built using React.js, Vite, pnpm, and Tailwind CSS.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [Folder Structure](#folder-structure)
- [Usage](#usage)

## Features

- Track employee working hours
- Roles management (user, manager, admin)
- Permissions
  - Managers can approve time requests
  - Admins can create users
- Real-time updates
- User authentication
- Responsive design
- Easy-to-use interface

## Getting Started

### Prerequisites

Ensure you have the following installed on your machine:

- [Node.js](https://nodejs.org/) (version 14 or later)
- [pnpm](https://pnpm.io/)

### Installation

1. Clone the repository:

   ```sh
   git clone https://bitbucket.cyber-range.ray.com/scm/tc/timechaser.git
   cd timechaser/frontend
   ```

2. Install the dependencies

   ```sh
   pnpm install
   ```

3. Running the Application
   ```sh
   pnpm run dev
   ```
   This will start the Vite development server and you can view the application in your browser at http://localhost:5173.

### Folder Structure

The project's folder structure is organized as follows:

```php
frontend/
├── public/                 # Static files
├── src/                    # Source files
│ ├── assets/               # Assets (images, fonts, etc.)
│ ├── components/           # React components
│ ├── pages/                # Page components
│ ├── styles/               # Global styles (Tailwind)
│ ├── App.jsx               # Main application component
│ ├── index.css             # Global CSS file
│ └── main.jsx              # Entry point
├── .env                    # Default environment file
├── .env.dev                # Development environment file
├── .env.prod               # Production environment file
├── .eslintrc.cjs           # ESLint configuration
├── .gitignore              # Git ignore file
├── .prettierrc             # Prettier configuration
├── index.html              # HTML template
├── package.json            # Project metadata and dependencies
├── pnpm-lock.yaml          # pnpm lock file
├── postcss.config.js       # PostCSS configuration
├── README.md               # Project documentation
├── tailwind.config.js      # Tailwind CSS configuration
└── vite.config.js          # Vite configuration
```

### Usage

- Authentication: Users can sign up, log in, and log out.
- Time Tracking: Employees can clock in and clock out, and managers can view/approve logged hours.
- Dashboard: Provides an overview of logged hours, PTO requests, and more.
