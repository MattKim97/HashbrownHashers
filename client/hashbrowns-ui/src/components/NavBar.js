import { Link } from "react-router-dom";
import './NavBar.css';

function NavBar(){
    return(
        
        <nav className="navbar navbar-light bg-light">
            <Link to={'/'}>Home</Link>
            <form className="form-inline">
                <input className="form-control mr-sm-2  searchForm" type="search" placeholder="Search Recipes" aria-label="Search"/>
                <button className="searchBtn" type="submit">Search</button>
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