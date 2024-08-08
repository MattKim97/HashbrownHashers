import React from 'react'
import { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import './ViewRecipe.css'
import Reviews from './Reviews';

export default function ViewRecipe({user}) {

    // STILL NEED TO IMPLEMENT CURRENT USER TO RESTRICT EDIT/DELETE

    // hardcoded user for now
    const [currentUser, setCurrentUser] = useState(user);
    const [recipe, setRecipe] = useState(null);
    const [tags, setTags] = useState([]);
    const tagurl = "http://localhost:8080/api/recipe_tags"
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

    useEffect(()=>{
        fetch(`${tagurl}/${id}`)
        .then(response => {
            if(response.status === 200){
                return response.json()
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            setTags(data)
            console.log(data);
        
        
        })
        .catch(console.log)
    }
    ,[id])


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

    if(!recipe){
        return <p>Loading...</p>
    }
    console.log("tags", tags)
    console.log("recipe tags", recipe.tags)
 



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

                {tags && tags.map((tag, index) => (

                    <li key={index}>{tag.tagName}</li>
                ))}
            </ul>
            {currentUser != null && recipe.userId == currentUser ?     
    <div>
        <button onClick={() => navigate(`/recipe/${id}/edit`)}>Edit</button>
        <button onClick={() => handleDelete()}>Delete</button>
    </div> 
    : null
}   <Reviews currentUser={currentUser}/>
        </section> 
    </>
  )
}
