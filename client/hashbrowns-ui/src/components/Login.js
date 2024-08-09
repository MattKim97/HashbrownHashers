import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";

const CREDENTIALS_DEFAULT = {
  username: '',
  password: '',
};

const Login = (props) => {
  const [credentials, setCredentials] = useState(CREDENTIALS_DEFAULT);
  const [userError, setUserError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const url = "http://localhost:8080/api/user/authenticate"

  const navigate = useNavigate();


    useEffect(()=>{
        
        if(props.loggedIn === true){
            props.setLoggedIn(false);
            props.setUser(null);
            localStorage.clear();
        }



    },[]);

  const handleSubmit = (event) => {
    setUserError("");
    setPasswordError("");

    console.log(credentials);

    // Check if the user has entered both fields correctly
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

    //Check username password. If database contains user/pass, login.
    //else passworderror.set(invalid username/password. please try again.)

    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    };
    fetch(url, init).then((response) => {
      if (response.status === 403) {
        console.log(response);
        setPasswordError("Incorrect username/password.");
        return;
      } else {
        response.json().
        then((data) => {
          console.log(data);
          props.setToken(data.jwt_token);
          localStorage.setItem('token',data.jwt_token);
          localStorage.setItem('user_id',data.user_id);
          props.setLoggedIn(true);
          props.setUser(data.user_id);
          navigate("/");
        });
      }
    });
  };

  const handleChange = (event) => {
    const newCredentials = { ...credentials };

    newCredentials[event.target.name] = event.target.value;

    setCredentials(newCredentials);
  };

  return (
    <div className="mainContainer">
      <div className="titleContainer">
        <h1>Login</h1>
        <br />
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
            Log In
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
