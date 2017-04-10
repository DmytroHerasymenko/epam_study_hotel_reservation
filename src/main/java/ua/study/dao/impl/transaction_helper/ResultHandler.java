package ua.study.dao.impl.transaction_helper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dima on 28.03.17.
 */
public interface ResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
