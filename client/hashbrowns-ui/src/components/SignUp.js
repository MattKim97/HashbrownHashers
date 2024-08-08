import { useState } from "react";
import './SignUp.css'
import { useNavigate } from "react-router-dom";




const CREDENTIALS_DEFAULT = {
  username: "",
  password: "",
  email: "",
  firstName: "",
  lastName: "",
};

const SignUp = (props) => {
  const [credentials, setCredentials] = useState(CREDENTIALS_DEFAULT);
  const [userError, setUserError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [emailError, setEmailError] = useState("");
  const [firstError, setFirstError] = useState("");
  const [lastError, setLastError] = useState("");

  const navigate = useNavigate();


  const url = "http://localhost:8080/api/user/register"

  const handleSubmit = (event) => {
    setUserError("");
    setPasswordError("");
    setFirstError("");
    setLastError("");
    setEmailError("");

    if ("" === credentials.firstName) {
      setFirstError("Please enter a first name");
      return;
    }
    if ("" === credentials.lastName) {
      setLastError("Please enter a last name");
      return;
    }
    if ("" === credentials.email) {
      setEmailError("Please enter a valid email");
      return;
    }

    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(credentials.email)) {
      setEmailError("Please enter a valid email");
      return;
    }
    if ("" === credentials.username) {
      setUserError("Please enter your email");
      return;
    }
    if ("" === credentials.password) {
      setPasswordError("Please enter a password");
      return;
    }
    if (credentials.password.length < 8) {
      setPasswordError("The password must be 8 characters or longer");
      return;
    }

    const init = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    };
    fetch(url,init)
    .then((response)=> response.json())
    .then((data)=>{
        if(data){
            navigate('/')
        }else{
            
            return;
        }
    })



  };

  const handleChange = (event) => {
    const newCredentials = { ...credentials };

    newCredentials[event.target.name] = event.target.value;

    setCredentials(newCredentials);
  };

  return (
    <div className="mainSignContainer">
      <div className="titleSignContainer">
      <h1>Welcome to Has#Browns</h1>
      <h2>Sign Up</h2>
        <br />
        <div className="inputContainer">
          <input
            value={credentials.firstName}
            name="firstName"
            placeholder="Enter First Name"
            onChange={handleChange}
            className="inputHolder"
          />
          <label className="error">{firstError}</label>
        </div>
        <div className="inputContainer">
          <input
            value={credentials.lastName}
            name="lastName"
            placeholder="Enter Last Name"
            onChange={handleChange}
            className="inputHolder"
          />
          <label className="error">{lastError}</label>
        </div>
        <div className="inputContainer">
          <input
            value={credentials.email}
            name="email"
            placeholder="Enter Email"
            onChange={handleChange}
            className="inputHolder"
          />
          <label className="error">{emailError}</label>
        </div>
        <div className="inputContainer">
          <input
            value={credentials.username}
            placeholder="Enter Username"
            name="username"
            onChange={handleChange}
            className="inputHolder"
          />
          <label className="error">{userError}</label>
        </div>
        <div className="inputContainer">
          <input
            value={credentials.password}
            type="password"
            name="password"
            placeholder="Enter Password"
            onChange={handleChange}
            className="inputHolder"
          />
          <label className="error">{passwordError}</label>
        </div>

        <br />
        <div className="buttonContainer">
          <button onClick={handleSubmit} className="inputButton">
            Submit
          </button>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
