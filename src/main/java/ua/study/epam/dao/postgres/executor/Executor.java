package ua.study.epam.dao.postgres.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.epam.domain.Reservation;
import ua.study.epam.domain.ReservedRoom;
import ua.study.epam.domain.User;

import java.sql.*;
import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class Executor {
    private static final Logger LOGGER = LogManager.getLogger(Executor.class);

    public boolean executorUpdate(Connection connection, String update) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(update);
            statement.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public <T> T executorQuery(Connection connection, String query, ResultHandler<T> handler) {
        T value = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            value = handler.handle(result);
            result.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return value;
    }

    public <T> T getUser(Connection connection, String query, String login,
                         ResultHandler<T> handler) {
        T value = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            value = handler.handle(result);
            result.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return value;
    }

    public <T> T verifyUser(Connection connection, String query, String login,
                            String password, ResultHandler<T> handler) {
        T value = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            value = handler.handle(result);
            result.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return value;
    }

    public Long reservationInsert(Connection connection, Reservation domain, String update){
        Long l;
        try(PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setLong(1, domain.getClientId());
            preparedStatement.setTimestamp(2,
                    Timestamp.valueOf(domain.getArrivingDate().atStartOfDay()));
            preparedStatement.setTimestamp(3,
                    Timestamp.valueOf(domain.getDepartureDate().atStartOfDay()));
            preparedStatement.setString(4, domain.getStatus().name());
            preparedStatement.execute();
            preparedStatement.getResultSet().next();
            l = preparedStatement.getResultSet().getLong(1);
            return l;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public boolean reservedRoomInsert(Connection connection,
                                      List<ReservedRoom> reservedRooms, String update){
        try(PreparedStatement statement = connection.prepareStatement(update)) {
            for(ReservedRoom reservedRoom : reservedRooms){
                statement.setLong(1, reservedRoom.getReservationId());
                statement.setLong(2, reservedRoom.getRoomTypeId());
                statement.addBatch();
            }
            statement.executeBatch();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean userInsert(Connection connection, User domain, String update){
        try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setString(1, domain.getName());
            preparedStatement.setString(2, domain.getLogin());
            preparedStatement.setString(3, domain.getPassword());
            preparedStatement.setString(4, domain.getUserRole().name());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
}