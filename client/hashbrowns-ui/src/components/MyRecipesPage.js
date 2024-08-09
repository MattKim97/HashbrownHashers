import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './MyRecipesPage.css';

const MyRecipesPage = ({user}) => {
  const [recipes, setRecipes] = useState([]);
  const [currentUser, setCurrentUser] = useState(localStorage.getItem('user_id'));
  const navigate = useNavigate();


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
    <section className="container recipesList">
      {currentUser ? (
        <>
          <h1>My Recipes</h1>
          {recipes.length > 0 ? (
            <ul>
              {recipes.sort((a, b) => new Date(b.uploadDate) - new Date(a.uploadDate)).map((recipe) => (
                <li key={recipe.recipeId} className="recipeListElement" onClick={() => navigate(`/recipe/${recipe.recipeId}`)}>
                    <h2 className='allRecipesHeader'>{recipe.recipeName}</h2>
                    <p>{recipe.description}</p>
                    <img className="listImages" src={recipe.imageUrl ? recipe.imageUrl : "https://news.mit.edu/sites/default/files/images/202312/MIT_Food-Diabetes-01.jpg"} alt={recipe.recipeName} />
                </li>
              ))}
            </ul>
          ) : (
            <p>No recipes found.</p>
          )}
        </>
      ) : (
        <div className="container loginForm">
          <h2>Log in to view your recipes</h2>
          <div className="mt-3">
          <p>You need to be logged in to see your recipes and manage them.</p>
          <button className="btn btn-outline-info loginBtn" onClick={() => navigate('/login')}>Log In</button>
          </div>
        </div>
      )}
    </section>
  );
};

export default MyRecipesPage;
