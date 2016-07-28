set dbName=%1
echo off

echo ##### Initializing DB...

mongo %dbName% --eval "db.users.remove({})"
mongoimport --db %dbName% --collection users --file %dbName%/users.json

mongo %dbName% --eval "db.planets.remove({})"
mongoimport --db %dbName% --collection planets --file %dbName%/planets.json

echo ##### Done!
