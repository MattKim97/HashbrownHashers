import AWS from 'aws-sdk';




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

function addRecipeForm(){

    const [file, setFile] = useState(null);
    const handleFileChange = (e) => {
      const file = e.target.files[0];
      setFile(file);
    };

    const uploadFile = async () => {
        const S3_BUCKET = S3_BUCKET_IMAGE;
        const REGION = REGION;

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

        var upload = s3
          .putObject(params)
          .on("httpUploadProgress", (evt) => {
            console.log(
              "Uploading " + parseInt((evt.loaded * 100) / evt.total) + "%"
            );
          })
          .promise();

        await upload.then((err, data) => {
          console.log(err);
          alert("File uploaded successfully.");
        });
      };


    return(
        <>
        </>
    )
}

export default addRecipeForm;
