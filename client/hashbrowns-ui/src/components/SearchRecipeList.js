import React from 'react'
import { useEffect, useState } from 'react'
import {Link, useParams} from "react-router-dom"

export default function SearchRecipesList() {

    const [recipes, setRecipes] = useState([]);
    const url = "http://localhost:8080/recipe/search"
    const {text} = useParams();
    
    useEffect(()=>{
        
        fetch(`${url}/${text}`)
        .then(response => {
            if(response.status === 200){
                return response.json()
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => setRecipes(data))
        .catch(console.log)
    },[text])

  return (
    <>
      <section className="container recipesList">
        <h2>Recipe Results</h2>
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
