import React from 'react'
import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';

export default function EditRecipeForm({user}) {

    // STILL NEED TO IMPLEMENT CURRENT USER TO RESTRICT EDIT/DELETE

    // hardcoded user for now
    const [currentUser, setCurrentUser] = useState(user);
    const [errors, setErrors] = useState([]);
    const [recipe, setRecipe] = useState(null);
    const navigate = useNavigate();
    const { recipeId } = useParams();


    useEffect(()=>{
        if(recipeId){
            fetch(`http://localhost:8080/recipe/${recipeId}`)
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
    },[recipeId])


    const handleChange = (e) => {
        const newRecipe = {...recipe};

        newRecipe[e.target.name] = e.target.value;

        setRecipe(newRecipe);

    }

    const handleSubmit = async (e) => {

        if(currentUser != recipe.userId){
            window.alert("You can only edit your own recipes")
            navigate(`/recipe/${recipeId}`)
            return
        }

        e.preventDefault();

        const errors = [];

        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(recipe)
        };
        fetch(`http://localhost:8080/recipe/${recipeId}`, init)
        .then(response => {
            if(response.status === 204){
                return null
            } else if(response.status === 400){
                return response.json()
            } else {
                return Promise.reject(`Unexpected Status Code: ${response.status}`);
            }
        })
        .then(data => {
            if(data){
                setErrors(data);
            } else {
                navigate(`/recipe/${recipeId}`)
            }
        })
        .catch(console.log)
    }

    if (!recipe) {
        // Optionally render a loading state or placeholder
        return <div>Loading...</div>;
    }

  return (
    <div>
             {currentUser ? <section className="container">
            <h2>Edit a Recipe</h2>
            {errors.length > 0 && (
                <div className="alert alert-danger">
                    <p>The Following Errors were found: </p>
                    <ul>
                        {errors.map(error =>(
                            <li key={error}>{error}</li>
                        ))}
                    </ul>
                </div>
            )}
            <form onSubmit={handleSubmit}>
                <fieldset className="form-group inputForm">
                    <label>Recipe Name</label>
                    <input type="text" className="form-control inputForm" id="recipeName"  name="recipeName" placeholder="Recipe Name" value={recipe.recipeName} onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group inputForm">
                    <label>Difficulty</label>
                    <input type="number" className="form-control" id="difficulty" name="difficulty" placeholder="Difficulty" min="1" max="5" value={recipe.difficulty} onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group inputForm">
                    <label>Spiciness</label>
                    <input type="number" className="form-control" id="spicyness" name="spicyness" placeholder="Spiciness" min="1" max="5" value={recipe.spicyness} onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group inputForm">
                    <label>Prep Time</label>
                    <input type="number" className="form-control" id="prepTime" name="prepTime" placeholder="Prep Time" value={recipe.prepTime} min="1" onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group">
                    <label>Description</label>
                    <textarea className="form-control descriptionRecipe" id="description" name="description" placeholder="Description" value={recipe.description} onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group">
                    <label>Text</label>
                    <textarea className="form-control textRecipe" id="text" name="text" placeholder="Text" value={recipe.text} onChange={handleChange}/>
                </fieldset>
                <button type="submit" className="btn btn-outline-primary">Submit</button>
                <button className="btn btn-outline-secondary" onClick={() => navigate(`/recipe/${recipeId}`)}>Cancel</button>
            </form>
        </section> : 
        <section className="container loginForm">
            <h2>Log in to edit your recipe</h2>
            <div className="mt-3">
                    <p>You need to be logged in to edit your recipe.</p>
                    <button className="btn btn-outline-info loginBtn" onClick={() => navigate('/login')}>Log In</button>
            </div>
        </section>
        }
    </div>
  )
}
