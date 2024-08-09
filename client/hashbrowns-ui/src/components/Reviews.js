import React from 'react'
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

function Reviews(props) {
    const [reviews, setReviews] = useState([]);
    const [review, setReview] = useState([]);
    const [errors, setErrors] = useState([]);
    const url = "http://localhost:8080/api/reviews"
    //this is recipeId
    const { id } = useParams();

    useEffect(() => {
        review.recipeId = id;
        review.userId = props.currentUser;
        fetch(`${url}/${id}`)
        .then(response => {
            if(response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected Status Code:${response.status}`);
            }
        })
        .then((data) => {
            if(data) {
                setReviews(data);
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
    }, [id])

    const handleAddReview = (event) => {
        event.preventDefault();
        const init = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'},
                body: JSON.stringify(review)
            }


        fetch(url, init)
        .then(response => {
            if(response.status === 201) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected Status Code:${response.status}`);
            }
        }).then(data => {
            if(data.reviewId) {
                console.log(data);
                let newReviews = reviews.filter(a => a.reviewId > 0);
                newReviews.push(data);
                console.log(reviews.filter(rev => rev.userId === props.currentUser));
                setReviews(newReviews);

            } else
                setErrors(data);
                
        })
        
    };

    const handleDeleteReview = (rev) => {
        if(window.confirm(`Delete This Review: ${rev.title}?`)) {
            const reviewid = rev.reviewId;
            const init = {
                method: 'DELETE'
            };
            fetch(`${url}/${reviewid}`, init)
            .then(response => {
                if(response.status === 204) {
                    const newReviews = reviews.filter(a => a.reviewId !== reviewid)
                    setReviews(newReviews);
                    return response.json();
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

    const getStars = (rating) => {
        const stars = Math.max(1, Math.min(rating, 5));
        return '⭐'.repeat(stars);
    };



    return(<>
        <section className="container">
        <h2 className='mb-4'>Reviews</h2>
            {(props.currentUser != null && (reviews.filter(rev => rev.userId == props.currentUser).length < 1)) ?
            (<form onSubmit={handleAddReview}>
            <div className="row"> 
            <div className="col-9">
            <fieldset className="form-group inputForm">
        <label htmlFor="title">Review Title</label>
            <input
                id="title"
                name="title"
                type="text"
                className="form-control"
                value={review.title}
                onChange={handleChange}/>
                </fieldset>
                </div>
                <div className="col-3">
                <fieldset className="form-group inputForm">

        <label htmlFor="rating">Review Rating</label>
            <input
                id="rating"
                name="rating"
                type="number"
                className="form-control"
                value={review.rating}
                onChange={handleChange}/>
                </fieldset>
                </div>
                </div>
                <fieldset className="form-group inputForm">
        <label htmlFor="description">Review</label>
            <textarea
                id="description"
                name="description"
                placeholder="Your Review"
                rows="4"
                className="form-control"
                value={review.description}
                onChange={handleChange}/>
                </fieldset>

            <button type="submit" className="btn btn-primary formButton mb-4">
                Add Review
            </button>
            </form>
            ) : null }
             <section className="row">
                {reviews.map((rev,index) =>
                    <div className="col-md" key={index} >
                         <div className="card" id={rev.reviewId}>
                         <div className="card-body">
                            <h3>{rev.title}</h3>
                            <div>{getStars(rev.rating)}</div>
                            <p>{rev.description}</p>
                            {(props.currentUser != null && rev.userId == props.currentUser) ?
                            (
                                <button className="btn btn-danger formButton" onClick={() => handleDeleteReview(rev)}>Delete Review</button>
                            ) : null }
                    </div>
                    </div>
                    </div>
                )}
             </section>
        </section>

    </>)
}

export default Reviews;
