import React from 'react'
import { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

function Reviews() {
    const [reviews, setReviews] = useState([]);
    const [errors, setErrors] = useState([]);
    const url = "http://localhost:8080/api/reviews"
    const { id } = useParams();

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
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
    }
)

    return(<>
    
    </>)
}

export default Reviews;