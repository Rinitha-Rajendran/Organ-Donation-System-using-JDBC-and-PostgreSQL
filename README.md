# Organ Donation System

## Project Overview
The Organ Donation System is a Java-based application designed to streamline the process of organ donation and transplantation. Utilizing JDBC and PostgreSQL for robust data management, it incorporates OOP concepts and features secure user management, automated matching, and comprehensive admin dashboards, including detailed records for donors, patients, and transportation logistics.

## Features
- **User Management**: Secure registration and login for donors, recipients, and administrators.
- **Donor Registration**: Donors can register their organs, specifying the type of organ and other relevant details.
- **Recipient Registration**: Potential recipients can register their need for specific organs.
- **Matching System**: Automated matching of donors and recipients based on compatibility criteria.
- **Search Functionality**: Search for donors and recipients based on various parameters.
- **Notification System**: Automated notifications to donors and recipients regarding matches and updates.
- **Admin Dashboard**: Comprehensive dashboard for administrators to manage users, view statistics, and oversee the entire organ donation process.
- **Transportation Details**: Manage transportation logistics for organ delivery.

## Technologies Used
- **Java**: Core programming language used for developing the application.
- **JDBC**: Used for database connectivity and operations.
- **PostgreSQL**: Relational database used to store and manage data.
- **Swing**: GUI toolkit for building the user interface.
- **Maven**: Project management and build automation tool.

## Setup and Installation
### Prerequisites
- **Java Development Kit (JDK)**
- **IntelliJ IDEA**
- **PostgreSQL**

### Steps to Setup
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Rinitha-Rajendran/Organ-Donation-System.git
   cd Organ-Donation-System
   ```

2. **Database Setup**:
   - Install PostgreSQL and create a database named 'organ_donation' and database tables named `donor, Transportationsystem, recipient, organ_data, OrganRegistration` with appropriate attribute/column names as observed in the code.
   - Execute the SQL scripts provided in the  directory to create the necessary tables.

3. **Configure Database Connection**:
   - Update the `db.properties` file with your PostgreSQL database credentials.

4. **Open Project in IntelliJ IDEA**:
   - Open IntelliJ IDEA.
   - Click on `File` -> `Open` and select the project directory.
   - Wait for IntelliJ IDEA to index the project and download dependencies.

5. **Run the Application**:
   - Locate `Main.java` in the `src` directory.
   - Right-click on `Main.java` and select `Run 'Main'`.

6. **View Changes in PostgreSQL**:
   - Open your PostgreSQL client (e.g., pgAdmin).
   - Connect to the `organ_donation` database.
   - Monitor the changes in the tables as you interact with the application.

## Usage
- **Donors**: Register and log in to the system, add your organ donation details, and receive notifications when a match is found.
- **Recipients**: Register and log in to the system, specify the organ you need, and get notified when a suitable donor is found.
- **Administrators**: Use the admin dashboard to manage the system, view donor and recipient lists, and monitor the donation process.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure that your code adheres to the project's coding standards and includes appropriate tests.
