#!/bin/bash

echo .............................................................
echo ... Starting Mongo DB initialization for $1 environement ...
echo .............................................................


mongo itk --eval "db.users.remove({})"
mongoimport --db itk --collection users --file $1/users.js

mongo itk --eval "db.user_permissions.remove({})"
mongoimport --db itk --collection user_permissions --file $1/permissions.js

mongo itk --eval "db.localizations.remove({})"
mongoimport --db itk --collection localizations --file $1/localizations.js

mongo itk --eval "db.templates.remove({})"
mongoimport --db itk --collection report_templates --file $1/templates.js

mongo itk --eval "db.sequences.update({type:'PROJECT_ID'}, {\$inc:{value:100}}, { upsert: true })"

mongo itk --eval "db.rg_reports.remove({})"

mongo itk --eval "db.rg_projects.remove({})"

mongo itk --eval "db.customers.remove({})"

mongo itk --eval "db.projects.remove({})"

mongo itk --eval "db.projects_h.remove({})"

mongo itk --eval "db.assets.remove({})"

mongo itk --eval "db.assets_h.remove({})"

mongo itk --eval "db.rejects.remove({})"

mongo itk --eval "db.reports.remove({})"

mongo itk --eval "db.settlements.remove({})"

mongo itk --eval "db.rg_events.remove({})"

mongo itk --eval "db.crm_events.remove({})"

mongo itk --eval "db.user_events.remove({})"

mongo itk --eval "db.reports_events.remove({})"


echo ..............................................
echo ... Mongo DB prepared for $1 environement ...
echo ..............................................
