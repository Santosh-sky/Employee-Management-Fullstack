import { useState } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import EditEmployee from "./pages/EditEmployee";
import Login from "./pages/Login";
import EmployeeList from "./pages/EmployeeList";
import AddEmployee from "./pages/AddEmployee";
import Navbar from "./components/Navbar";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(
    localStorage.getItem("token") !== null
  );

  const [refresh, setRefresh] = useState(false);

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
  };

  const refreshEmployees = () => {
    setRefresh(!refresh);
  };

  return (
    <BrowserRouter>
      {isLoggedIn && <Navbar onLogout={handleLogout} />}

      <div className="container mt-4">
        <Routes>
          <Route
            path="/login"
            element={
              isLoggedIn ? (
                <Navigate to="/employees" />
              ) : (
                <Login onLoginSuccess={handleLoginSuccess} />
              )
            }
          />

          <Route
            path="/employees"
            element={
              isLoggedIn ? (
                <EmployeeList refresh={refresh} />
              ) : (
                <Navigate to="/login" />
              )
            }
          />

          <Route
            path="/add-employee"
            element={
              isLoggedIn ? (
                <AddEmployee onEmployeeAdded={refreshEmployees} />
              ) : (
                <Navigate to="/login" />
              )
            }
          />

          <Route
            path="/"
            element={
              isLoggedIn ? (
                <Navigate to="/employees" />
              ) : (
                <Navigate to="/login" />
              )
            }
          />

          <Route
              path="/edit-employee/:id"
              element={
                isLoggedIn ? (
                  <EditEmployee />
                ) : (
                  <Navigate to="/login" />
                )
              }
            />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;