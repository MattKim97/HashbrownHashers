import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Home from "./components/Home"
import NavBar from "./components/NavBar";
import AllRecipesList from "./components/AllRecipesList";
import ViewRecipe from "./components/ViewRecipe";
import AddRecipeForm from "./components/AddRecipeForm";
import SearchRecipesList from "./components/SearchRecipeList";
import Login from "./components/Login";

function App() {
  return (
    <Router>
      <NavBar/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/recipe" element={<AllRecipesList/>}/>
        <Route path="/recipe/:id" element={<ViewRecipe/>}/>
        <Route path="/recipe/new" element={<AddRecipeForm/>}/>
        <Route path="/recipe/search/:text" element={<SearchRecipesList/>}/>
        <Route path="/login" element={<Login/>}/>
      </Routes>
    </Router>
  );
}

export default App;
