import AWS from 'aws-sdk';
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import './AddRecipeForm.css';


const RECIPE_DEFAULT = {
    recipeName: '',
    difficulty: 1,
    spicyness: 1,
    prepTime: 1,
    imageUrl: '',
    description: '',
    text: '',
    tags: []
}

const S3_BUCKET_IMAGE = process.env.REACT_APP_S3_BUCKET_IMAGE;
const REGION = process.env.REACT_APP_REGION;
const S3_KEY = process.env.REACT_APP_S3_KEY;
const S3_SECRET = process.env.REACT_APP_S3_SECRET;

const getFileUrl = (fileName) => {
  return `https://${S3_BUCKET_IMAGE}.s3.${REGION}.amazonaws.com/${fileName}`;
};

function AddRecipeForm({user, token }){
  
       // hardcoded user for now
    const [currentUser, setCurrentUser] = useState(localStorage.getItem('user_id'));
    const tagurl = "http://localhost:8080/api/recipe_tags"
    const [file, setFile] = useState(null);
    const [fileUrl, setFileUrl] = useState('');
    const [errors, setErrors] = useState([]);
    const [recipe, setRecipe] = useState(RECIPE_DEFAULT);
    const [recipes, setRecipes] = useState([]);
    const [tags, setTags] = useState([]);
    const url = "http://localhost:8080/recipe"
    const navigate = useNavigate();

    useEffect(()=>{
        fetch("http://localhost:8080/api/tags")
        .then(response => {
            if(response.status === 200){
                return response.json()
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => setTags(data))
        .catch(console.log)
    }
    ,[])



    const handleFileChange = (e) => {
      const file = e.target.files[0];
      setFile(file);
    };

    const uploadFile = async () => {
        const S3_BUCKET = S3_BUCKET_IMAGE;
    
        if (!file) {
            alert("Please select a file first.");
            return null;
        }
    
        AWS.config.update({
            accessKeyId: S3_KEY,
            secretAccessKey: S3_SECRET,
        });
    
        const s3 = new AWS.S3({
            params: { Bucket: S3_BUCKET },
            region: REGION,
        });
    
        const params = {
            Bucket: S3_BUCKET,
            Key: file.name,
            Body: file,
        };
    
        try {
            await s3.putObject(params)
                .on("httpUploadProgress", (evt) => {
                    console.log(
                        "Uploading " + parseInt((evt.loaded * 100) / evt.total) + "%"
                    );
                })
                .promise();
    
            const url = getFileUrl(file.name);
            return url; // Return the file URL directly
        } catch (err) {
            console.error("Error uploading file:", err);
            alert("Error uploading file.");
            return null;
        }
    };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let imageUrl = '';




    if (file) {
        imageUrl = await uploadFile(); // Capture the returned URL
    }

    const updatedRecipe = { 
        ...recipe, 
        imageUrl: imageUrl || recipe.imageUrl, // Use the returned URL directly
        userId: currentUser 
    };

    console.log(recipe.imageUrl)

    const init = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedRecipe)
    };

    fetch(url, init)
        .then(response => {
            if (response.status === 201 || response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            if (data.recipeId) {
                const newRecipes = [...recipes];
                newRecipes.push(data);
                setRecipes(newRecipes);
                handleTags(data.recipeId);
                navigate(`/recipe/${data.recipeId}`);
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
};


const handleTags = (recipeId) => {
    const tagId = recipe.tag
    const init = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: tagId
    };

    fetch(`${tagurl}/${recipeId}`, init)
            .then(response => {
            if (response.status === 201) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .catch(console.log);
}


    const handleChange = (e) => {
        const newRecipe = {...recipe};

        newRecipe[e.target.name] = e.target.value;

        setRecipe(newRecipe);

    }




    return(
        <>
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
                <fieldset className="form-group inputForm">
                    <label>Recipe Name</label>
                    <input type="text" className="form-control" id="recipeName"  name="recipeName" placeholder="Recipe Name" value={recipe.recipeName} onChange={handleChange}/>
                </fieldset>
                <div className="row">
                    <div className="col-md">
                        <fieldset className="form-group inputForm">
                            <label>Difficulty</label>
                            <input type="number" className="form-control" id="difficulty" name="difficulty" placeholder="Difficulty" min="1" max="5" value={recipe.difficulty} onChange={handleChange}/>
                        </fieldset>
                        </div>
                        <div className="col-md">
                        <fieldset className="form-group inputForm">
                    <label>Spiciness</label>
                    <input type="number" className="form-control" id="spicyness" name="spicyness" placeholder="Spiciness" min="1" max="5" value={recipe.spicyness} onChange={handleChange}/>
                </fieldset>
                </div>
                <div className="col-md">
                <fieldset className="form-group inputForm">
                    <label>Prep Time (in Minutes)</label>
                    <input type="number" className="form-control" id="prepTime" name="prepTime" placeholder="Prep Time" value={recipe.prepTime} min="1" onChange={handleChange}/>
                </fieldset>
                </div>
                </div>
                <fieldset className="form-group inputForm">
                    <label>Image</label>
                    <input type="file" className="form-control" id="image" onChange={handleFileChange}/>
                </fieldset>
                <fieldset className="form-group ">
                    <label>Description</label>
                    <textarea className="form-control descriptionRecipe" rows="2" id="description" name="description" placeholder="Description" value={recipe.description} onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group ">
                    <label>Tags</label>
                    <select className="form-control tagsSelect" id="tag" name="tag" value={recipe.tag} onChange={handleChange}>
                        <option value="">Select a Tag</option>
                        {tags.map((tag, index) => (
                            <option key={index} value={tag.tagId}>{tag.tagName}</option>
                        ))}
                    </select>
                </fieldset>
                <fieldset className="form-group">
                    <label>Text</label>
                    <textarea className="form-control textRecipe" id="text" name="text" placeholder="Text" rows="4" value={recipe.text} onChange={handleChange}/>
                </fieldset>
                <div className="buttonsFormContainer">
                <button type="submit" className="btn btn-primary formButton">Submit</button>
                <button className="btn btn-secondary formButton" onClick={() => navigate("/")}>Cancel</button>
                </div>
            </form>
        </section> : 
        <section className="container">
        <div className="container loginForm">
            <h2>Log in to add a recipe</h2>
            <div className="mt-3 ">
                    <p>You need to be logged in to add a recipe.</p>
                    <button className="btn btn-outline-info loginBtn" onClick={() => navigate('/login')}>Log In</button>
            </div>
        </div>
        </section>
        }
        </>
    )
}

export default AddRecipeForm;
