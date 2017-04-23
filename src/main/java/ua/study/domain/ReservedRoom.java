package ua.study.domain;

/**
 * Created by dima on 28.03.17.
 */
public class ReservedRoom {
    private long reservedRoomId;
    private long reservationId;
    private int roomTypeId;
    private int roomId;

    public long getReservedRoomId() {
        return reservedRoomId;
    }

    public void setReservedRoomId(long reservedRoomId) {
        this.reservedRoomId = reservedRoomId;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
