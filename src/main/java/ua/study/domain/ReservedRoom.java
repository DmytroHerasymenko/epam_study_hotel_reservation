package ua.study.domain;

/**
 * Created by dima on 28.03.17.
 */
public class ReservedRoom {
    private long reservedRoomId;
    private long reservationId;
    private long roomTypeId;
    private int roomNumber;

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

    public long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
