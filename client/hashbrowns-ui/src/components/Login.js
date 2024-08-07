import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";

const Login = (props) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [userError, setUserError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const navigate = useNavigate();

  const handleSubmit = (event) => {
    setUserError("");
    setPasswordError("");

    // Check if the user has entered both fields correctly
    if ("" === username) {
      setUserError("Please enter your email");
      return;
    }
    if ("" === password) {
      setPasswordError("Please enter a password");
      return;
    }
    if (password.length < 8) {
      setPasswordError("The password must be 8 characters or longer");
      return;
    }
  };

  return (
    <div className="mainContainer">
      <div className="titleContainer">
        <h1>Login</h1>
        <br />
        <div className="inputContainer">
          <input
            value={username}
            placeholder="Enter Username"
            onChange={(event) => setUsername(event.target.value)}
            className="inputHolder"
          />
          <label className="error">{userError}</label>
        </div>
        <div className="inputContainer">
          <input
            value={password}
            type="password"
            name="password"
            placeholder="Enter Password"
            onChange={(event) => setPassword(event.target.value)}
            className="inputHolder"
          />
          <label className="error">{passwordError}</label>
        </div>
        <br />
        <div className="buttonContainer">
          <button onClick={handleSubmit} className="inputButton">
            Log In
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
