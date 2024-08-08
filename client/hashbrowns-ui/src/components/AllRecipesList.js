import React from 'react'
import { useEffect, useState } from 'react'
import {Link} from "react-router-dom"
import "./AllRecipesList.css"
export default function AllRecipesList() {

    const [recipes, setRecipes] = useState([]);
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
        .then(data => setRecipes(data))
        .catch(console.log)
    },[])

  return (
    <>
      <section className="container recipesList">
        <h2>All Recipes</h2>
        <ul>
            {recipes.map((recipe, index) => (
                <li key={index}>
                    <Link to={`/recipe/${recipe.recipeId}`}>{recipe.recipeName}</Link>
                    <p>{recipe.description}</p>
                    <img src={recipe.imageUrl} alt={recipe.recipeName} />
                </li>
            ))}
        </ul>
      </section>
    </>
  )
}
