import React from 'react'
import { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import './ViewRecipe.css'

export default function ViewRecipe() {

    // STILL NEED TO IMPLEMENT CURRENT USER TO RESTRICT EDIT/DELETE

    // hardcoded user for now
    const [currentUser, setCurrentUser] = useState({
        username: "admin",
        userId : 2
    });
    const [recipe, setRecipe] = useState([]);
    const [tags, setTags] = useState([]);
    const tagurl = "http://localhost:8080/api/tags"
    const recipeurl = "http://localhost:8080/recipe"
    const { id } = useParams();
    const url = "http://localhost:8080/recipe"
    const navigate = useNavigate();

    useEffect(()=>{
        fetch(`${recipeurl}/${id}`)
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

    console.log("userid" ,currentUser.userId)
    console.log("recipeid", recipe.userId)

    const handleDelete = () => {
        if(window.confirm("Are you sure you want to delete this recipe?")){
            const init = {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            };
            fetch(`${url}/${id}`, init)
            .then(response => {
                if(response.status === 204){
                    navigate('/')
                } else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`);
                }
            })
            .catch(console.log)
        }
    }



  return (
    <>
        <section className="container viewRecipe">
            <h1>{recipe.recipeName}</h1>
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
            {recipe.userId === currentUser.userId &&     
            <div>
                <button onClick={() => navigate(`/recipe/${id}/edit`)}>Edit</button>
                <button onClick={() => handleDelete()}>Delete</button>
            </div> 
            }

        </section> 
    </>
  )
}
