package ships.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

public class SqliteDaoTest {

    @Mock
    ResultSet resultSet;

    @Mock
    Connection connection;

    @Mock
    Statement statement;

    @InjectMocks
    SqliteDao sut;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        this.sut = new SqliteDao(connection);
    }

    @Test
    public void shouldReturnListOfHighScores() throws SQLException {
        //given
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery((String) any())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt((String)any())).thenReturn(123);
        when(resultSet.getString((String)any())).thenReturn("testowy");
        //when
        sut.initializeTable();
        List<HighScore> scores = sut.getTopScores(10);
        //then
        assertTrue(scores.size() == 1);
        assertEquals(new HighScore(123, "testowy"), scores.get(0));
    }

}