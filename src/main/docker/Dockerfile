FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD blog-0.0.1-SNAPSHOT.jar blog.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/blog.jar"]