FROM maven:3.6.3-openjdk-16-slim AS javabuilder
WORKDIR /app
COPY pom.xml .
COPY src /app/src
RUN ["mvn", "--version"]
RUN ["mvn", "install"]
RUN ["mvn", "clean", "package"]

FROM node:22
WORKDIR /app
COPY --from=javabuilder /app/target/myapp.jar /app/myapp.jar
COPY package*.json ./
RUN ["npm", "install"]
COPY . .
RUN ["npm", "run", "build"]
CMD ["node", "index.js"]
