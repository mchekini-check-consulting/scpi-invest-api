FROM ubuntu
RUN apt-get update && apt-get install openjdk-17-jdk vim curl -y
WORKDIR /opt
ADD target/scpi-invest-*.jar scpi-invest.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/scpi-invest.jar"]