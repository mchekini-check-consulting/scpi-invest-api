FROM ubuntu
RUN apt-get update && apt-get install openjdk-17-jdk vim curl -y
ADD elastic-apm-agent-1.46.0.jar /apm.jar
WORKDIR /opt
ADD target/scpi-invest-*.jar scpi-invest.jar
ADD script.sh /script.sh
RUN chmod 777 /script.sh
EXPOSE 8080
ENTRYPOINT ["/script.sh"]