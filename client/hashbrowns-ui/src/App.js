import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Home from "./components/Home"
import NavBar from "./components/NavBar";
import AllRecipesList from "./components/AllRecipesList";
import ViewRecipe from "./components/ViewRecipe";
import AddRecipeForm from "./components/AddRecipeForm";
import SearchRecipesList from "./components/SearchRecipeList";
import Login from "./components/Login";
import { useEffect, useState } from "react";
import ChefsChoice from "./components/ChefsChoice";
import EditRecipeForm from "./components/EditRecipeForm";
import SignUp from "./components/SignUp";
import MyRecipesPage from "./components/MyRecipesPage";

function App() {
  const [loggedIn,setLoggedIn] = useState(false);
  const [user, setUser] = useState('');
  const [token,setToken] = useState('');

  useEffect(() => {
    if(localStorage.getItem('token')) {
      setLoggedIn(true);
    }
  }, []);
 



  return (

    <Router>
      <NavBar loggedIn={loggedIn}/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/recipe" element={<AllRecipesList/>}/>
        <Route path="/recipe/:id" element={<ViewRecipe/>}/>
        <Route path="/recipe/new" element={<AddRecipeForm/>}/>
        <Route path="/recipe/search/:text" element={<SearchRecipesList/>}/>
        <Route path="/recipe/:recipeId/edit" element={<EditRecipeForm/>}/>
        <Route path="/recipe/user/:id" element={<Home/>}/>
        <Route path="/login" element={<Login setLoggedIn={setLoggedIn} setUser={setUser} setToken={setToken}/>}/>
        <Route path="/chefschoice" element={<ChefsChoice/>}/>
        <Route path="/signup" element={<SignUp/>}/>
        <Route path="/my-recipes" element={<MyRecipesPage user={user}/>}/>
      </Routes>
    </Router>
  );
}

export default App;
