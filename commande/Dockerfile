# https://spring.io/guides/topicals/spring-boot-docker/
FROM eclipse-temurin:17-jdk-alpine

# https://github.com/mdutoo/ipi-jva324-distrarch-tptd
LABEL maintainer="mdutoo"

VOLUME /tmp
COPY target/*.jar app.jar

#EXPOSE 8282 8283 8284 8787
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n"
ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker -jar /app.jar"]