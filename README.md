# maverick-bank

Maverick Bank is a **full-stack banking management application** developed using **Spring Boot** for the backend and **HTML, CSS, JavaScript, and Bootstrap** for the frontend.
The system enables users to manage bank accounts, beneficiaries, and banking operations through a role-based dashboard.

---

## Tech Stack

**Backend**

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* Maven

**Frontend**

* HTML
* CSS
* Bootstrap
* JavaScript (AJAX / Fetch)

**Database**

* MySQL

---

## Architecture

The backend follows a **Layered Architecture**:

```
Controller → Service → Repository → Database
```

* **Controller** – Handles REST API requests
* **Service** – Contains business logic
* **Repository** – Handles database operations using JPA
* **DTO** – Transfers data between client and server

---

## Features

* User authentication and role-based access
* Account creation and management
* Beneficiary management
* Transaction and banking operations
* RESTful API integration with frontend

---

## Key Relationships

* **CustomerProfile → Accounts** (One-to-Many)
* **Account → Beneficiaries** (One-to-Many)

---

## Running the Project

1. Clone the repository

```
git clone https://github.com/Yazeensheik/maverick-bank.git
```

2. Configure the database in `application.properties`

3. Run the Spring Boot application

```
MaverickBankApplication.java
```

Application will start at:

```
http://localhost:8080
```

---

## Author

Developed as part of a **Full Stack Banking System project** using Spring Boot and modern web technologies.

---

If you'd like, I can also give you a **version that looks more impressive on GitHub (with badges, icons, and screenshots)** while still staying short.
