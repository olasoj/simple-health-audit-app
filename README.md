# Health & Audit Controller

An app exposes health and Audit functionality .

## Authors

- [@olasoj](https://www.github.com/olasoj)

## Environment Variables

This project made no use of environmental variables.

## Run Locally

Clone the project

```bash
   git clone -b master https://github.com/olasoj/simple-health-audit-app.git
```

Go to the project directory

```bash
  cd simple-health-audit-app
```

Start the app

```bash
    mvn spring-boot:run
```

Test health endpoint

```bash
    curl --location 'localhost:8080/api/v1/health/details'
```

Test audit endpoint

```bash
    curl --location 'localhost:8080/api/v1/audit/log' \
    --header 'Content-Type: application/json' \
    --data '{
        "eventType": "LOGIN",
        "description": "User attempted login",
        "userId": "12345"
    }'
```

## OpenAPI

- http://localhost:8080/swagger-ui/index.html
- http://localhost:8080/api/v1/v3/api-docs

## Tech Stack

**Client:** Curl

**Server:** Java, Spring boot
