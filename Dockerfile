FROM openjdk:19
ADD target/MyBookShopApp-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]