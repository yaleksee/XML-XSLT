package magnit.Initial;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

import static magnit.Initial.DataBaseVariables.getPassword;
import static magnit.Initial.DataBaseVariables.getUsername;

public class JdbcConnection {
    /**
     * Метод Возвращает подключение к БД
     *
     * @return BasicDataSource
     */

    public static DataSource getDataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false");
        dataSource.setUsername(getUsername());
        dataSource.setPassword(getPassword());
        return dataSource;
    }
}
