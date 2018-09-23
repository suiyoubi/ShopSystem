FROM openjdk:8

# Use gradle clean
# Use gradle build
# Copy the built Jar into toBeExported

COPY ./toBeExported/*.jar ./

# Expose ports.

EXPOSE 80
EXPOSE 8080

ENTRYPOINT sh -c 'java -jar ./shopsystem-0.0.1-SNAPSHOT.jar'
