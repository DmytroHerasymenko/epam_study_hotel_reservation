package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.ReservedRoomDao;
import ua.study.dao.impl.transaction_helper.TransactionHelper;
import ua.study.domain.ReservedRoom;
import ua.study.service.ReservedRoomService;

import java.util.List;

/**
 * Created by dima on 11.04.17.
 */
public class ReservedRoomServiceImpl implements ReservedRoomService {
    @Override
    public List<ReservedRoom> getReservedRooms(Long reservationId) {
        DaoFactory factory = DaoFactory.getInstance();
        ReservedRoomDao reservedRoomDao = factory.getDao("ReservedRoomDao", ReservedRoomDao.class);
        TransactionHelper.getInstance().beginTransaction();
        List<ReservedRoom> reservedRooms = reservedRoomDao.get(reservationId);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return reservedRooms;
    }
}
