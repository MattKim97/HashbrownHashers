import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './MyRecipesPage.css';

const MyRecipesPage = () => {
  const [recipes, setRecipes] = useState([]);
  const [currentUser, setCurrentUser] = useState(null);
  const navigate = useNavigate();
  
  useEffect(() => {
    fetch('http://localhost:8080/current-user')
      .then(response => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then(data => setCurrentUser(data))
      .catch(console.log);
  }, []);

  useEffect(() => {
    if (currentUser) {
      fetch(`http://localhost:8080/recipe/user/${currentUser.userId}`)
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
                <li key={recipe.recipeId} className="recipe-item">
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
