# data_management_system_VideoGameCollectionManager_Phase4-DB

Video Game Manager

A Java GUI application for managing a video game collection using a MySQL database. This project allows users to add, update, delete, and view video game records through a desktop interface.

Features
Connects to a MySQL database
Displays all game records in a GUI table
Adds new games
Updates existing games
Deletes games
Reuses missing ID numbers automatically
Sorts records by ID
Technologies Used
Java
Swing
MySQL
JDBC
IntelliJ IDEA
Database Information

Database name: videogamesdb
Table name: games

Table Structure
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
Sample Data
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
How the ID System Works

This project does not use MySQL AUTO_INCREMENT.

Instead, it uses custom logic to assign IDs:

If there is a missing ID in the list, the program uses the smallest missing ID
If there are no missing IDs, the program uses the next highest number
Example

If the database contains these IDs:

1, 2, 3, 5, 6

The next added game will receive ID 4.

If the database contains these IDs:

1, 2, 3, 4, 5

The next added game will receive ID 6.

This allows the application to keep the ID list organized without gaps whenever possible.

Project Files
Game.java - stores the data for each video game object
GameManager.java - handles database operations and manages the list of games
VGMApp.java - contains the GUI and user interaction logic
Requirements

Before running the program, make sure the following are installed and configured:

Java
MySQL Server
IntelliJ IDEA or another Java IDE
MySQL Connector/J JDBC driver
MySQL Connector/J Setup

The project requires the MySQL JDBC driver so Java can connect to MySQL.

Steps to add it in IntelliJ IDEA
Download MySQL Connector/J from the official MySQL website
Open the project in IntelliJ IDEA
Go to File > Project Structure
Click Modules
Open the Dependencies tab
Click the + button
Select JARs or directories
Choose the MySQL Connector/J .jar file
Apply the changes and click OK
How to Run the Program
Open the project in IntelliJ IDEA
Make sure MySQL Server is running
Create the videogamesdb database
Create the games table
Insert the sample data
Add MySQL Connector/J to the project
Run VGMApp.java
Enter your database connection information when prompted
Example JDBC URL
jdbc:mysql://localhost:3306/videogamesdb
CRUD Operations

The application supports the following database operations:

Create - add a new game to the database
Read - display all games in the GUI table
Update - modify an existing game record
Delete - remove a game from the database
Database Verification

To verify that the program is updating the database correctly, run the following SQL:

USE videogamesdb;
SELECT * FROM games ORDER BY id;

This shows all records currently stored in the database in ID order.

Program Behavior
The application connects to a relational database instead of a text file
All game records are loaded from MySQL into the GUI
Any add, update, or delete action in the app changes the MySQL database
Records are displayed in sorted ID order
Missing ID values are reused automatically when possible
Purpose of the Project

The purpose of this project is to demonstrate how a Java desktop application can connect to a relational database and perform CRUD operations using a graphical user interface.

Author

Kaveen Amin
