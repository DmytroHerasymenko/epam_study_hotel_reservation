package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.ReservationDao;
import ua.study.dao.impl.ReservedRoomDao;
import ua.study.dao.impl.UserDao;
import ua.study.dao.impl.executor.TransactionHelper;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.RoomType;
import ua.study.domain.User;
import ua.study.service.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationService implements Service {

    public Reservation reservation(String login, LocalDate arrive, LocalDate departure,
                                   Map<RoomType, Integer> reservedRoomTypes){
        ReservationDao reservationDao = DaoFactory.getInstance().getDao("ReservationDao", ReservationDao.class);
        UserDao userDao = DaoFactory.getInstance().getDao("UserDao", UserDao.class);
        User domain = new User();
        domain.setLogin(login);
        User user = userDao.get(domain);
        Reservation reservation = getReservation(user.getUserId(), arrive, departure);

        TransactionHelper.getInstance().beginTransaction();
        Long reservationId = (reservationDao.insert(reservation));
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
        ReservationDao reservationDao = DaoFactory.getInstance().getDao("ReservationDao", ReservationDao.class);
        User user = new User();
        user.setLogin(login);
        return reservationDao.get(user);
    }

    private boolean reservationRooms(Reservation reservation, Map<RoomType, Integer> reservedRoomTypes){
        ReservedRoomDao reservedRoomDao =
                DaoFactory.getInstance().getDao("ReservedRoomDao", ReservedRoomDao.class);
        List<ReservedRoom> reservedRooms = new ArrayList<>();
        ReservedRoom reservedRoom;
        for(Map.Entry<RoomType, Integer> entry : reservedRoomTypes.entrySet()){
            for(int i = 0; i < entry.getValue(); i++){
                reservedRoom = new ReservedRoom();
                reservedRoom.setRoomTypeId(entry.getKey().getRoomTypeId());
                reservedRoom.setReservationId(reservation.getReservationId());
                reservedRooms.add(reservedRoom);
            }
        }
        return reservedRoomDao.insert(reservedRooms);
    }

    private Reservation getReservation(Long clientId, LocalDate arrive, LocalDate departure){
        Reservation reservation = new Reservation();
        reservation.setClientId(clientId);
        reservation.setArrivingDate(arrive);
        reservation.setDepartureDate(departure);
        return reservation;
    }
}
