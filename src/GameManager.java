/**
 * Kaveen Amin
 * CEN 3024C - Software Development I CRN: 23586
 * Date: April 6, 2026
 * Class: GameManager
 *
 * This class manages the collection of Game objects in the Video Game Manager
 * application. It handles loading games from a text file, saving games back to
 * the file, adding new games, removing games, searching for games, updating
 * game information, and generating reports such as the backlog report.
 */



import java.util.*;
import java.io.*;
import java.sql.*;

public class GameManager {

    private final List<Game> games;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public GameManager(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        games = new ArrayList<>();
        loadGames();
    }

    /**
     * method: getConnection
     * purpose: creates a database connection for the current MySQL configuration
     * parameters: none
     * return: Connection - an active JDBC connection to the video game database
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    /**
     * method: testConnection
     * purpose: verifies that the application can connect to the configured database
     * parameters: none
     * return: boolean - true if the database connection succeeds, otherwise false
     */
    public boolean testConnection() {
        try (Connection ignored = getConnection()) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * method: reloadGames
     * purpose: clears the in-memory list and reloads all game records from the database
     * parameters: none
     * return: boolean - true if the reload was successful, otherwise false
     */
    public boolean reloadGames() {
        games.clear();
        return loadGames();
    }

    /**
     * method: loadGames
     * purpose: reads the game data from the database and stores the records in memory ordered by id
     * parameters: none
     * return: boolean - true if the records were loaded successfully, otherwise false
     */
    private boolean loadGames() {
        String sql = "SELECT id, title, platform, genre, price, hours_played, completed, release_year FROM games ORDER BY id";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            games.clear();

            while (resultSet.next()) {
                Game g = new Game(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("platform"),
                        resultSet.getString("genre"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("hours_played"),
                        resultSet.getBoolean("completed"),
                        resultSet.getInt("release_year")
                );

                games.add(g);
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error loading database records.");
            return false;
        }
    }

    /**
     * method: getAllGames
     * purpose: returns a copy of all games currently stored in memory
     * parameters: none
     * return: List<Game> - a list containing all stored game objects
     */
    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }

    /**
     * method: getNextAvailableId
     * purpose: finds the smallest missing positive id so deleted id values can be reused before creating a new id at the end
     * parameters: none
     * return: int - the next available id for a new game record
     */
    public int getNextAvailableId() {
        int expectedId = 1;

        for (Game g : games) {
            if (g.getGameId() == expectedId) {
                expectedId++;
            } else if (g.getGameId() > expectedId) {
                return expectedId;
            }
        }

        return expectedId;
    }

    /**
     * method: addGame
     * purpose: adds a new game to the database if its ID does not already exist
     * parameters: game - the game object to be added
     * return: boolean - true if the game was added successfully, otherwise false
     */
    public boolean addGame(Game game) {
        if (findGame(game.getGameId()) != null) {
            return false;
        }

        String sql = "INSERT INTO games (id, title, platform, genre, price, hours_played, completed, release_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, game.getGameId());
            statement.setString(2, game.getTitle());
            statement.setString(3, game.getPlatform());
            statement.setString(4, game.getGenre());
            statement.setDouble(5, game.getPurchasePrice());
            statement.setDouble(6, game.getHoursPlayed());
            statement.setBoolean(7, game.isCompleted());
            statement.setInt(8, game.getReleaseYear());

            if (statement.executeUpdate() != 1) {
                return false;
            }

            return reloadGames();

        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * method: removeGame
     * purpose: removes a game from the database using its ID
     * parameters: id - the ID of the game to remove
     * return: boolean - true if the game was removed successfully, otherwise false
     */
    public boolean removeGame(int id) {
        String sql = "DELETE FROM games WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            if (statement.executeUpdate() != 1) {
                return false;
            }

            return reloadGames();

        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * method: updateGameId
     * purpose: updates the id of an existing game record while preventing duplicate ids in the database
     * parameters: currentId - the current game id, newId - the replacement id value
     * return: boolean - true if the id was updated successfully, otherwise false
     */
    public boolean updateGameId(int currentId, int newId) {
        if (currentId == newId) {
            return true;
        }

        Game game = findGame(currentId);

        if (game == null || newId <= 0) {
            return false;
        }

        if (findGame(newId) != null) {
            return false;
        }

        String sql = "UPDATE games SET id = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newId);
            statement.setInt(2, currentId);

            if (statement.executeUpdate() != 1) {
                return false;
            }

            return reloadGames();

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateGameTitle(int id, String newTitle) {
        if (newTitle == null || newTitle.trim().isEmpty()) {
            return false;
        }
        return updateColumnValue(id, "title", newTitle.trim());
    }


    public boolean updateGamePlatform(int id, String newPlatform) {
        if (newPlatform == null || newPlatform.trim().isEmpty()) {
            return false;
        }
        return updateColumnValue(id, "platform", newPlatform.trim());
    }

    public boolean updateGameGenre(int id, String newGenre) {
        if (newGenre == null || newGenre.trim().isEmpty()) {
            return false;
        }
        return updateColumnValue(id, "genre", newGenre.trim());
    }

    public boolean updateGamePrice(int id, double newPrice) {
        if (newPrice < 0) {
            return false;
        }
        return updateColumnValue(id, "price", newPrice);
    }

    public boolean updateGameHours(int id, double newHours) {
        if (newHours < 0) {
            return false;
        }
        return updateColumnValue(id, "hours_played", newHours);
    }

    public boolean updateGameCompleted(int id, boolean status) {
        return updateColumnValue(id, "completed", status);
    }

    public boolean updateGameYear(int id, int newYear) {
        if (newYear < 1950 || newYear > 2100) {
            return false;
        }
        return updateColumnValue(id, "release_year", newYear);
    }

    /**
     * method: updateColumnValue
     * purpose: updates one database column for a single game record and refreshes the in-memory list after the change
     * parameters: id - the id of the game to update, columnName - the target database column, value - the replacement value
     * return: boolean - true if the column update was successful, otherwise false
     */
    private boolean updateColumnValue(int id, String columnName, Object value) {
        if (findGame(id) == null) {
            return false;
        }

        String sql = "UPDATE games SET " + columnName + " = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, value);
            statement.setInt(2, id);

            if (statement.executeUpdate() != 1) {
                return false;
            }

            return reloadGames();

        } catch (SQLException e) {
            return false;
        }
    }

    public Game findGame(int id) {
        for (Game g : games) {
            if (g.getGameId() == id) {
                return g;
            }
        }

        return null;
    }

    /**
     * method: backlogReport
     * purpose: creates a list of all games that are not yet completed
     * parameters: none
     * return: List<Game> - a list of unfinished games
     */
    public List<Game> backlogReport() {
        List<Game> backlog = new ArrayList<>();

        for (Game g : games) {
            if (!g.isCompleted()) {
                backlog.add(g);
            }
        }

        return backlog;
    }

    /**
     * method: exportBacklog
     * purpose: exports the backlog report to the selected file path and format
     * parameters: exportPath - the destination file path, format - the export file format such as csv or txt
     * return: boolean - true if the export was successful, otherwise false
     */
    public boolean exportBacklog(String exportPath, String format) {
        List<Game> backlog = backlogReport();
        String normalizedFormat = format == null ? "" : format.trim().toLowerCase();

        try (PrintWriter writer = new PrintWriter(exportPath)) {
            if ("csv".equals(normalizedFormat)) {
                writer.println("Game ID,Title,Platform,Genre,Purchase Price,Hours Played,Completed,Release Year");
                for (Game g : backlog) {
                    writer.printf(Locale.US, "%d,%s,%s,%s,%.2f,%.2f,%b,%d%n",
                            g.getGameId(), g.getTitle(), g.getPlatform(), g.getGenre(),
                            g.getPurchasePrice(), g.getHoursPlayed(), g.isCompleted(), g.getReleaseYear());
                }
                return true;
            }

            if ("txt".equals(normalizedFormat)) {
                for (Game g : backlog) {
                    writer.println(g.toString());
                }
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
