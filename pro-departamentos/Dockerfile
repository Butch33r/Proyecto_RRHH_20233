FROM openjdk:19-alpine as construc

WORKDIR /app/pro-departamentos

COPY ./pom.xml /app
COPY ./pro-departamentos/.mvn ./.mvn
COPY ./pro-departamentos/mvnw .
COPY ./pro-departamentos/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./pro-departamentos/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:19-alpine

WORKDIR /app
COPY --from=construc /app/pro-departamentos/target/pro-departamentos-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

ENTRYPOINT ["java","-jar","pro-departamentos-0.0.1-SNAPSHOT.jar"]