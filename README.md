# Car Booking System (Java Maven Project)

## Overview
This is a Java-based Car Booking System developed as part of an AUT software development assignment.  
The application follows a layered architecture using DAO, Service, and UI layers and uses an embedded Apache Derby database for data persistence.

The system supports managing users, cars, customers, and bookings through a desktop GUI application.

---

## Features
- User registration and login system
- Add, view, update, and delete cars
- Customer management
- Create and manage bookings
- Admin booking management panel
- Role-based access (Admin/User)
- DAO-based database architecture
- Observer pattern for booking updates

---

## Technologies Used
- Java (JDK 17+)
- Maven
- Apache Derby (Embedded Database)
- Swing GUI (NetBeans UI Forms)
- JUnit (for unit testing)
- NetBeans IDE

---

## Project Structure
src/
├── main/
│ ├── java/
│ │ ├── app
│ │ ├── dao
│ │ ├── model
│ │ ├── service
│ │ ├── ui
│ │ └── observer
│ └── resources/
│ └── icons/
└── test/
└── java/
└── test/

---

## How to Run the Project

### Option 1: NetBeans (Recommended)
1. Open NetBeans IDE
2. Click **File → Open Project**
3. Select the project folder (`bookingsystem`)
4. Wait for Maven dependencies to load
5. Clean and Build the project
6. Run the main class:
   ```
   app.AppLauncher
   ```

---

### Option 2: Maven (Command Line)
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="app.AppLauncher"
