#!/bin/bash

echo ##### Initializing DB...

mongo $1 --eval "db.users.remove({})"
mongoimport --db $1 --collection users --file $1/users.json

mongo $1 --eval "db.planets.remove({})"
mongoimport --db $1 --collection planets --file $1/planets.json

echo ##### Done!