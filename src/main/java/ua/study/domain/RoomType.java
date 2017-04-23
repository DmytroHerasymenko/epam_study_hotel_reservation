package ua.study.domain;

import ua.study.domain.enums.Bedspace;
import ua.study.domain.enums.RoomCategory;

/**
 * Created by dima on 28.03.17.
 */
public class RoomType {
    private int roomTypeId;
    private RoomCategory roomCategory;
    private Bedspace bedspace;
    private double price;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
