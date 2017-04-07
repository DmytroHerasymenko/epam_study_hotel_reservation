package ua.study.epam.dao.postgres;

import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;
import ua.study.epam.domain.RoomType;
import ua.study.epam.domain.enums.Bedspace;
import ua.study.epam.domain.enums.RoomCategory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class RoomTypeDaoPostgres extends AbstractDaoPostgres<RoomType> {

    public RoomTypeDaoPostgres(ConnectionProxy connectionProxy) {
        super(connectionProxy);
    }

    public List<RoomType> getRoomTypes(){
        String getRoomTypesSql = "SELECT * FROM room_types";

        List<RoomType> roomTypes = new ArrayList<>();

        getExecutor().executorQuery(getConnectionProxy().getConnection(), getRoomTypesSql, result -> {
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
