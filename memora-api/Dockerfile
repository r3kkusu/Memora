# Use a base image with Java and a minimal Linux distribution
FROM openjdk:19-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your build target to the container
COPY build/libs/memora-api.jar /app/memora-api.jar

# Expose the port your Spring Boot application runs on (default is 8080)
EXPOSE 8080

# Specify the command to run your application when the container starts
CMD ["java", "-jar", "memora-api.jar"]
