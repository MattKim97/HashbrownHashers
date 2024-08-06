import { Link } from "react-router-dom";


function NavBar(){
    return(
        <>
        <nav>
            <Link to={'/'}>Home</Link>
            <Link to={'/'}>Chef's Choice</Link>
            <Link to={'/'}>View Recipes</Link>
            <Link to={'/'}>Create New</Link>
            <Link to={'/'}>My Recipes</Link>
            <Link to={'/'}>Log In/Log Out</Link>
        </nav>
        </>
    )
}


export default NavBar;