#!/bin/bash

service postgresql stop
service postgresql start

createdb shop-system
psql -U admin shop-system -f ./init_user.sql
psql -U admin shop-system < dbexport.pgsql


