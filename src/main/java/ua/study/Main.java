package ua.study;

import ua.study.dao.impl.transaction_helper.TransactionHelper;
import ua.study.dao.impl.transaction_helper.Executor;

import java.sql.SQLException;

/**
 * Created by dima on 28.03.17.
 */
public class Main {
    static TransactionHelper transactionHelper = TransactionHelper.getInstance();
    Executor executor = new Executor();

    public static void main(String[] args) throws SQLException {
        Main main = new Main();
        transactionHelper.beginTransaction();
        //transactionHelper.getConnection().getConnection().setAutoCommit(true);
        main.createTableUsers();
        main.createTableRoomTypes();
        main.createTableRooms();
        main.createTableReservations();
        main.createTableBills();
        main.createTableReservedRooms();
        //transactionHelper.getConnection().getConnection().commit();
        //transactionHelper.getConnection().getConnection().close();
        transactionHelper.commitTransaction();
        transactionHelper.closeTransaction();
    }
        //DELETE
    public void createTableBills() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS bills("
                + "bill_id SERIAL PRIMARY KEY NOT NULL, "
                + "reservation_id INTEGER REFERENCES reservations(reservation_id) UNIQUE NOT NULL, "
                + "billing_date TIMESTAMP CONSTRAINT bill_date DEFAULT CURRENT_TIMESTAMP NOT NULL, "
                + "total_price INTEGER NOT NULL)";

        executor.executorUpdate(createTableSql);
    }

        //DELETE
    public void createTableReservations() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS reservations("
                + "reservation_id SERIAL PRIMARY KEY NOT NULL, "
                + "client_id INTEGER REFERENCES users(user_id) NOT NULL, "
                + "reservation_date TIMESTAMP CONSTRAINT reserv_date DEFAULT CURRENT_TIMESTAMP NOT NULL, "
                + "arriving_date TIMESTAMP(8) NOT NULL, "
                + "departure_date TIMESTAMP(8) NOT NULL, "
                + "status RESERVATION_STATUS NOT NULL)";
        //+ "administrator_id INTEGER REFERENCES users(user_id))";

        String enumSql = "CREATE TYPE reservation_status AS ENUM ( 'NEW', 'CONFIRMED', 'REJECTED')";

        //getExecutor().executorUpdate(getTransactionHelper().getConnection(), enumSql);
        executor.executorUpdate(createTableSql);
    }

        //DELETE
    public void createTableReservedRooms() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS reserved_rooms("
                + "reserved_room_id SERIAL PRIMARY KEY NOT NULL, "
                + "reservation_id INTEGER REFERENCES reservations(reservation_id) NOT NULL, "
                + "room_type_id INTEGER REFERENCES room_types(room_type_id) NOT NULL, "
                + "room_number INTEGER REFERENCES rooms(room_number))";

        executor.executorUpdate(createTableSql);
    }

        //DELETE
    public void createTableRooms() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS rooms("
                + "room_number INTEGER PRIMARY KEY NOT NULL, "
                + "room_type_id INTEGER REFERENCES room_types(room_type_id) NOT NULL, "
                + "balcony BOOLEAN NOT NULL)";

        String s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23;

        s1 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (201, 1, false)";
        s2 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (202, 1, false)";
        s3 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (203, 1, false)";
        s4 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (204, 1, false)";
        s5 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (205, 1, false)";
        s6 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (206, 3, false)";
        s7 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (207, 3, false)";
        s8 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (208, 3, false)";
        s9 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (209, 3, false)";
        s10 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (210, 3, false)";
        s11 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (211, 2, false)";
        s12 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (212, 2, false)";
        s13 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (213, 2, false)";
        s14 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (214, 2, false)";
        s15 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (215, 2, false)";
        s16 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (301, 4, true)";
        s17 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (302, 4, true)";
        s18 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (303, 4, true)";
        s19 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (304, 5, true)";
        s20 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (305, 5, true)";
        s21 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (306, 5, true)";
        s22 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (401, 6, true)";
        s23 = "INSERT INTO rooms (room_number, room_type_id, balcony) VALUES (402, 6, true)";
        String s[] = new String[23];

        executor.executorUpdate(createTableSql);

        executor.executorUpdate(s1);
        executor.executorUpdate(s2);
        executor.executorUpdate(s3);
        executor.executorUpdate(s4);
        executor.executorUpdate(s5);
        executor.executorUpdate(s6);
        executor.executorUpdate(s7);
        executor.executorUpdate(s8);
        executor.executorUpdate(s9);
        executor.executorUpdate(s10);
        executor.executorUpdate(s11);
        executor.executorUpdate(s12);
        executor.executorUpdate(s13);
        executor.executorUpdate(s14);
        executor.executorUpdate(s15);
        executor.executorUpdate(s16);
        executor.executorUpdate(s17);
        executor.executorUpdate(s18);
        executor.executorUpdate(s19);
        executor.executorUpdate(s20);
        executor.executorUpdate(s21);
        executor.executorUpdate(s22);
        executor.executorUpdate(s23);
    }

    //DELETE
    /*public boolean insert(Room domain) {
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
    }*/

    //DELETE
    public void createTableRoomTypes() {

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

        executor.executorUpdate(createTableSql);
        executor.executorUpdate(insert1);
        executor.executorUpdate(insert2);
        executor.executorUpdate(insert3);
        executor.executorUpdate(insert4);
        executor.executorUpdate(insert5);
        executor.executorUpdate(insert6);
    }

    //DELETE
    public void createTableUsers() {

        String createTableSql = "CREATE TABLE IF NOT EXISTS users("
                + "user_id SERIAL PRIMARY KEY NOT NULL, "
                + "name VARCHAR(30) NOT NULL, "
                + "login VARCHAR(30) UNIQUE NOT NULL, "
                + "password VARCHAR(20) NOT NULL)";
                //+ "user_role ROLE NOT NULL)";

        String enumSql = "CREATE TYPE role AS ENUM ( 'USER', 'ADMIN')";

        //getExecutor().executorUpdate(getTransactionHelper().getConnection(), enumSql);
        executor.executorUpdate(createTableSql);
    }
}