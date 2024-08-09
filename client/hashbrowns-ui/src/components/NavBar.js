import { Link, useLocation, useNavigate } from "react-router-dom";
import './NavBar.css';
import { useState } from "react";

function NavBar({loggedIn}){

    const navigate = useNavigate();
    const [search, setSearch] = useState("");
    const location = useLocation();

    const handleSubmit = (event) =>{
        event.preventDefault();
        //console.log(location.pathname)
        if(search.length > 0){
            navigate(`/recipe/search/${search}`)
            setSearch(''); 
            
        }
    }
    const handleChange = (event) =>{
        setSearch(event.target.value);
    }
   



    return(
        
        <nav className="navbar navbar-light bg-light">
            <Link to={'/'}>Home</Link>
            <form className="form-inline" onSubmit={handleSubmit}>
                <input value={search} onChange={handleChange} className="form-control mr-sm-2  searchForm" type="search" placeholder="Search Recipes" aria-label="Search"/>
                <button className="searchBtn" >Search</button>
            </form>
            <Link to={'/chefschoice'}>Chef's Choice</Link>
            <Link to={'/recipe'}>View Recipes</Link>
            <Link to={'/recipe/new'}>Create New</Link>
            <Link to={'/my-recipes'}>My Recipes</Link>
            <Link to={'/login'}>{loggedIn ? `Log Out` : `Log In`}</Link>
            {loggedIn ? null : <Link to={'/signup'}>Sign Up</Link>}
        </nav>
        
    )
}


export default NavBar;
