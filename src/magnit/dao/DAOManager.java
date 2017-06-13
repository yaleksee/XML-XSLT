package magnit.dao;

import magnit.Initial.JdbcConnection;
import magnit.pojo.Entry;
import magnit.Initial.Variables;
import java.sql.*;
import java.util.ArrayList;

public class DAOManager {
    public static final String INSERT_STATEMENT = "INSERT INTO test ( field ) VALUES ( ? )";
    public static final String DELETE_STATEMENT = "DELETE FROM test";
    public static final String SELECT_STATEMENT = "SELECT field FROM test ORDER BY field";

    /**
     *  Подключение к БД
     *  @return Connection
     *  @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        Connection connection = JdbcConnection.getDataSource().getConnection();
        return connection;
    }

    /**
     *  Метод отчистки БД от всех данных
     *  @throws DAOException
     */
    public void deleteAllFieldsFromDatabase() throws DAOException {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     *  Получение данных из БД
     *  @return ArrayList<Entry>
     *  @throws DAOException
     */
    public ArrayList<Entry> selectFieldsFromDataBase() throws DAOException {
        ArrayList<Entry> entries = new ArrayList<>();
        try (Statement stmt = getConnection().createStatement()) {
            ResultSet resultSet = stmt.executeQuery(SELECT_STATEMENT);
            while (resultSet.next()) {
                Entry entry = new Entry();
                entry.setField(resultSet.getInt(Variables.FIELD));
                entries.add(entry);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return entries;
    }

    /**
     *  Внесение данных в базу данных
     *  @throws DAOException
     */
    public void insertFieldsToDatabase(Integer from, Integer to) throws DAOException {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            /*
            /   Экземпляры PreparedStatement "помнят" скомпилированные SQL-выражения.
                Именно поэтому они называются "prepared" ("подготовленные").
                SQL-выражения в PreparedStatement могут иметь один или более входной (IN) параметр.
                Входной параметр - это параметр, чье значение не указывается при создании SQL-выражения.
             */
            try (PreparedStatement prepStmt = conn.prepareStatement(INSERT_STATEMENT)) {
                for (int i = from+1; i <= to; i++) {
                    prepStmt.setInt(1, i);
                    prepStmt.addBatch();
                }
                prepStmt.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
