# Hostel Booking and Management System

Welcome to the Hostel Booking and Management System project! This system is developed using Java and JavaFX, with MySQL as the database, providing a comprehensive hostel management solution for both residents and hostel owners.

## Project Overview
The Hostel Booking and Management System allows residents to book hostels, search based on various criteria, compare hostels, provide feedback, and more. Hostel owners can create and manage hostel advertisements, handle bookings, allocate rooms, and manage leave requests and complaints.

## Features

### Resident Features
- Hostel booking and searching
- Comparison by price, facilities, and rooms
- Feedback and ratings
- Leave requests
- Complaints and maintenance requests

### Hostel Owner Features
- Create and manage hostel advertisements
- Manage hostel information
- Manage student bookings and feedback
- Room allocation and visibility management
- Manage leave requests
- Respond to complaints

## Technologies Used
- **Backend:** Java
- **Frontend:** JavaFX
- **Database:** MySQL
- **IDE:** Eclipse

## Prerequisites
Ensure you have the following prerequisites installed:
- Java Development Kit (JDK): [Download JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- Eclipse IDE: [Download Eclipse](https://www.eclipse.org/downloads/)
- JavaFX SDK: [Download JavaFX](https://openjfx.io/)
- MySQL: [Download MySQL](https://dev.mysql.com/downloads/)
- MySQL Workbench: [Download MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- Scene Builder: [Download Scene Builder](https://gluonhq.com/products/scene-builder/)

## Getting Started
Follow these steps to set up and run the project on your local machine:

1. **Clone the Repository**
    ```bash
    git clone https://github.com/yourusername/hostel-booking-management-system.git
    cd hostel-booking-management-system
    ```

2. **Set Up the Database**
    - Open MySQL Workbench and create a new database. Import the provided SQL script (`SQL_Tables.sql`) to set up the required tables.

3. **Configure Database Connection**
    - Open the project in Eclipse IDE. Navigate to the database connection configuration file (`PersistenceHandler.java`). Update the following lines of code with your MySQL credentials:
    ```java
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_hms";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    ```

4. **Set Up JavaFX in Eclipse**
    - Download the JavaFX SDK from the [official website](https://openjfx.io/). Extract the downloaded file to a convenient location on your system. Configure JavaFX in Eclipse:
        - Go to `Window` -> `Preferences` -> `Java` -> `Build Path` -> `User Libraries`.
        - Click `New` and name it `JavaFX`.
        - Click `Add External JARs` and navigate to the `lib` folder of your extracted JavaFX SDK. Select all JAR files.
        - Click `OK` to add the library.

5. **Set Up Scene Builder**
    - Download Scene Builder from the [official website](https://gluonhq.com/products/scene-builder/). Install Scene Builder by following the instructions for your operating system. Link Scene Builder to Eclipse:
        - In Eclipse, go to `Window` -> `Preferences` -> `JavaFX`.
        - Browse to the location where you installed Scene Builder and select the executable file.

6. **Add Required JAR Files**
    - Download the required JAR files:
        - Font Awesome icon JAR: [Download](https://jar-download.com/artifacts/de.jensd/fontawesomefx/8.9)
        - ControlFX JAR: [Download](https://jar-download.com/artifacts/org.controlsfx/controlsfx/11.1.0)
        - MySQL Connector JAR: [Download](https://dev.mysql.com/downloads/connector/j/8.0.html)
    - Add these JAR files to your project's build path:
        - Right-click on the project in Eclipse.
        - Go to `Build Path` -> `Configure Build Path`.
        - Click `Add External JARs` and add the downloaded JAR files.

7. **Configure JavaFX Run Configuration**
    - Right-click your project and select `Run As` -> `Run Configurations`. Select your Java Application configuration. Navigate to the `Arguments` tab. Under VM arguments, add:
    ```css
    --module-path "path-to-javafx-sdk-lib" --add-modules javafx.controls,javafx.fxml
    ```
    Replace `"path-to-javafx-sdk-lib"` with the actual path to your JavaFX SDK lib directory. Click `Apply`.

8. **Run the Project**
    - Ensure all the dependencies are correctly added. Right-click the main class file (containing the main method). Select `Run As` -> `Java Application`.

## Contact
Feel free to reach out if you have any questions or suggestions!
- LinkedIn: [Hassan Rehman](https://www.linkedin.com/in/hassan-gill-41179a2a1/)
- Email: [hassangill9393@gmail.com](mailto:hassangill9393@gmail.com)
