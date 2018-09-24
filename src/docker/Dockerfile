FROM postgres:9.6.5

ENV POSTGRESQL_USER: postgres
ENV POSTGRESQL_PASSWORD: postgres
ENV POSTGRESQL_DATABASE: shop-system

RUN mkdir ./sql

COPY ./*.sh ./
COPY ./*.sql ./sql/

RUN pg_createcluster 9.6 docker
RUN pg_ctlcluster 9.6 docker start

USER postgres

RUN sh ./init.sh

USER root

# Expose ports.

EXPOSE 5432
EXPOSE 5433
EXPOSE 443

FROM openjdk:8

RUN apt-get update
RUN apt-get -y install nginx

RUN mkdir -p /etc/www/authentication/logs
RUN mkdir -p /etc/ssl/authentication
RUN mkdir -p /etc/nginx/sites-available
RUN mkdir -p /etc/nginx/sites-enabled

# Use gradle clean
# Use gradle build
# Copy the built Jar into toBeExported

COPY ./toBeExported/*.jar ./
COPY ./toBeExported/authentication.* /etc/ssl/authentication/
COPY ./toBeExported/authentication /etc/nginx/sites-available/
# COPY ./toBeExported/*.sql ./

RUN rm /etc/nginx/sites-available/default
RUN rm /etc/nginx/sites-enabled/default
RUN ln -s /etc/nginx/sites-available/authentication /etc/nginx/sites-enabled/default

# Expose ports.

EXPOSE 80
EXPOSE 8080

ENTRYPOINT sh -c 'service nginx start && java -jar ./shopsystem-0.0.1-SNAPSHOT.jar'
