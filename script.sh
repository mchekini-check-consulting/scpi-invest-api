#!/bin/bash

java -javaagent:/apm.jar \
          -Delastic.apm.service_name=scpi-invest \
          -Delastic.apm.server_urls=http://apm-server.logging:8200 \
          -Delastic.apm.secret_token= \
          -Delastic.apm.application_packages=net.checkconsulting \
          -jar /opt/scpi-invest.jar