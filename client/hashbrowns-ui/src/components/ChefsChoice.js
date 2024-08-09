import React from "react";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./ChefsChoice.css";
import { useRef } from "react";
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
      gsap.to(".box", { rotation: "+=360", duration: 4 });
      const buttons = gsap.utils.toArray(".button");
buttons.forEach((item) => {
  let span = item.querySelector("span");
  let tl = gsap.timeline({ paused: true });

  tl.to(span, { duration: 0.2, yPercent: -150, ease: "power2.in" });
  tl.set(span, { yPercent: 150 });
  tl.to(span, { duration: 0.2, yPercent: 0 });

  item.addEventListener("mouseenter", () => tl.play(0));
});

    },
    { scope: container }
  );

  const recipeIds = recipes.map((recipe) => recipe.recipeId);

  const randomIndex = recipeIds[Math.floor(Math.random() * recipeIds.length)];

  const handleClick = (event) => {
    navigate(`/recipe/${randomIndex}`);
  };

  return (
    <section>
      <div className="chefHeader">
        <h2>
          Feeling stuck? Let the chef decide on a recipe for you!
        </h2>
      </div>
      <div ref={container} className="chefButtonContainer">
        <button className="chefsButton box" onClick={handleClick}>
          <span>See what the chef is cookin'</span>
        </button>
      </div>
    </section>
  );
}
