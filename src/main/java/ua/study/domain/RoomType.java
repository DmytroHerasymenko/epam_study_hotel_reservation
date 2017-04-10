package ua.study.domain;

import ua.study.domain.enums.Bedspace;
import ua.study.domain.enums.RoomCategory;

/**
 * Created by dima on 28.03.17.
 */
public class RoomType {
    private long roomTypeId;
    private RoomCategory roomCategory;
    private Bedspace bedspace;
    private int price;

    public long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(long roomTypeId) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
