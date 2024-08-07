import React from 'react'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';

export default function EditRecipeForm() {

    const [currentUser, setCurrentUser] = useState(null);
    const [errors, setErrors] = useState([]);
    const [recipe, setRecipe] = useState(null);
    const navigate = useNavigate();


    const handleChange = (e) => {
        const newRecipe = {...recipe};

        newRecipe[e.target.name] = e.target.value;

        setRecipe(newRecipe);

    }

  return (
    <div>
             {currentUser ? <section className="container">
            <h2>Add a Recipe</h2>
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
                <fieldset className="form-group">
                    <label>Recipe Name</label>
                    <input type="text" className="form-control" id="recipeName"  name="recipeName" placeholder="Recipe Name" value={recipe.recipeName} onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group">
                    <label>Difficulty</label>
                    <input type="number" className="form-control" id="difficulty" name="difficulty" placeholder="Difficulty" min="1" max="5" value={recipe.difficulty} onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group">
                    <label>Spiciness</label>
                    <input type="number" className="form-control" id="spicyness" name="spicyness" placeholder="Spiciness" min="1" max="5" value={recipe.spicyness} onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group">
                    <label>Prep Time</label>
                    <input type="number" className="form-control" id="prepTime" name="prepTime" placeholder="Prep Time" value={recipe.prepTime} min="1" onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group">
                    <label>Description</label>
                    <textarea className="form-control" id="description" name="description" placeholder="Description" value={recipe.description} onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group">
                    <label>Text</label>
                    <textarea className="form-control" id="text" name="text" placeholder="Text" value={recipe.text} onChange={handleChange}/>
                </fieldset>
                <button type="submit" className="btn btn-outline-primary">Submit</button>
                <button className="btn btn-outline-secondary" onClick={() => navigate("/")}>cancel</button>
            </form>
        </section> : 
        <section className="container loginForm">
            <h2>Log in to add a recipe</h2>
            <div className="mt-3">
                    <p>You need to be logged in to add a recipe.</p>
                    <button className="btn btn-outline-info loginBtn" onClick={() => navigate('/login')}>Log In</button>
            </div>
        </section>
        }
    </div>
  )
}
