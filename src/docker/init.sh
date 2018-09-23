#!/bin/bash

service postgresql stop
service postgresql start

psql -U postgres -f ./sql/init_user.sql
