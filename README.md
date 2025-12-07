# RideShare Backend

A mini ride sharing backend built with Spring Boot and MongoDB.

## Tech Stack

- Spring Boot 3.2
- MongoDB
- JWT Authentication
- BCrypt Password Encoding
- Jakarta Validation

## Prerequisites

- Java 17 or higher
- MongoDB running on localhost:27017
- Maven

## Running the Application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The server will start on port 8081.

## API Endpoints

### Authentication (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/login | Login and get JWT token |

### Passenger Endpoints (ROLE_USER)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/rides | Create a new ride request |
| GET | /api/v1/user/rides | Get all rides for the logged-in user |

### Driver Endpoints (ROLE_DRIVER)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/v1/driver/rides/requests | Get all pending ride requests |
| POST | /api/v1/driver/rides/{id}/accept | Accept a ride request |

### Shared Endpoints (USER or DRIVER)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/rides/{id}/complete | Complete a ride |

## Sample Requests

### Register a User
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"1234","role":"ROLE_USER"}'
```

### Register a Driver
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"driver1","password":"abcd","role":"ROLE_DRIVER"}'
```

### Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"1234"}'
```

### Create a Ride (use token from login)
```bash
curl -X POST http://localhost:8081/api/v1/rides \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{"pickupLocation":"Koramangala","dropLocation":"Indiranagar"}'
```

### View Pending Rides (as driver)
```bash
curl -X GET http://localhost:8081/api/v1/driver/rides/requests \
  -H "Authorization: Bearer <driver_token>"
```

### Accept a Ride (as driver)
```bash
curl -X POST http://localhost:8081/api/v1/driver/rides/{rideId}/accept \
  -H "Authorization: Bearer <driver_token>"
```

### Complete a Ride
```bash
curl -X POST http://localhost:8081/api/v1/rides/{rideId}/complete \
  -H "Authorization: Bearer <token>"
```

## Project Structure

```
src/main/java/org/example/rideshare/
├── model/          # Entity classes
├── repository/     # MongoDB repositories
├── service/        # Business logic
├── controller/     # REST endpoints
├── config/         # Security configuration
├── dto/            # Request/Response DTOs
├── exception/      # Custom exceptions
└── util/           # Utility classes (JWT)
```
