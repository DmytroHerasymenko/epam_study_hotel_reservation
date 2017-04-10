package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.ReservationDaoImpl;
import ua.study.dao.impl.ReservedRoomDaoImpl;
import ua.study.dao.impl.RoomTypeDaoImpl;
import ua.study.dao.impl.UserDaoImpl;
import ua.study.dao.impl.transaction_helper.TransactionHelper;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.enums.ReservationStatus;
import ua.study.domain.RoomType;
import ua.study.domain.User;
import ua.study.domain.enums.Bedspace;
import ua.study.domain.enums.RoomCategory;
import ua.study.service.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 04.04.17.
 */
public class ReservationServiceImpl implements ReservationService {
    private List<RoomType> roomTypes;

    @Override
    public Reservation reservation(String clientLogin, LocalDate arrive, LocalDate departure,
                                   int[] reservedRooms) {
        DaoFactory factory = DaoFactory.getInstance();

        ReservationDaoImpl reservationDao = factory.getDao("ReservationDao", ReservationDaoImpl.class);
        UserDaoImpl clientDao = factory.getDao("UserDao", UserDaoImpl.class);
        ReservedRoomDaoImpl reservedRoomDao = factory.getDao("ReservedRoomDao", ReservedRoomDaoImpl.class);
        RoomTypeDaoImpl roomTypeDao = factory.getDao("RoomTypeDao", RoomTypeDaoImpl.class);

        getRoomTypes(roomTypeDao);
        Reservation reservation = getReservation(clientDao, clientLogin, arrive, departure);

        TransactionHelper.getInstance().beginTransaction();
        Long reservationId = reservationDao.insert(reservation);
        if(reservationId == null){
            TransactionHelper.getInstance().rollbackTransaction();
            TransactionHelper.getInstance().closeTransaction();
            return null;
        }
        List<ReservedRoom> reservedRoomsList = getReservedRooms(reservedRooms, reservationId);
        boolean isSuccess = reservedRoomDao.insert(reservedRoomsList);

        if(!isSuccess){
            TransactionHelper.getInstance().rollbackTransaction();
            TransactionHelper.getInstance().closeTransaction();
            return null;
        }
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();

        Reservation checkedReservation = new Reservation();
        checkedReservation.setReservationId(reservationId);
        ///////////////////////////////
        checkedReservation.setStatus(checkDates(reservationDao, reservation, reservedRooms));
        updateReservationStatus(reservationDao, checkedReservation);
        return checkedReservation;
    }

    private boolean updateReservationStatus(ReservationDaoImpl reservationDao, Reservation reservation){
        TransactionHelper.getInstance().beginTransaction();
        boolean isSuccess = reservationDao.update(reservation);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return isSuccess;
    }

    private Reservation getReservation(UserDaoImpl clientDao, String clientLogin,
                                       LocalDate arrive, LocalDate departure){

        Reservation reservation = new Reservation();
        User user = new User();
        user.setLogin(clientLogin);
        TransactionHelper.getInstance().beginTransaction();
        User client = clientDao.getClientByLogin(user);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        reservation.setClientId(client.getUserId());
        reservation.setArrivingDate(arrive);
        reservation.setDepartureDate(departure);
        reservation.setStatus(ReservationStatus.NEW);
        return reservation;
    }

    private List<RoomType> getRoomTypes(RoomTypeDaoImpl roomTypeDao){
        if(roomTypes == null){
            TransactionHelper.getInstance().beginTransaction();
            roomTypes = roomTypeDao.getRoomTypes();
            TransactionHelper.getInstance().commitTransaction();
            TransactionHelper.getInstance().closeTransaction();
        }
        return roomTypes;
    }

    private ReservationStatus checkDates(ReservationDaoImpl reservationDao, Reservation reservation,
                                   int[] reservedRooms){

        TransactionHelper.getInstance().beginTransaction();
        List<ReservedRoom> confirmedReservedRooms = reservationDao.checkDates(reservation);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return countReservedRoomTypes(reservedRooms, confirmedReservedRooms);
    }

    private List<ReservedRoom> getReservedRooms(int[] reservedRooms, Long reservationId){
        List<ReservedRoom> reservedRoomsList = new ArrayList<>();

        ReservedRoom reservedRoom;
        long id;

        for(int i = 0; i < reservedRooms.length; i++){
            id = getRoomTypeId(i);
            for(int j = 0; j < reservedRooms[i]; j++){
                reservedRoom = new ReservedRoom();
                reservedRoom.setRoomTypeId(id);
                reservedRoom.setReservationId(reservationId);
                reservedRoomsList.add(reservedRoom);
            }
        }
        return reservedRoomsList;
    }

    private Long getRoomTypeId(int x) {
        switch (x) {
            case 0: return getId(RoomCategory.STANDARD, Bedspace.SINGLE);
            case 1: return getId(RoomCategory.STANDARD, Bedspace.DOUBLE);
            case 2: return getId(RoomCategory.STANDARD, Bedspace.TWIN);
            case 3: return getId(RoomCategory.SUITE, Bedspace.DOUBLE);
            case 4: return getId(RoomCategory.SUITE, Bedspace.TWIN);
            case 5: return getId(RoomCategory.DELUXE, Bedspace.DOUBLE);
            default: throw new IllegalArgumentException();
        }
    }

    private Long getId(RoomCategory category, Bedspace bedspace){
        for (RoomType roomType : roomTypes) {
            if (roomType.getRoomCategory().equals(category) && roomType.getBedspace().equals(bedspace))
                return roomType.getRoomTypeId();
        }
        return null;
    }

    private ReservationStatus countReservedRoomTypes(int[] reservedRooms,
                                                     List<ReservedRoom> confirmedReservedRooms){
        int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0;
        for (ReservedRoom r : confirmedReservedRooms) {
            switch ((int) r.getRoomTypeId()) {
                case 1: count1++;
                    break;
                case 2: count2++;
                    break;
                case 3: count3++;
                    break;
                case 4: count4++;
                    break;
                case 5: count5++;
                    break;
                case 6: count6++;
                    break;
                default: throw new IllegalArgumentException("wrong room type");
            }
        }
        if(reservedRooms[0] > (5-count1) || reservedRooms[1] > (5-count2) || reservedRooms[2] > (5-count3)
                || reservedRooms[3] > (3-count4) || reservedRooms[4] > (3-count5) || reservedRooms[5] > (2-count6)){
            return ReservationStatus.REJECTED;
        } else {
            return ReservationStatus.CONFIRMED;
        }
    }
}