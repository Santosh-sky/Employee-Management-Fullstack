import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  getEmployees,
  deleteEmployee,
} from "../services/employeeService";

function EmployeeList({ refresh }) {
  const navigate = useNavigate();

  const [employees, setEmployees] = useState([]);
  const [searchText, setSearchText] = useState("");

  const loadEmployees = async () => {
    try {
      const result = await getEmployees();
      setEmployees(result.data);
    } catch (error) {
      console.log(error);
      alert("Failed to load employees");
    }
  };

  useEffect(() => {
    loadEmployees();
  }, [refresh]);

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this employee?"
    );

    if (!confirmDelete) {
      return;
    }

    try {
      await deleteEmployee(id);
      loadEmployees();
    } catch (error) {
      console.log(error);
      alert("Failed to delete employee");
    }
  };

  const filteredEmployees = employees.filter((emp) => {
    const department =
      emp.departmentName || emp.department?.departmentName || "";

    return (
      emp.name.toLowerCase().includes(searchText.toLowerCase()) ||
      department.toLowerCase().includes(searchText.toLowerCase())
    );
  });

  const sortByName = () => {
    const sorted = [...employees].sort((a, b) =>
      a.name.localeCompare(b.name)
    );

    setEmployees(sorted);
  };

  const sortBySalary = () => {
    const sorted = [...employees].sort((a, b) => a.salary - b.salary);

    setEmployees(sorted);
  };

  const sortBySalaryDesc = () => {
    const sorted = [...employees].sort((a, b) => b.salary - a.salary);

    setEmployees(sorted);
  };

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Employee List</h2>

        <button
          className="btn btn-primary"
          type="button"
          onClick={() => navigate("/add-employee")}
        >
          Add Employee
        </button>
      </div>

      <div className="card shadow mb-4">
        <div className="card-body">
          <div className="mb-3">
            <input
              className="form-control"
              type="text"
              placeholder="Search employee by name or department..."
              value={searchText}
              onChange={(e) => setSearchText(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <button
              className="btn btn-secondary me-2"
              type="button"
              onClick={sortByName}
            >
              Sort by Name
            </button>

            <button
              className="btn btn-secondary me-2"
              type="button"
              onClick={sortBySalary}
            >
              Sort Salary Low to High
            </button>

            <button
              className="btn btn-secondary"
              type="button"
              onClick={sortBySalaryDesc}
            >
              Sort Salary High to Low
            </button>
          </div>

          <table className="table table-bordered table-hover text-center">
            <thead className="table-dark">
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Department</th>
                <th>Salary</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {filteredEmployees.map((emp) => (
                <tr key={emp.id}>
                  <td>{emp.id}</td>
                  <td>{emp.name}</td>
                  <td>
                    {emp.departmentName ||
                      emp.department?.departmentName ||
                      "N/A"}
                  </td>
                  <td>{emp.salary}</td>
                  <td>
                    <button
                      className="btn btn-warning btn-sm me-2"
                      type="button"
                      onClick={() => navigate(`/edit-employee/${emp.id}`)}
                    >
                      Edit
                    </button>

                    <button
                      className="btn btn-danger btn-sm"
                      type="button"
                      onClick={() => handleDelete(emp.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}

              {filteredEmployees.length === 0 && (
                <tr>
                  <td colSpan="5">No employees found</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default EmployeeList;