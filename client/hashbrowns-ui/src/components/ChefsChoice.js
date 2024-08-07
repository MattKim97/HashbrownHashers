import React from 'react'
import { useEffect, useState } from 'react'
import {Link, useNavigate} from "react-router-dom"
import './ChefsChoice.css'



export default function ChefsChoice(){

    const [recipes, setRecipes] = useState([]);
    const [recipe, setRecipe] = useState([]);
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
    
    
    const randomNum = Math.floor(Math.random()*recipes.length) + 1;
    const handleClick = (event) =>{
        navigate(`/recipe/${randomNum}`)
    }
  


    return(
        <div className='buttonContainer'>
        <button className='chefsButton shaky-btn' onClick={handleClick}>See what the Chef is cookin</button>
        </div>
    )
}