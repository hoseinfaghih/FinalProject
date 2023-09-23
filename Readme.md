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

Login :

    curl --location 'http://localhost:8080/auth/user/login' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "email": "hfaghih80@gmail.com",
        "password" : "123456"
    }'


Adding Report :

    curl --location 'http://localhost:8080/report/add-report' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZmFnaGloODBAZ21haWwuY29tIiwiaWF0IjoxNjk1NDQ5MTQ3LCJleHAiOjE2OTU1MzU1NDd9.CPNYV7NnpDgyHvu4tv8Y-9jGTVrE-XJFZ1pdNIbrsS4' \
    --data '{
        "x" : 51.4378,
        "y" : 35.71678,
        "type" : "Traffic",
        "innerType" : "Low"
    }'

Getting Reports :

    curl --location 'http://localhost:8080/admin/all?approve=true&alive=false' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZmFnaGloODBAZ21haWwuY29tIiwiaWF0IjoxNjk1NDQ5MTQ3LCJleHAiOjE2OTU1MzU1NDd9.CPNYV7NnpDgyHvu4tv8Y-9jGTVrE-XJFZ1pdNIbrsS4'




approving/disapproving Reports by admins :

    curl --location 'http://localhost:8080/admin/approve' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZmFnaGloODBAZ21haWwuY29tIiwiaWF0IjoxNjk1NDYwOTIzLCJleHAiOjE2OTU1NDczMjN9.TWfD9r9pODyhyqWEJMLklkVi5vCC-MEVrY0NH5re1S0' \
    --data '{
        "ids" : [1,2,3,4,5]
    }'

like/dislike Reports :

    curl --location --request POST 'http://localhost:8080/report/like/3' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZmFnaGloODBAZ21haWwuY29tIiwiaWF0IjoxNjk1NDUzNzIxLCJleHAiOjE2OTU1NDAxMjF9.XJKeOx14chnoltJe0npcZXnpHVk3-vBSfBSxbW7fzTM'


getting reports near a Route :

    curl --location --request GET 'http://localhost:8080/path/near-reports' \
    --header 'Content-Type: application/json' \
    --data '{
        "path": "LINESTRING(51.4378 35.71678,51.43772 35.71681,51.43803 35.7174,51.43766 35.71753,51.43767 35.71755,51.43798 35.71821,51.43792 35.71823)"
    }'

most accidental hour :

    curl --location 'http://localhost:8080/accident/ms-hour' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZmFnaGk4MEBnbWFpbC5jb20iLCJpYXQiOjE2OTUxNTM0MjgsImV4cCI6MTY5NTIzOTgyOH0.zE9Rgw5xYZ9L1qViKM6mjN261h1ex64tRnPdyS0-RRA'


