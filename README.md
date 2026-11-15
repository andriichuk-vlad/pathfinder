![images/M35Mako.jpg](images/M35Mako.jpg)
# Pathfinder
This is an MVP web app for shared rides (Car-pooling) with transit-based search logic involving intermediate cities.
#### [Demo Video](https://drive.google.com/file/d/1iofvAoxOY6MIHp45AF34VT8Ub019F0qR/view?usp=sharing)

## **Technology stack:**

* **Language & Framework:** Java 21, Spring Boot 4.1.2, Spring Data JPA
* **Database:** H2 In-Memory Database (pre-populated via [data.sql](src/main/resources/data.sql))
* **Frontend:** Vanilla JavaScript (Fetch API), HTML5, Tailwind CSS
* **Testing & Tools:** JUnit 5, Mockito, Lombok, Maven

## How to Run Locally

1. **Clone the repository on your computer:**
   ```bash
   git clone https://github.com/andriichuk-vlad/pathfinder.git
   cd pathfinder
   ```
* or New > Project from Version Control > and add as url HTTPS or SSH (if it is configured), if
you use IntelliJ IDEA
    ```SSH
    git@github.com:andriichuk-vlad/pathfinder.git
    ```
2. **Run the application using Maven Wrapper:**
   ```bash
   ./mvnw spring-boot:run
   ```
   *(On Windows Command Prompt / PowerShell, you can also use `mvnw.cmd spring-boot:run` or run `PathfinderApplication` directly from IntelliJ IDEA).*

3. **Access the application:**
    * **Web Interface:** `http://localhost:8080`
    * **H2 Database Console:** `http://localhost:8080/h2-console`
        * **JDBC URL:** `jdbc:h2:mem:testdb`
        * **User Name:** `sa`
        * **Password:** *(leave empty)*

## REST API Endpoints
##### USER
* `GET /user` — Retrieve all registered users.
* `GET /user/{userId}` — Retrieve user details by ID.
* `GET /user/email/{email}` — Retrieve user details by email.
* `POST /user` — Register a new user.
* `PUT /user/{userId}` — Update an existing user's details.

##### RIDE
* `GET /ride` — Retrieve all available rides.
* `GET /ride/search?fromCity={city}&toCity={city}` — Search rides by departure and destination cities (supports intermediate stops).
* `POST /ride/driver/{driverId}` — Create a new ride (automatically updates user role to `DRIVER`).
* `POST /ride/{rideId}/book/{userId}` — Book a seat on a ride (validates seat availability, prevents duplicate bookings, and blocks drivers from booking their own rides).
* `POST /ride/{rideId}/cancel/{userId}` — Cancel an existing booking and restore seat availability.

## AI-Assisted Development Workflow
This project was built using AI coding assistants guided by a custom [.cursorrules](.cursorrules). The ruleset enforced:
* Clean 3-layer backend architecture (`controller` -> `service` -> `repository`) and transactional integrity (`@Transactional`).
* Pragmatic MVP decisions, such as working directly with JPA entities instead of overhead DTOs/mappers.
* Lightweight, zero-build frontend architecture using Vanilla JS and Tailwind CSS CDN.