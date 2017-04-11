package ua.study.service;

import ua.study.domain.ReservedRoom;

import java.util.List;

/**
 * Created by dima on 11.04.17.
 */
public interface ReservedRoomService {
    List<ReservedRoom> getReservedRooms(Long reservationId);
}
