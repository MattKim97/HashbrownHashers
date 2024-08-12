import React from 'react'
import { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import './ViewRecipe.css'
import Reviews from './Reviews';

export default function ViewRecipe({user}) {

    const [currentUser, setCurrentUser] = useState(localStorage.getItem('user_id'));
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

    const getPeppers = (spicyness) => {
        const pepper = Math.max(1, Math.min(spicyness, 5));
        return '🌶️'.repeat(pepper);
    };

    
    const getDifficulty = (difficulty) => {
        const muscle = Math.max(1, Math.min(difficulty, 5));
        return '💪'.repeat(muscle);
    };


    const convertPreptime = (prep) => {
        const min = prep % 60;
        const hour = Math.trunc((prep / 60))
        let mintext = '';
        let hourtext = '';
        let connect = '';
        if (hour > 1) {
            hourtext = `${hour} hours `;
        } else if (hour > 0) {
            hourtext = `${hour} hour `;
        } 

        if (min > 1) {
            mintext = `${min} minutes `;
        } else if (hour > 0) {
            mintext = `${min} minute `;
        }
        if(mintext != '' && hourtext != '') {
            connect = 'and '
        } 
        return 'Prep Time: ' + hourtext + connect + mintext;
    }

  return (
    <>
        <section className="container viewRecipe">
            <h1>{recipe.recipeName}</h1>
            <div className='viewRecipeHeader'>
            <ul>
                Tags:
                {tags && tags.map((tag, index) => (
                    <li key={index}>{tag.tagName}</li>
                ))}
            </ul>
            </div>
            <img className= "viewRecipeImage" src={recipe.imageUrl ? recipe.imageUrl : "https://news.mit.edu/sites/default/files/images/202312/MIT_Food-Diabetes-01.jpg"} alt={recipe.recipeName} />
            <p className='viewRecipeDesc'>{recipe.description}</p>
            <p className='viewRecipePrepTime'>{convertPreptime(recipe.prepTime)}</p>
            <p className='viewRecipeDifficulty'>Difficulty: {getDifficulty(recipe.difficulty)}</p>
            <p className='viewRecipeSpiciness'>Spiciness: {getPeppers(recipe.spicyness)}</p>
            <p className='viewRecipeText'>{recipe.text}</p>
            {currentUser != null && recipe.userId == currentUser ?     
    <div className='recipeButtonContainer'>
        <button className="btn btn-warning recipeButton" onClick={() => navigate(`/recipe/${id}/edit`)}>Edit</button>
        <button className="btn btn-danger recipeButton" onClick={() => handleDelete()}>Delete</button>
    </div> 
    : null
}   <Reviews currentUser={user}/>
        </section> 
    </>
  )
}
