# Investment Portfolio Tracker

Secure Spring Boot application for managing investment portfolios.

## Features
- JWT authentication & role-based access
- PostgreSQL database
- Portfolio creation, positions tracking
- Basic P&L calculations
- Dockerized with docker-compose

## Tech Stack
- Java 25
- Spring Boot 4.0.3
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Docker & docker-compose

## Setup
1. Clone repo
2. `docker compose up --build`
3. Access: http://localhost:8082

## Endpoints (examples)
- POST /api/auth/register
- POST /api/auth/login
- POST /api/portfolios (protected)

See full API docs in Swagger (once added).
