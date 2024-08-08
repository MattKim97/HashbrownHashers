import { useEffect, useState } from 'react';
import './Home.css'
import { Link } from 'react-router-dom';


function Home(){


    const [recipes, setRecipes] = useState([]);
    const [popularRecipes, setPopularRecipes] = useState([]);
    const url = "http://localhost:8080/recipe"
    
    useEffect(()=>{
        
        fetch(url)
        .then(response => {
            if(response.status === 200){
                return response.json()
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            setRecipes(data)
            
            console.log(data);
        
        })
        .catch(console.log)
    },[])

    const sorted = [...recipes].sort((a, b) => {
        return Date.parse(b.timePosted) - Date.parse(a.timePosted);
      });

    

    return(
    
        <section className="landingPage">
        <header>
            <h1 className="text-center">Welcome to Has#Browns!</h1>
        </header>
        <div className='centeredHeader'>
            <h3 className="homeHeaderSm">Recipes for You!</h3>
        </div>
        <div className="recipes">
                {recipes.slice(0,3).map((recipe, index) => (
                    <div key={index} className='recipe-item'>
                        <Link to={`/recipe/${recipe.recipeId}`}>
                        <img src={recipe.imageUrl} alt={recipe.recipeName} />
                        <p className='recipe-title'>{recipe.recipeName}</p>
                        </Link>
                    </div>
                ))}
        </div>
        <br/>
        <div className='centeredHeader'>
            <h3 className="homeHeaderSm">Newest Recipes</h3>
        </div>
        <div className="recipes">
                {sorted.slice(0,3).map((recipe, index) => (
                    <div key={index} className='recipe-item'>
                        <Link to={`/recipe/${recipe.recipeId}`}>
                        <img src={recipe.imageUrl} alt={recipe.recipeName} />
                        <p className='recipe-title'>{recipe.recipeName}</p>
                        </Link>
                    </div>
                ))}
        </div>


        </section>


       
        
    )
}


export default Home;