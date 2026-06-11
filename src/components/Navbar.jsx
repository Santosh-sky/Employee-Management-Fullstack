import { Link, useNavigate } from "react-router-dom";

function Navbar({ onLogout }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    onLogout();
    navigate("/login");
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4">
      <Link className="navbar-brand fw-bold" to="/employees">
        Employee System
      </Link>

      <div className="navbar-nav">
        <Link className="nav-link" to="/employees">
          Employees
        </Link>

        <Link className="nav-link" to="/add-employee">
          Add Employee
        </Link>
      </div>

      <button
        className="btn btn-outline-light ms-auto"
        onClick={handleLogout}
      >
        Logout
      </button>
    </nav>
  );
}

export default Navbar;