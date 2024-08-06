import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Home from "./Home"
import NavBar from "./NavBar";

function App() {
  return (
    <Router>
      <NavBar/>
      <Routes>
        <Route path="/" element={<Home/>}/>
      </Routes>
    </Router>
  );
}

export default App;
