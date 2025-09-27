IMAGE_NAME="nirmalkumarp/taskdemo-image"
printf "removing docker image %s if already exist" $IMAGE_NAME
docker image rm $IMAGE_NAME
cd ..
printf "\n\n ---------- Building the app with maven ----------\n\n"
mvn clean compile test install
printf "\n\n ---------- Building the docker image %s ----------\n\n" $IMAGE_NAME
docker build -t $IMAGE_NAME .
printf "\n\n ---------- Docker Image %s built ----------\n\n" $IMAGE_NAME