# Virtual-Cinema RESTful API

This project from JetBrains Academy aims to create a RESTful API for a virtual movie theater. It involves modeling a cinema's seating layout and developing an endpoint to retrieve this information. 
The goal is to develop skills in building web services, handling HTTP requests, and working with JSON.

## API Endpoints

GET /api/v1/seats This endpoint retrieves the cinema's seat layout. It provides row and column information, and the price details for each seat.
Example Request: GET http://localhost:28852/api/v1/seats
Example Response: HTTP/1.1 200 OK Content-Type: application/json { "rows": 10, "columns": 10, "seats": [{ "row": 1, "column": 1, "price": 10 }, ... ] }

POST /api/v1/purchase This endpoint allows a user to purchase a ticket. You need to provide a row and column number in the request body to reserve a seat. If the seat is already reserved, you will receive an error message.
Example Request: POST http://localhost:28852/api/v1/purchase Content-Type: application/json { "row": 1, "column": 1 }
Example Response: HTTP/1.1 200 OK Content-Type: application/json { "token": "uuid_generated_here", "ticket": { "row": 1, "column": 1, "price": 10 } }

POST /api/v1/return This endpoint handles the return of a ticket. To do this, you must provide a valid token in the request body.
Example Request: POST http://localhost:28852/api/v1/return Content-Type: application/json { "token": "uuid_generated_here" }
Example Response: HTTP/1.1 200 OK Content-Type: application/json { "returned_ticket": { "row": 1, "column": 1, "price": 10 } }

GET /api/v1/stats This endpoint retrieves the stats from the cinema, which includes the total income, and the amount of available and purchased tickets. A password must be provided as a query parameter for this request (the default password is "super_secret").
Example Request: GET http://localhost:28852/api/v1/stats?password=super_secret
Example Response: HTTP/1.1 200 OK Content-Type: application/json { "income": 1000, "available_tickets": 90, "purchased_tickets": 10 }
