package ua.study.epam;

import ua.study.epam.dao.DaoFactory;
import ua.study.epam.dao.postgres.*;
import ua.study.epam.dao.postgres.transaction_helper.TransactionHelper;
import ua.study.epam.domain.Room;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static ua.study.epam.dao.DaoFactory.DaoFactoryType.POSTGRES;

/**
 * Created by dima on 28.03.17.
 */
public class Main {
    public static void main(String[] args) {
        /*TransactionHelper transactionHelper = new TransactionHelper();
        DaoFactory daoFactory = DaoFactory.getInstance(transactionHelper, POSTGRES);
        BillDaoPostgres billDao = daoFactory.getDao("BillDao", BillDaoPostgres.class);
        ReservationDaoPostgres reservationDao = daoFactory.getDao("ReservationDao", ReservationDaoPostgres.class);
        ReservedRoomDaoPostgres reservedRoomDao = daoFactory.getDao("ReservedRoomDao", ReservedRoomDaoPostgres.class);
        RoomDaoPostgres roomDao = daoFactory.getDao("RoomDao", RoomDaoPostgres.class);
        RoomTypeDaoPostgres roomTypeDao = daoFactory.getDao("RoomTypeDao", RoomTypeDaoPostgres.class);
        UserDaoPostgres userDao = daoFactory.getDao("UserDao", UserDaoPostgres.class);

        userDao.createTable();
        reservationDao.createTable();
        billDao.createTable();
        roomTypeDao.createTable();
        roomDao.createTable();
        reservedRoomDao.createTable();*/

        //DELETE
        /*public void createTable() {

            String createTableSql = "CREATE TABLE IF NOT EXISTS bills("
                    + "bill_id SERIAL PRIMARY KEY NOT NULL, "
                    + "reservation_id INTEGER REFERENCES reservations(reservation_id) UNIQUE NOT NULL, "
                    + "billing_date TIMESTAMP CONSTRAINT bill_date DEFAULT CURRENT_TIMESTAMP NOT NULL, "
                    + "total_price INTEGER NOT NULL)";

            getExecutor().executorUpdate(getConnectionProxy().getConnection(), createTableSql);
        }

        //DELETE
        public void createTable() {

            String createTableSql = "CREATE TABLE IF NOT EXISTS reservations("
                    + "reservation_id SERIAL PRIMARY KEY NOT NULL, "
                    + "client_id INTEGER REFERENCES users(user_id) NOT NULL, "
                    + "reservation_date TIMESTAMP CONSTRAINT reserv_date DEFAULT CURRENT_TIMESTAMP NOT NULL, "
                    + "arriving_date TIMESTAMP NOT NULL, "
                    + "departure_date TIMESTAMP NOT NULL, "
                    + "status RESERVATION_STATUS NOT NULL, "
                    + "administrator_id INTEGER REFERENCES users(user_id))";

            String enumSql = "CREATE TYPE reservation_status AS ENUM ( 'NEW', 'CONFIRMED', 'REJECTED')";

            //getExecutor().executorUpdate(getTransactionHelper().getConnection(), enumSql);
            getExecutor().executorUpdate(getConnectionProxy().getConnection(), createTableSql);
        }

        //DELETE
        public void createTable() {

            String createTableSql = "CREATE TABLE IF NOT EXISTS reserved_rooms("
                    + "reserved_room_id SERIAL PRIMARY KEY NOT NULL, "
                    + "reservation_id INTEGER REFERENCES reservations(reservation_id) NOT NULL, "
                    + "room_type_id INTEGER REFERENCES room_types(room_type_id) NOT NULL, "
                    + "room_number INTEGER REFERENCES rooms(room_number))";

            getExecutor().executorUpdate(getConnectionProxy().getConnection(), createTableSql);
        }

        //DELETE
        public void createTable() {

            String createTableSql = "CREATE TABLE IF NOT EXISTS rooms("
                    + "room_number INTEGER PRIMARY KEY NOT NULL, "
                    + "room_type_id INTEGER REFERENCES room_types(room_type_id) NOT NULL, "
                    + "balcony BOOLEAN NOT NULL)";

            getExecutor().executorUpdate(getConnectionProxy().getConnection(), createTableSql);
        }

    //DELETE
    public boolean insert(Room domain) {
        String insertTableSql = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement =
                     getConnectionProxy().getConnection().prepareStatement(insertTableSql)) {
            preparedStatement.setInt(1, domain.getRoomNumber());
            preparedStatement.setLong(2, domain.getRoomTypeId());
            preparedStatement.setBoolean(3, domain.isBalcony());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //DELETE
    public void createTable() {

        String createTableSql = "CREATE TABLE IF NOT EXISTS room_types("
                + "room_type_id SERIAL PRIMARY KEY NOT NULL, "
                + "room_category CATEGORY NOT NULL, "
                + "bedspace BED_SPACE NOT NULL, "
                + "price INTEGER NOT NULL)";

        String enumSql = "CREATE TYPE category AS ENUM ( 'STANDARD', 'SUITE', 'DELUXE')";
        String enumSql1 = "CREATE TYPE bed_space AS ENUM ( 'SINGLE', 'DOUBLE', 'TWIN')";

        String insert1 = "INSERT INTO room_types (room_category, bedspace, price) VALUES ('STANDARD', 'SINGLE', '130')";
        String insert2 = "INSERT INTO room_types (room_category, bedspace, price) VALUES ('STANDARD', 'DOUBLE', '140')";
        String insert3 = "INSERT INTO room_types (room_category, bedspace, price) VALUES ('STANDARD', 'TWIN', '140')";
        String insert4 = "INSERT INTO room_types (room_category, bedspace, price) VALUES ('SUITE', 'DOUBLE', '260')";
        String insert5 = "INSERT INTO room_types (room_category, bedspace, price) VALUES ('SUITE', 'TWIN', '260')";
        String insert6 = "INSERT INTO room_types (room_category, bedspace, price) VALUES ('DELUXE', 'DOUBLE', '370')";

        //getExecutor().executorUpdate(getTransactionHelper().getConnection(), enumSql);
        //getExecutor().executorUpdate(getTransactionHelper().getConnection(), enumSql1);
        getExecutor().executorUpdate(getConnectionProxy().getConnection(), insert1);
        getExecutor().executorUpdate(getConnectionProxy().getConnection(), insert2);
        getExecutor().executorUpdate(getConnectionProxy().getConnection(), insert3);
        getExecutor().executorUpdate(getConnectionProxy().getConnection(), insert4);
        getExecutor().executorUpdate(getConnectionProxy().getConnection(), insert5);
        getExecutor().executorUpdate(getConnectionProxy().getConnection(), insert6);
        getExecutor().executorUpdate(getConnectionProxy().getConnection(), createTableSql);
    }

    //DELETE
    public void createTable() {

        String createTableSql = "CREATE TABLE IF NOT EXISTS users("
                + "user_id SERIAL PRIMARY KEY NOT NULL, "
                + "name VARCHAR(30) NOT NULL, "
                + "login VARCHAR(30) UNIQUE NOT NULL, "
                + "password VARCHAR(20) NOT NULL, "
                + "user_role ROLE NOT NULL)";

        String enumSql = "CREATE TYPE role AS ENUM ( 'USER', 'ADMIN')";

        //getExecutor().executorUpdate(getTransactionHelper().getConnection(), enumSql);
        getExecutor().executorUpdate(getConnectionProxy().getConnection(), createTableSql);
    }*/
    }
}