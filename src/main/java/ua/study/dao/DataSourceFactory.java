package ua.study.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.ds.PGConnectionPoolDataSource;

/**
 * Created by dima on 28.03.17.
 */
public abstract class DataSourceFactory {

    private static final Logger LOGGER = LogManager.getLogger(DataSourceFactory.class);


    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://127.0.0.1/hoteldb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1";

    private static final PGConnectionPoolDataSource dataSource = new PGConnectionPoolDataSource();

    private DataSourceFactory(){}

    public static PGConnectionPoolDataSource getDataSource(){
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        dataSource.setUrl(URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}
