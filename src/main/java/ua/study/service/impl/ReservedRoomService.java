package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.ReservedRoomDao;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.RoomType;
import ua.study.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservedRoomService implements Service {

    public boolean reservation(Reservation reservation, Map<RoomType, Integer> reservedRoomTypes){
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

    public List<ReservedRoom> getReservedRooms(Long reservationId) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);
        ReservedRoomDao reservedRoomDao =
                DaoFactory.getInstance().getDao("ReservedRoomDao", ReservedRoomDao.class);
        return reservedRoomDao.get(reservation);
    }
}
