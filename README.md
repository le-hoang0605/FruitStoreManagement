**FRUIT STORE MANAGEMENT SYSTEM**
Spring Boot Backend Project
**1. Project Description**
The Fruit Store Management System is a backend RESTful API developed using Java and Spring Boot. The system is designed to manage fruits, categories, users, and customer orders for a fruit store.
The project require developing a backend system with database interaction, CRUD operations, authentication, authorization, validation, and testing.
The system provides RESTful APIs for managing fruits and categories, creating and viewing orders, and authenticating users using JSON Web Tokens (JWT). Different levels of access are provided based on user roles, including ADMIN and USER.
The backend is implemented using Spring Boot, Spring Web, Spring Data JPA, Hibernate, Spring Security, and JWT. The system follows a layered architecture consisting of controllers, services, repositories, entities, DTOs, and security components.
**2. Main Objectives**
The main objectives of this project are:
•	Build a complete backend application using Spring Boot.
•	Design and implement RESTful APIs.
•	Use JPA and Hibernate for database interaction.
•	Implement CRUD operations for the main entities.
•	Model relationships between entities using JPA.
•	Implement authentication using JWT.
•	Implement role-based authorization.
•	Validate user input.
•	Apply transaction management to important business operations.
•	Write unit tests using JUnit and Mockito.
•	Apply dependency injection and clean backend architecture.
**3. Main Entities**
The system consists of the following main entities:
•	**User** – stores user information, authentication credentials, and role.
•	**Category** – represents a category of fruits.
•	**Fruit** – stores fruit information, price, and available stock.
•	**Order** – represents an order created by a user.
•	**OrderItem** – represents an individual fruit and quantity within an order.
The relationships between these entities are:
•	A **Category** has many **Fruits**.
•	A **Fruit** belongs to one **Category**.
•	A **User** can have many **Orders**.
•	An **Order** belongs to one **User**.
•	An **Order** contains many **OrderItems**.
•	An **OrderItem** belongs to one **Order**.
•	An **OrderItem** references one **Fruit**.
**4. Core Functionalities**
**4.1 Authentication and Authorization**
The system provides JWT-based authentication through a login endpoint.
Users are divided into two roles:
•	**ADMIN**
•	**USER**
Administrators can manage fruits and view all orders, while authenticated users can create orders and view their own orders.
**4.2 Fruit Management**
The system provides APIs to:
•	Retrieve all fruits.
•	Retrieve a fruit by ID.
•	Create a new fruit.
•	Update an existing fruit.
•	Delete a fruit.
•	Retrieve fruits by category.
**4.3 Category Management**
The system provides an API to retrieve all available fruit categories.
**4.4 Order Management**
Authenticated users can create orders by providing a list of fruits and quantities.
When an order is created, the system automatically:
1.	Identifies the current user from the JWT.
2.	Sets the order date using the server time.
3.	Sets the initial order status to PENDING.
4.	Retrieves the current price of each fruit.
5.	Stores the price as the unitPrice of the corresponding order item.
6.	Validates that the requested quantity is positive.
7.	Checks whether sufficient stock is available.
8.	Calculates the total amount.
9.	Updates the fruit stock.
10.	Saves the order and its order items.
The original exam requirement specifies that the system should extract the user ID from the JWT, calculate the order total from quantity and unit price, and validate stock availability.
**5. Security**
The system applies Spring Security to protect restricted endpoints.
The security requirements include:
•	JWT-based authentication.
•	Password hashing using BCrypt.
•	Role-based authorization.
•	Input validation.
•	Restriction of users from accessing other users' orders.
Public users can view fruits and categories without authentication.
Administrators can create, update, and delete fruits and view all orders.
Authenticated users can create orders and view their own orders. These authorization rules are based on the original final examination requirements.
**6. Technology Stack**
The project uses the following technologies:
•	Java
•	Spring Boot
•	Spring Web
•	Spring Data JPA
•	Hibernate
•	Spring Security
•	JWT
•	Jakarta Bean Validation
•	PostgreSQL
•	Maven
•	JUnit 5
•	Mockito
**7. Project Architecture**
The project follows a layered backend architecture:
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
Database

Security components are integrated into the request processing flow:
Client
   ↓
JWT Authentication
   ↓
Spring Security
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
The project separates responsibilities between controllers, services, repositories, entities, DTOs, exception handling, and security components.
**8. Validation and Error Handling**
The system validates incoming requests using Jakarta Bean Validation.
Examples of validation rules include:
•	Fruit name is required and has a maximum length.
•	Fruit price must be positive.
•	Category name is required.
•	User email must have a valid email format.
•	Order quantity must be positive.
•	Order date cannot be in the future.
•	Referenced entities must exist.
Invalid requests should return an appropriate 400 Bad Request response, while requests for non-existing resources should return 404 Not Found.
**9. Testing**
The project includes unit tests for important business logic.
At minimum, the following cases should be tested:
•	Successful order creation.
•	Fruit not found.
•	Insufficient stock.
•	Invalid order quantity.
•	Correct calculation of order total.
•	Authorization restrictions.
JUnit 5 is used for testing, while Mockito can be used to mock dependencies such as repositories.

