import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Home from "./components/Home"
import NavBar from "./components/NavBar";
import AllRecipesList from "./components/AllRecipesList";

function App() {
  return (
    <Router>
      <NavBar/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/recipes" element={<AllRecipesList/>}/>
      </Routes>
    </Router>
  );
}

export default App;
