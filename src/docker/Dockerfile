FROM postgres:9.6.5

ENV POSTGRESQL_USER: postgres
ENV POSTGRESQL_PASSWORD: postgres
ENV POSTGRESQL_DATABASE: shopsystem

RUN mkdir ./sql

COPY ./*.sh ./
COPY ./*.sql ./sql/

RUN pg_createcluster 9.6 docker
RUN pg_ctlcluster 9.6 docker start

USER postgres

RUN sh ./init.sh

USER root

FROM openjdk:8

RUN apt-get update
RUN apt-get -y install nginx

RUN mkdir -p /etc/www/shop-system/logs
RUN mkdir -p /etc/nginx/sites-available
RUN mkdir -p /etc/nginx/sites-enabled

# Use gradle clean
# Use gradle build
# Copy the built Jar into toBeExported

COPY ./toBeExported/*.jar ./
COPY toBeExported/shopsystem /etc/nginx/sites-available/
# COPY ./toBeExported/*.sql ./

RUN rm /etc/nginx/sites-available/default
RUN rm /etc/nginx/sites-enabled/default
RUN ln -s /etc/nginx/sites-available/shop-system /etc/nginx/sites-enabled/default

# Expose ports.

EXPOSE 80

ENTRYPOINT sh -c 'service nginx start && java -jar ./shopsystem-0.0.1-SNAPSHOT.jar'
