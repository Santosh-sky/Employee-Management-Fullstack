import { useState } from "react";
import { login } from "../services/authService";

function Login({ onLoginSuccess }) {
  const [email, setEmail] = useState("santosh@gmail.com");
  const [password, setPassword] = useState("12345");
  const [message, setMessage] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const result = await login(email, password);

      const token = result.data.token;

      localStorage.setItem("token", token);

      setMessage("Login successful");

      onLoginSuccess();
    } catch (error) {
      setMessage("Invalid email or password");
    }
  };

  return (
    <div className="row justify-content-center mt-5">
      <div className="col-md-5">
        <div className="card shadow">
          <div className="card-header bg-dark text-white text-center">
            <h3>Employee Management Login</h3>
          </div>

          <div className="card-body">
            <form onSubmit={handleLogin}>
              <div className="mb-3">
                <label className="form-label">Email</label>
                <input
                  className="form-control"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Password</label>
                <input
                  className="form-control"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>

              <button className="btn btn-primary w-100" type="submit">
                Login
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

export default Login;