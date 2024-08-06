import AWS from 'aws-sdk';
import { useEffect, useState } from 'react'
import {Link} from "react-router-dom"



const RECIPE_DEFAULT = {
    recipeName: '',
    difficulty: 1,
    spicyness: 1,
    prepTime: 0,
    imageUrl: '',
    description: '',
    text: ''
}

const S3_BUCKET_IMAGE = process.env.REACT_APP_S3_BUCKET_IMAGE;
const REGION = process.env.REACT_APP_REGION;
const S3_KEY = process.env.REACT_APP_S3_KEY;
const S3_SECRET = process.env.REACT_APP_S3_SECRET;

const getFileUrl = (fileName) => {
  return `https://${S3_BUCKET_IMAGE}.s3.${REGION}.amazonaws.com/${fileName}`;
};

function AddRecipeForm(){
  
    const [file, setFile] = useState(null);
    const [fileUrl, setFileUrl] = useState('');

    const handleFileChange = (e) => {
      const file = e.target.files[0];
      setFile(file);
    };

    const uploadFile = async () => {
      const S3_BUCKET = S3_BUCKET_IMAGE;

      if (!file) {
        alert("Please select a file first.");
        return;
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
          setFileUrl(url);
          console.log("File uploaded successfully. File URL:", url);
          alert("File uploaded successfully. Check the link below.");
      } catch (err) {
          console.error("Error uploading file:", err);
          alert("Error uploading file.");
      }
  };

    return(
        <>
            <>
            <input type="file" onChange={handleFileChange} />
            <button onClick={uploadFile}>Upload File</button>
            {fileUrl && (
                <div>
                    <p>File URL:</p>
                    <a href={fileUrl} target="_blank" rel="noopener noreferrer">{fileUrl}</a>
                </div>
            )}
        </>
        </>
    )
}

export default AddRecipeForm;
