# Service Platform

A backend microservice system for an on-demand service marketplace, featuring:
- **Service Discovery**: Eureka
- **API Gateway**: Spring Cloud Gateway
- **Authentication & Authorization**: Spring Security + JWT
- **Async Messaging**: RabbitMQ
- **Data Persistence**: MyBatis + MySQL
- **Global Search**: Elasticsearch
- **Shared Structures**: Common module (DTOs, events, constants)

---

## Core Services

### Auth Service
Responsible for authentication and token issuance.

- Implements **JWT-based authentication** for end users (users/providers)
- Supports **client credentials flow** for internal service-to-service communication
- Issues and validates **internal tokens** with scoped permissions
- Caches internal tokens locally to reduce repeated auth requests

---

### User / Provider Services
Manage platform participants.

- User and provider accounts are stored in separate services
- Integrated with Auth Service via **Feign clients**
- Enforced role-based access through JWT claims

---

### Booking Service
Handles service requests and quotations.

- Users can create general or provider-specific service requests
- Providers submit quotes for active requests
- Listens to order-related events to update request/quote states
- Secured as a **resource server** using JWT validation

---

### Order Service
Manages order lifecycle and payment coordination.

- Creates orders based on accepted quotes
- Implements **idempotent order submission** using Redis token validation
- Uses **RabbitMQ delayed queues (TTL + DLX)** to automatically cancel unpaid orders
- Emits order creation and cancellation events for downstream services

---

### Service & Search Services
Support service discovery and filtering.

- Service metadata stored in MySQL
- Indexed into Elasticsearch for flexible search and filtering
- Search service exposed via REST APIs

---

## Asynchronous Workflow

The platform uses **event-driven communication** to decouple services:

- `OrderCreateEvent` and `OrderCancelEvent` are published via RabbitMQ
- Booking Service listens to events to update request and quote status
- Delayed queues are used to handle time-based order expiration

---

## Security Design

- **Stateless authentication** using JWT
- Separation of **user tokens** and **internal service tokens**
- Internal service calls authenticated via client credentials
- Resource servers validate tokens and extract authorities dynamically