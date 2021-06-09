

# CRUD API

A simple CRUD backend & frontend to manipulate a `Person` entity. The live demo can be seen at http://crudapi.s3-website-us-east-1.amazonaws.com/

## Backend

The backend is a Kotlin Spring Boot application. It contains REST endpoints for the `/people` resource
- `GET /people`
- `GET /people/{id}`
- `POST /people`
- `PATCH /people/{id}`
- `DELETE /people/{id}`

The postman collection in the root can be used to explore these endpoints

### Running

1. First, run DynamoDB locally with `./create-table.sh`

2. Make sure Java 11+ is installed and then run the app using gradle
   ```sh
   ./gradew bootRun
   ```

3. Import the postman collection and use the endpoints at localhost:5000

## Frontend

The frontend is an Angular 12 app built with Material Design components

### Running

1. Make sure `ng` is installed

2. `cd frontend/crud-frontend`

3. `npm i && ng serve -o`
