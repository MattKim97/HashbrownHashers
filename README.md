# HashbrownHashers

**A Recipe sharing application**

## Group 2 
Matthew Kim\
Nick Michuda\
Ga'briel Locke\
Nandan Gouri


### Problem Statement:


    Discovering, sharing, and organizing recipes can be a challenge even for professional chefs, let alone home cooks. Current platforms often lack new recipe discovery, effective organization tools, and efficient ingredient management. Users struggle to find new recipes tailored to their preferences, share recipes with ease, keep their favorite recipes organized, interact with other cooks for advice and feedback, and trust the quality of recipes. These challenges make the cooking experience less enjoyable and more cumbersome.	 

### Technical Solution


    Develop a web-based platform that allows users to share recipes, discover recipes, and organize recipes.

**Scenario 1**

    Sarah is an avid home cook and wants to share her favorite lasagna recipe with the community. She logs into the platform, fills out the recipe form, uploads a photo, and submits it. The lasagna recipe is successfully shared and is visible to other users.

**Scenario 2**

    John is looking for healthy dinner options. He uses the search functionality to filter recipes by dietary restrictions (e.g., gluten-free) and preparation time (under 30 minutes). The search results provide personalized recommendations based on John's past activity.

**Scenario 3**

    Lorenz is an admin and finds a recipe that is full of nonsensical ingredients and instructions. They want to delete the recipe from the page.

### Glossary

**Recipe**: A set of instructions for preparing a particular dish, including a list of the ingredients required, cooking time, and preparation time.

**User** : A person who uses the recipe sharing platform, does not need to be signed in and can view all recipes.

**Member** : A person who also uses the recipe sharing platform, by signing up for an account and signing in, can create a recipe, edit own recipe, and delete own recipe.

**Admin** : A person who manages the recipe sharing platform can delete any existing recipes and users if need be. All admins are members, but not all members are admins.


**Preparation Time** : The amount of time it takes to prepare the food before it is cooked.

**Cooking time** : The amount of time it takes to cook the food, separate from the preparation.

**Tag** : Labels attached to every recipe in order to effectively group and sort recipes.


### High Level Requirements

**For Anyone**

- View all Recipes : Access to a complete list of all recipes\
- View Recipes by Tags : Ability to filter recipes based on tags\
- View Recipes by Difficulty : Ability to filter recipes based on difficulty\
View Recipes by Preparation Time : Ability to filter recipes based on prep-time\
- View Recipes by Spiciness : Ability to filter recipes based on spiciness

**For Members**

- Create a Recipe : Members can add new recipes\
- Edit own Recipe : Members can modify recipes they have created\
- Delete own Recipe : Members can remove recipes that they have created

**Admin**

- Create a Recipe : Admins can create new recipes\
- Edit any Recipe : Admins can modify any recipe\
- Delete any Recipe : Admins can remove any recipe


### User Stories

## Browse Recipes

Decide on how to display recipes to anyone who uses the application

- View Recipes by Tags: Ability to filter recipes based on tags\
- View Recipes by Difficulty: Ability to filter recipes based on difficulty levels
- View Recipes by Preparation Time: Ability to filter recipes based on prep-time
- View Recipes by Spiciness: Ability to filter recipes based on spiciness levels

**User Role: Available to everyone, no need to be a member or admin**

## Create a Recipe

Create a recipe that other users can see

- Title
- Tags
- Difficulty
- Prep-Time
- Spiciness
- Image
- Short Desc
- Text

**Precondition**

- User must be logged in with a Member or Admin role

**Post-Condition**
- None

## Edit a Recipe

Edit an existing recipe

**Precondition**

- User must be logged in with a Member or Admin role
- User must own the Recipe or be an Admin

**Post-Condition**
- If the Member does not own Recipe, they can not edit the Recipe
- If User is Admin, able to edit any Recipe

## Delete a Recipe

Delete an existing recipe

**Precondition**

- User must be logged in with a Member or Admin role
- User must own the Recipe or be an Admin

**Post-Condition**
- If the Member does not own Recipe, they can not delete the Recipe
- If User is Admin, able to delete any Recipe
