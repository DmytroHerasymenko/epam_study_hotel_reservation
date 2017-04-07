package ua.study.epam.service.impl;

import ua.study.epam.dao.DaoFactory;
import ua.study.epam.dao.postgres.*;
import ua.study.epam.dao.postgres.transaction_helper.TransactionHelper;
import ua.study.epam.domain.Reservation;
import ua.study.epam.domain.ReservedRoom;
import ua.study.epam.domain.RoomType;
import ua.study.epam.domain.User;
import ua.study.epam.domain.enums.Bedspace;
import ua.study.epam.domain.enums.ReservationStatus;
import ua.study.epam.domain.enums.RoomCategory;
import ua.study.epam.service.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 04.04.17.
 */
public class ReservationServiceImpl implements ReservationService {

    private final TransactionHelper transactionHelper;

    private List<RoomType> roomTypes;

    public ReservationServiceImpl(TransactionHelper transactionHelper){
        this.transactionHelper = transactionHelper;
    }

    @Override
    public boolean reservation(String clientLogin, LocalDate arrive, LocalDate departure,
                               int[] reservedRooms) {
        DaoFactory factory = DaoFactory
                .getInstance(transactionHelper.getTransaction(), DaoFactory.DaoFactoryType.POSTGRES);

        ReservationDaoPostgres reservationDao = factory.getDao("ReservationDao", ReservationDaoPostgres.class);
        UserDaoPostgres clientDao = factory.getDao("UserDao", UserDaoPostgres.class);
        ReservedRoomDaoPostgres reservedRoomDao = factory.getDao("ReservedRoomDao", ReservedRoomDaoPostgres.class);
        RoomTypeDaoPostgres roomTypeDao = factory.getDao("RoomTypeDao", RoomTypeDaoPostgres.class);

        List<RoomType> roomTypes = getRoomTypes(roomTypeDao);
        transactionHelper.closeTransaction();
        Reservation reservation = getReservation(clientDao, clientLogin, arrive, departure);
        transactionHelper.closeTransaction();

        transactionHelper.beginTransaction();
        Long reservationId = reservationDao.insert(reservation);
        if(reservationId == null){
            transactionHelper.rollbackTransaction();
            transactionHelper.closeTransaction();
            return false;
        }
        List<ReservedRoom> reservedRoomsList = getReservedRooms(roomTypes, reservedRooms, reservationId);
        boolean isSuccess = reservedRoomDao.insert(reservedRoomsList);

        if(!isSuccess){
            transactionHelper.rollbackTransaction();
            transactionHelper.closeTransaction();
            return false;
        }
        transactionHelper.commitTransaction();
        transactionHelper.closeTransaction();
        return isSuccess;
    }

    private Reservation getReservation(UserDaoPostgres clientDao, String clientLogin,
                                       LocalDate arrive, LocalDate departure){

        Reservation reservation = new Reservation();
        User user = new User();
        user.setLogin(clientLogin);
        User client = clientDao.getClientByLogin(user);
        reservation.setClientId(client.getUserId());
        reservation.setArrivingDate(arrive);
        reservation.setDepartureDate(departure);
        reservation.setStatus(ReservationStatus.NEW);
        return reservation;
    }

    private List<RoomType> getRoomTypes(RoomTypeDaoPostgres roomTypeDao){
        if(roomTypes == null){
            roomTypes = roomTypeDao.getRoomTypes();
        }
        return roomTypes;
    }

    private List<ReservedRoom> getReservedRooms(List<RoomType> roomTypes, int[] reservedRooms,
                                                Long reservationId){
        List<ReservedRoom> reservedRoomsList = new ArrayList<>();

        ReservedRoom reservedRoom;
        long id;

        for(int i = 0; i < reservedRooms.length; i++){
            id = getRoomTypeId(roomTypes, reservedRooms[i]);
            for(int j = 0; j < reservedRooms[i]; j++){
                reservedRoom = new ReservedRoom();
                reservedRoom.setRoomTypeId(id);
                reservedRoom.setReservationId(reservationId);
                reservedRoomsList.add(reservedRoom);
            }
        }
        return reservedRoomsList;
    }

    private Long getRoomTypeId(List<RoomType> roomTypes, int x) {
        switch (x) {
            case 0: return getId(roomTypes, RoomCategory.STANDARD, Bedspace.SINGLE);
            case 1: return getId(roomTypes, RoomCategory.STANDARD, Bedspace.DOUBLE);
            case 2: return getId(roomTypes, RoomCategory.STANDARD, Bedspace.TWIN);
            case 3: return getId(roomTypes, RoomCategory.SUITE, Bedspace.DOUBLE);
            case 4: return getId(roomTypes, RoomCategory.SUITE, Bedspace.TWIN);
            case 5: return getId(roomTypes, RoomCategory.DELUXE, Bedspace.DOUBLE);
            default: throw new IllegalArgumentException();
        }
    }

    private Long getId(List<RoomType> roomTypes, RoomCategory category, Bedspace bedspace){
        for (RoomType roomType : roomTypes) {
            if (roomType.getRoomCategory().equals(category) && roomType.getBedspace().equals(bedspace))
                return roomType.getRoomTypeId();
        }
        return null;
    }
}