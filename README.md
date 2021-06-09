

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

1. First, create a DynamoDB table named `person` with a hash key `id` of type `string`

2. Make sure Java 11+ is installed

3. Fill out the AWS credentials in the `application.properties`
  - `aws.region`
  - `aws.accesskey`
  - `aws.secretkey`

4. Run the app using gradle
   ```sh
   ./gradew bootRun
   ```

5. Use the endpoints at localhost:5000

## Frontend

The frontend is an Angular 12 app built with Material Design components

### Running

1. `ng serve`
