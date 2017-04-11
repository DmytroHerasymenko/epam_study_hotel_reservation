package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.domain.RoomType;
import ua.study.domain.enums.Bedspace;
import ua.study.domain.enums.RoomCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by dima on 28.03.17.
 */
public class RoomTypeDao extends AbstractDao<RoomType> {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(BillDao.class);

    public RoomTypeDao(){
        init();
    }

    public List<RoomType> get(){
        String getRoomTypesSql = properties.getProperty("get.room_type");
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

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/dao.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Override
    public void create() {
        String create = properties.getProperty("create.room_type");
        String category = properties.getProperty("category.room_type");
        String bedspace = properties.getProperty("bedspace.room_type");
        getExecutor().executorUpdate(category);
        getExecutor().executorUpdate(bedspace);
        getExecutor().executorUpdate(create);
    }

    private boolean insert(){
        String insert1 = properties.getProperty("insert1.room_type");
        String insert2 = properties.getProperty("insert2.room_type");
        String insert3 = properties.getProperty("insert3.room_type");
        String insert4 = properties.getProperty("insert4.room_type");
        String insert5 = properties.getProperty("insert5.room_type");
        String insert6 = properties.getProperty("insert6.room_type");
        getExecutor().executorUpdate(insert1);
        getExecutor().executorUpdate(insert2);
        getExecutor().executorUpdate(insert3);
        getExecutor().executorUpdate(insert4);
        getExecutor().executorUpdate(insert5);
        return getExecutor().executorUpdate(insert6);
    }
}
