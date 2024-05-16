FROM openjdk:17

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

FROM alpine:3.13.2
# RUN apk add --no-cache tzdata
COPY --from=build /app/executable /executable
COPY --from=build /app/key.pem /key.pem
COPY --from=build /app/pub.pem /pub.pem
COPY --from=build /app/docker.json /docker.json
ENV PORT=8080
EXPOSE 8080
