# BikeBooking System

## Overview

BikeBooking is a Java-based application designed to manage bike reservations efficiently. The system allows users to book bikes, check availability, and manage reservations through a user-friendly interface. It leverages MySQL as the database backend to store and manage data related to bikes, users, and bookings.

## Features

- User authentication and authorization
- Bike inventory management
- Bike reservation system
- Booking management (create, update, delete)
- Availability check for bikes
- User-friendly interface

## Prerequisites

Before running the BikeBooking system, ensure you have the following installed:

- Java Development Kit (JDK) 8 or later
- MySQL Server
- MySQL Connector/J (Java MySQL driver)
- IDE (e.g., IntelliJ IDEA, Eclipse) or a text editor

## Setup Instructions

git clone https://github.com/pranaypatel/bikebooking.git


### 2. Set Up the MySQL Database

1. Open MySQL Workbench or your preferred MySQL client.
2. Create a new database named `bikebooking`.
3. Execute the following SQL script to create the necessary tables:

```sql
CREATE DATABASE bikebooking;

USE bikebooking;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE bikes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(50) NOT NULL,
    status ENUM('available', 'reserved') DEFAULT 'available'
);

CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    bike_id INT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (bike_id) REFERENCES bikes(id)
);
```

### 3. Configure the Database Connection

1. Open the `src/main/resources/database.properties` file.
2. Update the file with your MySQL database connection details:

```properties
db.url=jdbc:mysql://localhost:3306/bikebooking
db.username=your_mysql_username
db.password=your_mysql_password
```

### 4. Build and Run the Application

#### Using an IDE

1. Open the project in your preferred IDE.
2. Ensure that the MySQL Connector/J library is added to the project's dependencies.
3. Build and run the application from the IDE.

#### Using Command Line

1. Navigate to the project directory.
2. Compile the project using Maven:

```sh
mvn clean install
```

3. Run the application:

```sh
java -jar target/bikebooking-1.0-SNAPSHOT.jar
```

## Usage

1. **User Registration**: Register a new account by providing a username, password, and email.
2. **User Login**: Log in with your credentials to access the system.
3. **Bike Inventory**: View the list of available bikes.
4. **Bike Booking**: Select a bike and book it for a specified period.
5. **Manage Bookings**: View, update, or cancel your bookings.

## Contributing

We welcome contributions to the BikeBooking system. To contribute, follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit them (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Contact

If you have any questions or feedback, please contact us at [your-pranaypatel9696@gmail.com].

---

Thank you for using the BikeBooking system! Enjoy a seamless bike reservation experience.
