#!/bin/bash

echo "host    all             all             0.0.0.0/0               md5" >> /etc/postgresql/9.6/docker/pg_hba.conf

service postgresql restart

