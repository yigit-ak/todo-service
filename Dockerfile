FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only necessary files first to leverage Docker cache
COPY pom.xml ./
RUN mvn dependency:go-offline

# Now copy the full source and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM amazoncorretto:21-alpine-full

WORKDIR /app

# Copy the built jar from the builder image
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

RUN addgroup --system app && adduser --system --ingroup app app
USER app

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
