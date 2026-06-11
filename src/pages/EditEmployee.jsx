import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  getEmployees,
  updateEmployee,
} from "../services/employeeService";
import { getDepartments } from "../services/departmentService";

function EditEmployee() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [departmentId, setDepartmentId] = useState("1");
  const [salary, setSalary] = useState("");
  const [departments, setDepartments] = useState([]);
  const [message, setMessage] = useState("");

  const loadDepartments = async () => {
    try {
      const result = await getDepartments();
      setDepartments(result);
    } catch (error) {
      console.log(error);
      setMessage("Failed to load departments");
    }
  };

  const loadEmployee = async () => {
    try {
      const result = await getEmployees();

      const employee = result.data.find(
        (emp) => String(emp.id) === String(id)
      );

      if (!employee) {
        setMessage("Employee not found");
        return;
      }

      setName(employee.name);
      setSalary(employee.salary);

      const selectedDepartment = departments.find(
        (dept) =>
          dept.departmentName === employee.departmentName ||
          dept.departmentName === employee.department?.departmentName
      );

      if (selectedDepartment) {
        setDepartmentId(String(selectedDepartment.id));
      }
    } catch (error) {
      console.log(error);
      setMessage("Failed to load employee");
    }
  };

  useEffect(() => {
    loadDepartments();
  }, []);

  useEffect(() => {
    if (departments.length > 0) {
      loadEmployee();
    }
  }, [departments]);

  const handleUpdate = async (e) => {
    e.preventDefault();

    const updatedEmployee = {
      name,
      departmentId: Number(departmentId),
      salary: Number(salary),
    };

    try {
      await updateEmployee(id, updatedEmployee);

      setMessage("Employee updated successfully");

      navigate("/employees");
    } catch (error) {
      console.log(error);
      setMessage("Failed to update employee");
    }
  };

  return (
    <div className="row justify-content-center">
      <div className="col-md-6">
        <div className="card shadow">
          <div className="card-header bg-warning text-center">
            <h3 className="mb-0">Edit Employee</h3>
          </div>

          <div className="card-body">
            <form onSubmit={handleUpdate}>
              <div className="mb-3">
                <label className="form-label">Name</label>
                <input
                  className="form-control"
                  type="text"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Department</label>
                <select
                  className="form-select"
                  value={departmentId}
                  onChange={(e) => setDepartmentId(e.target.value)}
                >
                  {departments.map((dept) => (
                    <option key={dept.id} value={dept.id}>
                      {dept.departmentName}
                    </option>
                  ))}
                </select>
              </div>

              <div className="mb-3">
                <label className="form-label">Salary</label>
                <input
                  className="form-control"
                  type="number"
                  value={salary}
                  onChange={(e) => setSalary(e.target.value)}
                />
              </div>

              <button className="btn btn-success w-100" type="submit">
                Update Employee
              </button>

              <button
                className="btn btn-secondary w-100 mt-2"
                type="button"
                onClick={() => navigate("/employees")}
              >
                Back
              </button>
            </form>

            {message && (
              <p className="text-center mt-3 fw-bold">{message}</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default EditEmployee;