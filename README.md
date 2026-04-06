# data_management_system_VideoGameCollectionManager_Phase4-GUI-Database

## Overview
The Video Game Manager is a Java GUI-based application that allows users to manage a collection of video games using a MySQL relational database for storage. The program supports connecting to a database, displaying records in a table, adding new games, updating existing games, deleting games, and keeping the collection organized through a custom ID system.

This project was developed for **CEN 3024C – Software Development I** as part of the **DMS Project Phase 4** assignment.

---

# Features
- Connect to a MySQL database using JDBC
- Display all stored game records in a GUI table
- Add a new game through the interface
- Update an existing game record
- Delete a game by ID
- Automatically reuse missing ID numbers
- Display records in sorted ID order
- Synchronize all changes directly with the database

---

# Game Data Stored
Each game record contains:
- Game ID
- Title
- Platform
- Genre
- Purchase Price
- Hours Played
- Completion Status
- Release Year

---

# Project Files
- `Game.java` – represents a video game object
- `GameManager.java` – manages game records and database operations
- `VGMApp.java` – contains the main GUI and program flow
- `videogamesdb` – MySQL database used by the application
- `games` – table that stores all video game records

---

# Improvements in Phase 4
This phase focused on moving the application from text file storage to a relational database and implementing a graphical user interface.

### Changes made:
- Replaced text file storage with MySQL database storage
- Added JDBC connection support
- Created a GUI for interacting with the application
- Added table-based display for all games
- Enabled add, update, and delete operations directly in the database
- Implemented custom logic to reuse missing ID values
- Sorted records by ID for cleaner display and organization

---

# Technologies Used
- Java
- Swing
- MySQL
- JDBC
- IntelliJ IDEA

---

# Database Structure
The application uses a MySQL database named `videogamesdb` and a table named `games`.

```sql
CREATE DATABASE videogamesdb;

USE videogamesdb;

CREATE TABLE games (
    id INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    platform VARCHAR(50) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    price DECIMAL(6,2) NOT NULL,
    hours_played DECIMAL(6,1) NOT NULL,
    completed BOOLEAN NOT NULL,
    release_year INT NOT NULL
);
```

---

# Sample Data
The following sample records can be inserted into the database:

```sql
INSERT INTO games (id, title, platform, genre, price, hours_played, completed, release_year) VALUES
(1, 'God Of War: Ragnarok', 'PS5', 'Action-Adventure', 59.99, 120.0, true, 2025),
(2, 'Elden Ring', 'PC', 'RPG', 59.99, 95.0, true, 2022),
(3, 'Minecraft', 'PC', 'Sandbox', 26.95, 300.0, false, 2011),
(4, 'God of War', 'PlayStation 4', 'Action', 49.99, 45.0, true, 2018),
(5, 'Halo Infinite', 'Xbox Series X', 'Shooter', 59.99, 60.0, false, 2021),
(6, 'Stardew Valley', 'PC', 'Simulation', 14.99, 150.0, false, 2016),
(7, 'Red Dead Redemption 2', 'PlayStation 4', 'Action-Adventure', 59.99, 110.0, true, 2018),
(8, 'Cyberpunk 2077', 'PC', 'RPG', 59.99, 70.0, false, 2020),
(9, 'The Witcher 3', 'PC', 'RPG', 39.99, 140.0, true, 2015),
(10, 'Grand Theft Auto V', 'PC', 'Action', 29.99, 200.0, false, 2013),
(11, 'Super Mario Odyssey', 'Nintendo Switch', 'Platformer', 59.99, 40.0, true, 2017),
(12, 'Fortnite', 'PC', 'Battle Royale', 0.00, 220.0, false, 2017),
(13, 'Assassin’s Creed Valhalla', 'Xbox Series X', 'Action-Adventure', 59.99, 85.0, true, 2020),
(14, 'Uncharted', 'PC', 'Action-Adventure', 20.00, 70.0, true, 2016),
(15, 'League of Legends', 'PC', 'MOBA', 0.00, 500.0, false, 2009),
(16, 'Call of Duty: Modern Warfare II', 'PlayStation 5', 'Shooter', 69.99, 75.0, false, 2022),
(17, 'Resident Evil Village', 'PlayStation 5', 'Survival Horror', 59.99, 25.0, true, 2021),
(18, 'Terraria', 'PC', 'Sandbox', 9.99, 180.0, false, 2011),
(19, 'Monster Hunter World', 'PC', 'Action RPG', 29.99, 130.0, false, 2018),
(20, 'Warframe', 'PC', 'RPG Action', 0.00, 350.0, false, 2013);
```

---

# Custom ID System
This application does not use MySQL `AUTO_INCREMENT`.

Instead, the program uses a custom ID assignment system:

- If there is a missing ID in the sequence, the program assigns the smallest missing ID to the next new game
- If there are no missing IDs, the program assigns the next highest ID automatically

### Example:
If the IDs in the database are:

```text
1, 2, 3, 5, 6
```

The next new game will receive ID `4`.

If the IDs in the database are:

```text
1, 2, 3, 4, 5
```

The next new game will receive ID `6`.

This approach keeps the game collection organized and prevents unnecessary gaps in the record list.

---

# How to Run
1. Open the project in **IntelliJ IDEA**
2. Make sure **MySQL Server** is installed and running
3. Create the `videogamesdb` database
4. Create the `games` table
5. Insert the sample data into the table
6. Add the **MySQL Connector/J** library to the project
7. Run `VGMApp.java`
8. Enter the database connection information when prompted
9. Use the GUI buttons to manage game records

---

# MySQL Connector/J Setup
This project requires the MySQL JDBC driver so Java can connect to the database.

### Steps:
1. Download **MySQL Connector/J**
2. Open the project in IntelliJ IDEA
3. Go to **File > Project Structure**
4. Select **Modules**
5. Open the **Dependencies** tab
6. Click the **+** button
7. Choose **JARs or directories**
8. Select the MySQL Connector/J `.jar` file
9. Apply the changes and click **OK**

---

# Database Verification
To confirm that the application is correctly updating the database, run the following query in MySQL:

```sql
USE videogamesdb;
SELECT * FROM games ORDER BY id;
```

This can be used to verify that add, update, and delete operations performed in the GUI are also reflected in the database.

---

# Program Behavior
The application is designed so that:

- Records are loaded from MySQL into the GUI table
- New games are inserted directly into the database
- Updated games modify the existing database records
- Deleted games are removed from the database
- Missing ID values are reused when possible
- All records are displayed in ascending ID order

---

# Purpose of the Project
The purpose of this project is to demonstrate how a Java desktop application can connect to a relational database and perform CRUD operations through a user-friendly graphical interface.

---

# Author
**Your Name Here**
