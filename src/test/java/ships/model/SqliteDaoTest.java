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

import org.mockito.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
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
    public void shouldAddNewScoreToDb() throws SQLException {
        //given
        String nickname = "testowy";
        Integer score = 123;
        when(connection.createStatement()).thenReturn(statement);
        when(statement.execute((String) any())).thenReturn(true);
        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        //when
        sut.addNewScore(nickname, score);
        verify(statement).execute(sqlCaptor.capture());
        //then
        assertEquals("INSERT INTO "+SqliteDao.TABLE_NAME+" VALUES ('"+nickname+"',"+score+")", sqlCaptor.getValue());
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