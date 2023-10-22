# FlightBookingSystem

Booking flight ticket system.

Forked from https://github.com/aliahmadi4/FlightBookingSystem
Many thanks to the authors of the original code.

1. Framework: Spring Boot
2. Database: MySQL

Hibernate, Thymeleaf, Spring Boot Security, Thymeleaf Dialect, JPA, API Docs

Roles:

1. Admin: username=john, password:john123, Add/Remove flight, airplane, and aircraft, search flight, verify ticket
2. Agent: username=mike, password:mike123, Book/Cancel ticket for passengers, search flight, verify ticket

-----------

# HOW-TOs

1. Run MySql Server locally in a Docker container:

> docker pull mysql/mysql-server
>
> docker run -p 3306:3306 --name cont-mysql -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=ftb_db -d
> mysql/mysql-server

2. Access REST API Docs:

> host:port/api-docs/

e.g.

> http://localhost:8080/api-docs/

Swagger-UI (human-friendly API Docs) should be available at:
> host:port/swagger-ui.html

e.g.

> http://localhost:8080/swagger-ui.html


-----------

# TODO:

- UI: (Admin) Flights.
- Cleanup documentation.
- Add more unit and integration tests.
