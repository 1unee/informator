FROM openjdk:17-jdk-alpine

ARG APP_NAME
ARG APP_VERSION

ENV APP_NAME=${APP_NAME}
ENV APP_VERSION=${APP_VERSION}

RUN echo "Building ${APP_NAME}:${APP_VERSION}..."

WORKDIR /app

COPY target/${APP_NAME}-${APP_VERSION}.jar /app/${APP_NAME}.jar

ENTRYPOINT ["sh", "-c", "java -jar /app/${APP_NAME}.jar"]
