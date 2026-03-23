# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /app

# Copy only files needed to resolve dependencies first (better caching)
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Copy source code
COPY src ./src

# Build the jar
RUN mvn -B -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
