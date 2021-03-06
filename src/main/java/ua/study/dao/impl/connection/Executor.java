package ua.study.dao.impl.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.domain.*;

import java.sql.*;
import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class Executor {
    private static final Logger LOGGER = LogManager.getLogger(Executor.class.getName());

    public boolean executorUpdate(String update) {
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        Statement statement = connectionProxy.createStatement()) {
            statement.execute(update);
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public <T> T executorQuery(String query, ResultHandler<T> handler) {
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        Statement statement = connectionProxy.createStatement()) {
            ResultSet result = statement.executeQuery(query);
            T value = handler.handle(result);
            result.close();
            return value;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException("some problem with execute query", e);
        }
    }

    public boolean insertUser(User domain, String query){
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        PreparedStatement statement = connectionProxy.prepareStatement(query)) {
            statement.setString(1, domain.getName());
            statement.setString(2, domain.getLogin());
            statement.setString(3, domain.getPassword());
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean insertReservedRoom(Dates dates, List<ReservedRoom> reservedRooms, String query){
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        PreparedStatement statement = connectionProxy.prepareStatement(query)) {
            for(ReservedRoom reservedRoom : reservedRooms){
                setTimestamp(dates, statement);
                statement.setInt(7, reservedRoom.getRoomTypeId());
                statement.setLong(8, reservedRoom.getReservationId());
                statement.setInt(9, reservedRoom.getRoomTypeId());
                statement.addBatch();
            }
            statement.executeBatch();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean insertBill(Bill domain, String query){
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        PreparedStatement statement = connectionProxy.prepareStatement(query)) {
            statement.setLong(1, domain.getReservationId());
            statement.setDouble(2, domain.getTotalPrice());
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException("some problem with execute query", e);
        }
    }

    public Long insertReservation(Reservation domain, String query){
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        PreparedStatement statement = connectionProxy.prepareStatement(query)) {
            statement.setString(1, domain.getClientLogin());
            statement.setTimestamp(2, Timestamp.valueOf(domain.getArrivingDate().atStartOfDay()));
            statement.setTimestamp(3, Timestamp.valueOf(domain.getDepartureDate().atStartOfDay()));
            statement.execute();
            statement.getResultSet().next();
            Long l = statement.getResultSet().getLong(1);
            return l;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException("some problem with execute query", e);
        }
    }

    public <T> T getByLogin(String query, User domain, ResultHandler<T> handler) {
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        PreparedStatement statement = connectionProxy.prepareStatement(query)) {
            statement.setString(1, domain.getLogin());
            ResultSet result = statement.executeQuery();
            T value = handler.handle(result);
            result.close();
            return value;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException("some problem with execute query", e);
        }
    }

    public <T> T getUserReservedRoom(String query, Long[] reservationsId, ResultHandler<T> handler){
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        PreparedStatement statement = connectionProxy.prepareStatement(query)) {
            statement.setArray(1, connectionProxy.createArrayOf("BIGINT", reservationsId));
            ResultSet result = statement.executeQuery();
            T value = handler.handle(result);
            result.close();
            return value;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException("some problem with execute query", e);
        }
    }

    public <T> T getReservedRoom(String query, Reservation domain, ResultHandler<T> handler) {
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        PreparedStatement statement = connectionProxy.prepareStatement(query)) {
            statement.setLong(1, domain.getReservationId());
            ResultSet result = statement.executeQuery();
            T value = handler.handle(result);
            result.close();
            return value;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException("some problem with execute query", e);
        }
    }

    public <T> T verifyUser(String query, User domain, ResultHandler<T> handler) {
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        PreparedStatement statement = connectionProxy.prepareStatement(query)) {
            statement.setString(1, domain.getLogin());
            statement.setString(2, domain.getPassword());
            ResultSet result = statement.executeQuery();
            T value = handler.handle(result);
            result.close();
            return value;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException("some problem with execute query", e);
        }
    }

    public <T> T getFreeRoomTypes(String query, Dates domain, ResultHandler<T> handler) {
        try (ConnectionProxy connectionProxy = TransactionHelper.getInstance().getConnection();
        PreparedStatement statement = connectionProxy.prepareStatement(query)) {
            setTimestamp(domain, statement);
            ResultSet result = statement.executeQuery();
            T value = handler.handle(result);
            result.close();
            return value;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException("some problem with execute query", e);
        }
    }

    private void setTimestamp(Dates domain, PreparedStatement statement) throws SQLException {
        Timestamp arrive = Timestamp.valueOf(domain.getArrivingDate().atStartOfDay());
        Timestamp departure = Timestamp.valueOf(domain.getDepartureDate().atStartOfDay());
        statement.setTimestamp(1, arrive);
        statement.setTimestamp(2, arrive);
        statement.setTimestamp(3, departure);
        statement.setTimestamp(4, departure);
        statement.setTimestamp(5, arrive);
        statement.setTimestamp(6, departure);
    }
}