import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './MyRecipesPage.css';

const MyRecipesPage = ({user}) => {
  const [recipes, setRecipes] = useState([]);
  const [currentUser, setCurrentUser] = useState(localStorage.getItem('user_id'));
  const navigate = useNavigate();

  console.log(currentUser);


  useEffect(() => {
    if (currentUser) {
      fetch(`http://localhost:8080/recipe/user/${currentUser}`)
        .then(response => {
          if (response.status === 200) {
            return response.json();
          } else {
            return Promise.reject(`Unexpected status code: ${response.status}`);
          }
        })
        .then(data => setRecipes(data))
        .catch(console.log);
    }
  }, [currentUser]);



  return (
    <section className="container my-recipes">
      {currentUser ? (
        <>
          <h2>My Recipes</h2>
          {recipes.length > 0 ? (
            <ul>
              {recipes.sort((a, b) => new Date(b.uploadDate) - new Date(a.uploadDate)).map((recipe) => (
                <li key={recipe.recipeId} className="recipe-item" onClick={() => navigate(`/recipe/${recipe.recipeId}`)}>
                  <h3>{recipe.recipeName}</h3>
                  <img src={recipe.imageUrl} alt={recipe.recipeName} />
                  <p>{recipe.description}</p>
                </li>
              ))}
            </ul>
          ) : (
            <p>No recipes found.</p>
          )}
        </>
      ) : (
        <div className="login-prompt">
          <h2>Log in to view your recipes</h2>
          <p>You need to be logged in to see your recipes and manage them.</p>
          <button className="btn btn-outline-info" onClick={() => navigate('/login')}>Log In</button>
        </div>
      )}
    </section>
  );
};

export default MyRecipesPage;
