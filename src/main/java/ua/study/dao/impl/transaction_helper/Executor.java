package ua.study.dao.impl.transaction_helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.User;

import java.sql.*;
import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class Executor {
    private static final Logger LOGGER = LogManager.getLogger(Executor.class.getName());

    public boolean executorUpdate(String update) {
        try {
            Connection connection = TransactionHelper.getInstance().getConnection().getConnection();
            Statement statement = connection.createStatement();
            statement.execute(update);
            statement.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public <T> T executorQuery(String query, ResultHandler<T> handler) {
        T value = null;
        try {
            Connection connection = TransactionHelper.getInstance().getConnection().getConnection();
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

    public <T> T getUser(String query, String login, ResultHandler<T> handler) {
        T value = null;
        try {
            Connection connection = TransactionHelper.getInstance().getConnection().getConnection();
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

    public <T> T verifyUser(String query, User domain, ResultHandler<T> handler) {
        T value = null;
        try {
            Connection connection = TransactionHelper.getInstance().getConnection().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, domain.getLogin());
            statement.setString(2, domain.getPassword());
            ResultSet result = statement.executeQuery();
            value = handler.handle(result);
            result.close();
            statement.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return value;
    }

    public Long reservationInsert(Reservation domain, String update){
        Long l;
        try {
            Connection connection = TransactionHelper.getInstance().getConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(update);
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

    public boolean reservedRoomUpdate(List<ReservedRoom> reservedRooms, String update){
        try {
            Connection connection = TransactionHelper.getInstance().getConnection().getConnection();
            PreparedStatement statement = connection.prepareStatement(update);
            for(ReservedRoom reservedRoom : reservedRooms){
                statement.setInt(1, reservedRoom.getRoomNumber());
                statement.setLong(2, reservedRoom.getReservationId());
                statement.setLong(3, reservedRoom.getRoomTypeId());
                statement.addBatch();
            }
            statement.executeBatch();
            return true;
        } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                return false;
            }
        }

    public boolean reservedRoomInsert(List<ReservedRoom> reservedRooms, String update){
        try {
            Connection connection = TransactionHelper.getInstance().getConnection().getConnection();
            PreparedStatement statement = connection.prepareStatement(update);
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

    public boolean userInsert(User domain, String update){
        try {
            Connection connection = TransactionHelper.getInstance().getConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, domain.getName());
            preparedStatement.setString(2, domain.getLogin());
            preparedStatement.setString(3, domain.getPassword());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean roomInsert(String insert){
        try {
            Connection connection = TransactionHelper.getInstance().getConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, 201);
            preparedStatement.setLong(2, 1);
            preparedStatement.setBoolean(3, false);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
}