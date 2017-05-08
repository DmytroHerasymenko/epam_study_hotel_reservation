package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.BillDao;
import ua.study.dao.impl.ReservationDao;
import ua.study.dao.impl.ReservedRoomDao;
import ua.study.dao.impl.executor.TransactionHelper;
import ua.study.domain.*;
import ua.study.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationService implements Service {

    public Reservation reservation(Reservation reservation, Map<RoomType, Integer> reservedRoomTypes){
        ReservationDao reservationDao = DaoFactory.getInstance().getDao(ReservationDao.class);

        TransactionHelper.getInstance().beginTransaction();
        Long reservationId = reservationDao.insert(reservation);
        reservation.setReservationId(reservationId);
        boolean isSuccess = reservationRooms(reservation, reservedRoomTypes);
        if(!isSuccess){
            TransactionHelper.getInstance().rollbackTransaction();
            return null;
        }
        saveBill(reservation);
        TransactionHelper.getInstance().commitTransaction();

        return reservation;
    }

    public List<Reservation> getReservations(User user) {
        ReservationDao reservationDao = DaoFactory.getInstance().getDao(ReservationDao.class);
        return reservationDao.get(user);
    }

    private boolean reservationRooms(Reservation reservation, Map<RoomType, Integer> reservedRoomTypes){
        ReservedRoomDao reservedRoomDao = DaoFactory.getInstance().getDao(ReservedRoomDao.class);
        List<ReservedRoom> reservedRooms = new ArrayList<>();

        reservedRoomTypes.forEach((key, value) ->
                setReservedRooms(reservedRooms, key.getRoomTypeId(), value, reservation));

        boolean isSuccess = reservedRoomDao.insert(reservation, reservedRooms);
        if(isSuccess) {
            reservation.setReservedRooms(reservedRoomDao.get(reservation));
        }

        return isSuccess;
    }

    private void setReservedRooms(List<ReservedRoom> reservedRooms, int roomTypeId, int quantity,
                                  Reservation reservation) {
        ReservedRoom reservedRoom;
        for(int i = 0; i < quantity; i++){
            reservedRoom = new ReservedRoom();
            reservedRoom.setRoomTypeId(roomTypeId);
            reservedRoom.setReservationId(reservation.getReservationId());
            reservedRooms.add(reservedRoom);
        }
    }

    private void saveBill(Reservation reservation) {
        BillDao billDao = DaoFactory.getInstance().getDao(BillDao.class);
        Bill bill = new Bill();
        bill.setReservationId(reservation.getReservationId());
        bill.setTotalPrice(reservation.getTotalPrice());
        billDao.insert(bill);
    }
}
