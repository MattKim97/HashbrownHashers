import React from 'react'
import { useEffect, useState } from 'react'
import {Link, useNavigate} from "react-router-dom"
import './AllRecipesList.css'

export default function AllRecipesList() {

    const [recipes, setRecipes] = useState([]);
    const url = "http://localhost:8080/recipe"
    const navigate = useNavigate();
    
    useEffect(()=>{
        
        fetch(url)
        .then(response => {
            if(response.status === 200){
                return response.json()
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => setRecipes(data))
        .catch(console.log)
    },[])

  return (
    <>
      <section className="container recipesList">
        <h1 >All Recipes</h1>
        <ul>
            {recipes.map((recipe, index) => (
                <li key={index} className="recipeListElement" onClick={() =>  navigate(`/recipe/${recipe.recipeId}`)}>
                    <h2 className='allRecipesHeader'>{recipe.recipeName}</h2>
                    <p>{recipe.description}</p>
                    <img className="listImages" src={recipe.imageUrl} alt={recipe.recipeName} />
                </li>
            ))}
        </ul>
      </section>
    </>
  )
}
