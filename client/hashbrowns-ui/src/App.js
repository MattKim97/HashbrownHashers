import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Home from "./components/Home"
import NavBar from "./components/NavBar";
import AllRecipesList from "./components/AllRecipesList";
import ViewRecipe from "./components/ViewRecipe";
import AddRecipeForm from "./components/AddRecipeForm";
import SearchRecipesList from "./components/SearchRecipeList";
import Login from "./components/Login";
import { useState } from "react";
import ChefsChoice from "./components/ChefsChoice";
import MyRecipesPage from "./components/MyRecipesPage";

function App() {
  const [loggedIn,setLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const [token,setToken] = useState('');




  return (
    <Router>
      <NavBar loggedIn={loggedIn}/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/recipe" element={<AllRecipesList/>}/>
        <Route path="/recipe/:id" element={<ViewRecipe user={user} />}/>
        <Route path="/recipe/new" element={<AddRecipeForm user={user} token={token}/> }/>
        <Route path="/recipe/search/:text" element={<SearchRecipesList/>}/>
        <Route path="/recipe/:recipeId/edit" element={<EditRecipeForm user={user} />}/>
        <Route path="/recipe/user/:id" element={<Home/>}/>
        <Route path="/login" element={<Login setLoggedIn={setLoggedIn} setUser={setUser}/>}/>
        <Route path="/chefschoice" element={<ChefsChoice/>}/>
        <Route path="/my-recipes" element={<MyRecipesPage />}/>
      </Routes>
    </Router>
  );
}

export default App;
