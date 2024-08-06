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
