import React from "react";
import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "./ChefsChoice.css";
import gsap from "gsap";
import { useGSAP } from "@gsap/react";

export default function ChefsChoice() {
  const [recipes, setRecipes] = useState([]);
  const [recipe, setRecipe] = useState([]);
  const url = "http://localhost:8080/recipe";
  const navigate = useNavigate();

  useEffect(() => {
    fetch(url)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => setRecipes(data))
      .catch(console.log);
  }, []);

  gsap.registerPlugin(useGSAP);

  const container = useRef();

  useGSAP(
    () => {
      gsap.to(".box", { rotation: "+=360", duration: 3 });
    },
    { scope: container }
  );

  const recipeIds = recipes.map((recipe) => recipe.recipeId);
  const randomIndex = recipeIds[Math.floor(Math.random() * recipeIds.length)];

  const handleClick = () => {
    navigate(`/recipe/${randomIndex}`);
  };

  return (
    <section>
      <div className="chefHeader">
        <h2>The Chef's Choice</h2>
      </div>
      <div ref={container} className="chefButtonContainer">
        <button className="chefsButton box" onClick={handleClick}>
          See what the Chef is Cookin'!
        </button>
      </div>
      <div className="chefDescription">
        Feeling uninspired in the kitchen? Let our chef take the guesswork out
        of meal planning! Just hit the button above, and voilà—an exciting
        recipe will appear, tailored to spark your culinary creativity. Whether
        you're craving comfort or adventure, the chef's choice has got you
        covered. Embrace a new flavor adventure and discover what’s cooking
        today!
      </div>
    </section>
  );
}
