# Stage 1: Build the Spring Boot application
FROM maven:3.8.6-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml .
COPY src ./src

# Package the Spring Boot application as a WAR file
RUN mvn clean install

# Stage 2: Run the Spring Boot application with Tomcat
FROM tomcat:10.1.14-jdk17-temurin

# Remove the default ROOT web application
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy the WAR file from the build stage to the Tomcat webapps directory
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port Tomcat is running on
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
