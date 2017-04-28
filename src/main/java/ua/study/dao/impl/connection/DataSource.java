package ua.study.dao.impl.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dima on 28.03.17.
 */
public class DataSource {
    private static final DataSource instance = new DataSource();
    private final PGConnectionPoolDataSource dataSource = new PGConnectionPoolDataSource();
    private final Logger LOGGER = LogManager.getLogger(DataSource.class.getName());

    private DataSource(){
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/db.properties"));
            Class.forName(properties.getProperty("db.driver"));
            dataSource.setUrl(properties.getProperty("db.url"));
            dataSource.setUser(properties.getProperty("db.user"));
            dataSource.setPassword(properties.getProperty("db.password"));
        }  catch (IOException e) {
            LOGGER.error("some problem with reading properties file ", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("some problem with loading sql driver ", e);
        }
    }

    public static DataSource getInstance(){
        return instance;
    }

    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("some problem with database connection ", e);
            return null;
        }
    }
}