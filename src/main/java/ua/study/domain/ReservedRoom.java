package ua.study.domain;

import ua.study.domain.enums.Bedspace;
import ua.study.domain.enums.RoomCategory;

/**
 * Created by dima on 28.03.17.
 */
public class ReservedRoom {
    private long reservationId;
    private RoomCategory roomCategory;
    private Bedspace bedspace;
    private int roomTypeId;
    private int roomNumber;
    private double price;

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

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public Bedspace getBedspace() {
        return bedspace;
    }

    public void setBedspace(Bedspace bedspace) {
        this.bedspace = bedspace;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
