*README*

# Proyecto de Microservicios con Spring Boot

Este proyecto implementa una arquitectura de microservicios utilizando Spring Boot, Spring Cloud Gateway, RabbitMQ y PostgreSQL. El sistema está diseñado para gestionar autenticación de usuarios, catálogo de productos y creación de órdenes.

## Arquitectura del Sistema

El sistema está compuesto por los siguientes microservicios:

- **API Gateway**: Punto de entrada para todas las peticiones del cliente.
- **Auth Service**: Gestiona la autenticación de usuarios utilizando JWT.
- **Catalog Service**: Gestiona el catálogo de productos.
- **Orders Service**: Gestiona la creación y administración de órdenes.

Cada microservicio tiene su propia base de datos PostgreSQL y se comunica de manera independiente.

## Tecnologías Utilizadas

- Spring Boot
- Spring Cloud Gateway
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- RabbitMQ
- Docker
- Maven
- Spring Data JPA

## Ejecución del Proyecto

### 1. Levantar la infraestructura con Docker

Ejecutar en la raíz del proyecto:
docker-compose up -d
Copiar código

Esto iniciará los contenedores de:

- PostgreSQL
- RabbitMQ

### 2. Ejecutar los microservicios

Cada microservicio debe ejecutarse individualmente:

- API Gateway → puerto **8080**
- Auth Service → puerto **8081**
- Catalog Service → puerto **8082**
- Orders Service → puerto **8083**

Los servicios se pueden ejecutar usando:
mvn spring-boot:run
Copiar código

## Ejemplos de Endpoints

### Verificar estado de servicios

Auth Service
GET http://localhost:8080/auth/actuator/health⁠�
Copiar código

Catalog Service
GET http://localhost:8080/catalog/ping⁠�
Copiar código

Orders Service
GET http://localhost:8080/orders/ping⁠�
Copiar código

## Comunicación entre Microservicios

Cuando se crea una orden en el **Orders Service**, se genera un evento llamado **OrderCreatedEvent** que se envía a **RabbitMQ**.  

El **Catalog Service** escucha este evento mediante un listener y procesa la información correspondiente.

Este enfoque permite una **comunicación asíncrona basada en eventos**, lo cual desacopla los microservicios y mejora la escalabilidad del sistema.

## Autor

Proyecto académico desarrollado para demostrar una arquitectura de microservicios utilizando Spring Boot y mensajería basada en eventos.



*DIAGRAMA MERMAID*

## Diagrama de Arquitectura

```mermaid
graph TD

Client[Cliente] --> Gateway[API Gateway]

Gateway --> Auth[Auth Service]
Gateway --> Catalog[Catalog Service]
Gateway --> Orders[Orders Service]

Orders --> RabbitMQ[RabbitMQ]
Catalog --> RabbitMQ

Auth --> AuthDB[(Auth DB)]
Catalog --> CatalogDB[(Catalog DB)]
Orders --> OrdersDB[(Orders DB)]