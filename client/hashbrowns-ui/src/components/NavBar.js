import { Link, useNavigate } from "react-router-dom";
import './NavBar.css';
import { useState } from "react";

function NavBar(){

    const navigate = useNavigate();
    const [search, setSearch] = useState("");

    const handleSubmit = (event) =>{
        event.preventDefault();        
        navigate(`/recipes/${search}`)
        setSearch(''); 
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
            <Link to={'/'}>Chef's Choice</Link>
            <Link to={'/'}>View Recipes</Link>
            <Link to={'/'}>Create New</Link>
            <Link to={'/'}>My Recipes</Link>
            <Link to={'/'}>Log In/Log Out</Link>

            

        </nav>
        
    )
}


export default NavBar;