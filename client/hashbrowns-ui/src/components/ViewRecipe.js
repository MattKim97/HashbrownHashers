import React from 'react'
import { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

export default function ViewRecipe() {

    const [recipe, setRecipe] = useState([]);
    const url = "http://localhost:8080/recipe"
    const { id } = useParams();

    useEffect(()=>{
        fetch(`${url}/${id}`)
        .then(response => {
            if(response.status === 200){
                return response.json()
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => setRecipe(data))
        .catch(console.log)
    }
    ,[id])



  return (
    <>
        <section className="container viewRecipe">
            <h2>{recipe.recipeName}</h2>
            <img src={recipe.imageUrl} alt={recipe.recipeName} />
            <p>{recipe.description}</p>
            <p>Prep Time: {recipe.prepTime} minutes</p>
            <p>Difficulty: {recipe.difficulty}</p>
            <p>Spicyness: {recipe.spicyness}</p>
            <p>{recipe.text}</p>
            <ul>
                {recipe.tag && recipe.tag.map((tag, index) => (
                    <li key={index}>{tag}</li>
                ))}
            </ul>
        </section> 
    </>
  )
}
