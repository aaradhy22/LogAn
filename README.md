# LogAn - A Log Analyzer & Management Tool

LogAn is a command-line utility built with Java that parses server log files, stores the structured data in a MySQL database, and generates simple daily reports.

This project demonstrates foundational backend skills including database interaction (JDBC), dependency management (Maven), file I/O, and XML parsing for configuration.

---

### Technologies Used
- **Java 8+**
- **Maven:** For dependency management and building the project.
- **MySQL:** As the database for storing structured log data.
- **JDBC:** For connecting Java to the MySQL database.
- **JAXP (XML Parsing):** For reading external configuration from `config.xml`.
- **JUnit:** For unit testing.

---

### Features
- Parses log files with a specific format: `[TIMESTAMP] [LEVEL] [SOURCE] - MESSAGE`
- Stores parsed log entries into a MySQL database.
- Externalizes database credentials and file paths into a `config.xml` file.
- Generates a console report on the number of `ERROR` logs for the most recent day in the database.

---

### Setup and Configuration

**1. Prerequisites:**
- Java JDK 8 or higher
- Apache Maven
- MySQL Server

**2. Database Setup:**
Run the following SQL script to create the database and table:
```sql
CREATE DATABASE logan_db;
USE logan_db;
CREATE TABLE logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    log_timestamp DATETIME NOT NULL,
    log_level VARCHAR(10) NOT NULL,
    source VARCHAR(255) NOT NULL,
    message TEXT,
    INDEX (log_level),
    INDEX (log_timestamp)
);
```

**3. Configure the Application:**
- Edit the `src/main/resources/config.xml` file to match your MySQL username and password.
- Place the log file you want to parse (e.g., `server.log`) in the root directory of the project.

---

### How to Run

1.  **Clone the repository:**
    ```bash
    git clone <your-repository-url>
    cd LogAn
    ```
2.  **Build the project using Maven:**
    ```bash
    mvn clean install
    ```
3.  **Run the application:**
    The `maven-jar-plugin` will create an executable JAR in the `target/` directory. You can run it from the command line.
    ```bash
    java -jar target/LogAn-0.0.1-SNAPSHOT.jar
    ```