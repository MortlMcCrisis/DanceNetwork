# Importing JDK and copying required files
FROM openjdk:17-alpine AS build
WORKDIR /app
COPY gradle.properties .
COPY settings.gradle .
COPY build.gradle .
COPY src src

# Copy Maven wrapper
COPY gradlew .
COPY .gradle .gradle
COPY gradle gradle

# Set execution permission for the Maven wrapper
RUN chmod +x ./gradlew && \
    ./gradlew clean build bootJar

# Stage 2: Create the final Docker image using OpenJDK 19
FROM openjdk:17-alpine
VOLUME /tmp

# Copy the JAR from the build stage
#COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/build/libs/dancenetwork-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080