package ua.study.service;

import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.Bill;
import ua.study.domain.User;

import java.util.List;

/**
 * Created by dima on 09.04.17.
 */
public interface BillService {
    Bill bill();
    User getClient();
    Reservation getReservation();
    List<ReservedRoom> getReservedRooms();
}
