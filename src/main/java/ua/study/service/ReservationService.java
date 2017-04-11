package ua.study.service;

import ua.study.domain.Reservation;
import ua.study.domain.Room;
import ua.study.domain.RoomType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by dima on 04.04.17.
 */
public interface ReservationService {
    Reservation reservation(String clientLogin, LocalDate arrive, LocalDate departure, int[] reservedRooms);
    List<Room> getAllRooms();
    List<RoomType> getAllRoomTypes();
}
