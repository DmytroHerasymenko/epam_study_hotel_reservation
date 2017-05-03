package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.ReservationDao;
import ua.study.dao.impl.ReservedRoomDao;
import ua.study.dao.impl.executor.TransactionHelper;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.User;
import ua.study.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationService implements Service {

    public Reservation reservation(Reservation reservation, Map<Integer, Integer> reservedRoomTypes){

        ReservationDao reservationDao = DaoFactory.getInstance().getDao(ReservationDao.class);

        TransactionHelper.getInstance().beginTransaction();
        Long reservationId = reservationDao.insert(reservation);
        reservation.setReservationId(reservationId);
        boolean isSuccess = reservationRooms(reservation, reservedRoomTypes);
        if(!isSuccess){
            TransactionHelper.getInstance().rollbackTransaction();
            return null;
        }
        TransactionHelper.getInstance().commitTransaction();
        return reservation;
    }

    public List<Reservation> getReservations(String login) {
        ReservationDao reservationDao = DaoFactory.getInstance().getDao(ReservationDao.class);
        User user = new User();
        user.setLogin(login);
        return reservationDao.get(user);
    }

    private boolean reservationRooms(Reservation reservation, Map<Integer, Integer> reservedRoomTypes){
        ReservedRoomDao reservedRoomDao = DaoFactory.getInstance().getDao(ReservedRoomDao.class);
        List<ReservedRoom> reservedRooms = new ArrayList<>();

        reservedRoomTypes.forEach((key, value) -> getReservedRooms(reservedRooms, key, value, reservation));

        return reservedRoomDao.insert(reservation, reservedRooms);
    }

    private void getReservedRooms(List<ReservedRoom> reservedRooms, int roomTypeId, int quantity,
                                  Reservation reservation) {
        ReservedRoom reservedRoom;
        for(int i = 0; i < quantity; i++){
            reservedRoom = new ReservedRoom();
            reservedRoom.setRoomTypeId(roomTypeId);
            reservedRoom.setReservationId(reservation.getReservationId());
            reservedRooms.add(reservedRoom);
        }

    }
}
