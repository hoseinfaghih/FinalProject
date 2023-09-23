This is a training project for adding and managing map-related reports,as well as displaying reports related to or for specific route.

Curl Samples :

Registering User/Admin :

curl --location 'http://localhost:8080/auth/user/register' \
--header 'Content-Type: application/json' \
--data-raw '{
"name" : "steve",
"email": "hfaghih80@gmail.com",
"password": "123456",
"role" : "ADMIN"
}'