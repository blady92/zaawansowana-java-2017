package ships.model;

import javax.security.auth.Destroyable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteDao {

    Connection connection = null;
    private final static String TABLE_NAME = "HighScores";

    public SqliteDao() throws ClassNotFoundException, SQLException {
        ResultSet resultSet = null;
        Statement statement = null;
        connection = connect();
        initializeTable();
    }

    public SqliteDao(Connection connection) throws SQLException {
        ResultSet resultSet = null;
        Statement statement = null;
        this.connection = connection;
    }

    /**
     * Establishes connection to the database
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:highscores.db");
    }

    public void initializeTable() throws SQLException {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "  (nickname           VARCHAR(32),"
                + "   score INTEGER)";

        Statement stmt = connection.createStatement();
        stmt.execute(sqlCreate);
    }

    /**
     * Sends score to the database
     * @param nickname
     * @param score
     * @throws java.sql.SQLException
     */
    public void addNewScore(String nickname, Integer score) throws SQLException {
        String sqlCreate = "INSERT INTO "+TABLE_NAME+" VALUES ('"+nickname+"',"+score+")";
        Statement stmt = connection.createStatement();
        stmt.execute(sqlCreate);
    }

    /**
     * Get top scores to get
     * @param number number of scores to get
     * @return
     */
    public List<HighScore> getTopScores(int number) throws SQLException {
        List<HighScore> result = new ArrayList<>();
        ResultSet resultSet = null;
        Statement statement = null;
        statement = connection.createStatement();
        resultSet = statement
                .executeQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY score DESC LIMIT "+number+";");
        while (resultSet.next())
        {
            result.add(new HighScore(resultSet.getInt("score"), resultSet.getString("nickname")));
        }
        return result;
    }
}
