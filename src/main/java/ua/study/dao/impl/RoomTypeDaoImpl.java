package ua.study.dao.impl;

import ua.study.dao.AbstractDao;
import ua.study.domain.RoomType;
import ua.study.domain.enums.Bedspace;
import ua.study.domain.enums.RoomCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class RoomTypeDaoImpl extends AbstractDao<RoomType> {

    public List<RoomType> getRoomTypes(){
        String getRoomTypesSql = "SELECT * FROM room_types";
        List<RoomType> roomTypes = new ArrayList<>();

        getExecutor().executorQuery(getRoomTypesSql, result -> {
            if(!(result.next())) return null;
            do {
                RoomType roomType = new RoomType();
                roomType.setRoomTypeId(result.getLong(1));
                roomType.setRoomCategory(RoomCategory.valueOf(result.getString(2)));
                roomType.setBedspace(Bedspace.valueOf(result.getString(3)));
                roomType.setPrice(result.getInt(4));
                roomTypes.add(roomType);
            } while(result.next());
            return roomTypes;
        });
        return roomTypes;
    }
}
