IMAGE_NAME="taskdemo-image"
docker image rm $IMAGE_NAME
cd ..
mvn clean compile test install
docker build -t $IMAGE_NAME .