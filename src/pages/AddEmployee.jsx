import { addEmployee } from "../services/employeeService";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getDepartments } from "../services/departmentService";

function AddEmployee({ onEmployeeAdded }) {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [departmentId, setDepartmentId] = useState("1");
  const [salary, setSalary] = useState("");
  const [message, setMessage] = useState("");
  const [departments, setDepartments] = useState([]);

  const loadDepartments = async () => {
    try {
      const result = await getDepartments();
      setDepartments(result);

      if (result.length > 0) {
        setDepartmentId(String(result[0].id));
      }
    } catch (error) {
      console.log(error);
      setMessage("Failed to load departments");
    }
  };

  useEffect(() => {
    loadDepartments();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const employee = {
      name,
      departmentId: Number(departmentId),
      salary: Number(salary),
    };

    try {
      const result = await addEmployee(employee);

      setMessage(result.message);

      setName("");
      setDepartmentId(departments.length > 0 ? String(departments[0].id) : "1");
      setSalary("");

      onEmployeeAdded();

      navigate("/employees");
    } catch (error) {
      console.log("Full error:", error);
      console.log("Response data:", error.response?.data);
      console.log("Status:", error.response?.status);

      setMessage(
        error.response?.data?.message ||
          JSON.stringify(error.response?.data) ||
          "Failed to add employee"
      );
    }
  };

  return (
    <div className="row justify-content-center">
      <div className="col-md-6">
        <div className="card shadow">
          <div className="card-header bg-primary text-white text-center">
            <h3 className="mb-0">Add Employee</h3>
          </div>

          <div className="card-body">
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Name</label>
                <input
                  className="form-control"
                  type="text"
                  placeholder="Enter employee name"
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
                  placeholder="Enter salary"
                  value={salary}
                  onChange={(e) => setSalary(e.target.value)}
                />
              </div>

              <button className="btn btn-success w-100" type="submit">
                Add Employee
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

export default AddEmployee;