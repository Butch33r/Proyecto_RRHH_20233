FROM openjdk:19-alpine as construc

WORKDIR /app/pro-empleados

COPY ./pom.xml /app
COPY ./pro-empleados/.mvn ./.mvn
COPY ./pro-empleados/mvnw .
COPY ./pro-empleados/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./pro-empleados/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:19-alpine

WORKDIR /app
COPY --from=construc /app/pro-empleados/target/pro-empleados-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

ENTRYPOINT ["java","-jar","pro-empleados-0.0.1-SNAPSHOT.jar"]