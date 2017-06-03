package ships.model;

import javax.security.auth.Destroyable;
import java.sql.*;

public class SqliteDao implements Destroyable {

    Connection connection = null;
    private final static String TABLE_NAME = "HighScores";

    public SqliteDao() throws ClassNotFoundException, SQLException {
        ResultSet resultSet = null;
        Statement statement = null;
        connection = connect();
        initializeTable();
        statement = connection.createStatement();
        resultSet = statement
                .executeQuery("SELECT * FROM "+TABLE_NAME);
        while (resultSet.next())
        {
            System.out.println("NICK:"
                    + resultSet.getString("nickname"));
        }
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

    private void initializeTable() throws SQLException {
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

}
