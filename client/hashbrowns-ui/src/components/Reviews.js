import { init } from 'create-react-app/createReactApp';
import React from 'react'
import { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

function Reviews() {
    const [reviews, setReviews] = useState([]);
    const [review, setReview] = useState([]);
    const [errors, setErrors] = useState([]);
    const [hasReviewed, setHasReviewed] = useState([]);
    const url = "http://localhost:8080/api/reviews"
    //this is recipeId
    const { id } = useParams();
    const userId = 1;
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`${url}/${id}`)
        .then(response => {
            if(response === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected Status Code:${response.status}`);
            }
        })
        .then((data) => {
            if(data) {
                setReviews(data);
                setHasReviewed(reviews.filter(review => review.recipeId
                    === id && review.userId === userId));
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
    })

    const handleAddReview = () => {
        const init = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'},
                body: JSON.stringify(review)
            }


        fetch(url, init)
        .then(response => {
            if(response === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected Status Code:${response.status}`);
            }
        }).then(data => {
            if(data.review_id) {
                //Reloads the page
                navigate(`/recipe/${id}`);
            } else
                setErrors(data);
        })
    };

    const handleDeleteReview = (deleteUserId) => {
        const reviewhand = reviews.find(review => {review.userId === deleteUserId && review.recipeId === id});
        if(window.confirm(`Delete This Review: ${reviewhand.title}?`)) {
            const reviewid = reviewhand.reviewId;
            const init = {
                method: 'DELETE'
            };
            fetch(`${url}/${reviewid}`, init)
            .then(response => {
                if(response.status === 204) {
                    const newReviews = reviews.filter(a => a.reviewId !== reviewid)
                    setReviews(newReviews);
                } else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`);
                }
            })
            .catch(console.log);
        }
    };

    const handleChange = (event) => {
        //create new review by copying review
        const newReview = {...review};
        //sets value based on name of event target
        newReview[event.target.name] = event.target.value;
        //sets state review to newReview
        setReview(newReview);
    }

    const handleSubmit = (event)  => {
        event.preventDefault();
        handleAddReview();

    }



    return(<>
        <section className="container">
        <h2 className='mb-4'>Reviews</h2>
            {!(hasReviewed > 0) &&
            (<form onSubmit={handleSubmit}>
            <fieldset className="form-group">
        <label htmlFor="title">Review Title</label>
            <input
                id="title"
                name="title"
                type="text"
                className="form-control"
                value={review.title}
                onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group">
        <label htmlFor="description">Review Description</label>
            <input
                id="description"
                name="description"
                type="text"
                className="form-control"
                value={review.description}
                onChange={handleChange}/>
                </fieldset>
                <fieldset className="form-group">
        <label htmlFor="rating">Review Rating</label>
            <input
                id="rating"
                name="rating"
                type="number"
                className="form-control"
                value={review.rating}
                onChange={handleChange}/>
                </fieldset>
            <button type="submit" className="btn btn-outline-secondary mb-4" onClick={(event) => handleSubmit(event)}>
                Add Review
            </button>
            </form>
            )}
             <section className="row">
                {reviews.map(rev =>
                    <div className="col-md">
                         <div className="card" id={rev.reviewId}>
                         <div className="card-body">
                            <span>{rev.rating}/5   {rev.title}</span>
                            <p>{rev.description}</p>
                            {rev.userId === userId && (
                                <button className="btn btn-danger" onClick={() => handleDeleteReview(rev.userId)}>Delete Review</button>
                            )}
                    </div>
                    </div>
                    </div>
                )}
             </section>
        </section>

    </>)
}

export default Reviews;