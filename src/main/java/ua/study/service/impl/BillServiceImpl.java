package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.transaction_helper.TransactionHelper;
import ua.study.domain.Bill;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.dao.impl.BillDaoImpl;
import ua.study.domain.User;
import ua.study.service.BillService;

import java.util.List;

/**
 * Created by dima on 09.04.17.
 */
public class BillServiceImpl implements BillService {

    @Override
    public Bill bill() {

        DaoFactory factory = DaoFactory.getInstance();
        BillDaoImpl billDao = factory.getDao("BillDao", BillDaoImpl.class);
        TransactionHelper.getInstance().beginTransaction();
        //billDao.getBillBy
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();

        return null;
    }

    @Override
    public User getClient() {
        return null;
    }

    @Override
    public Reservation getReservation() {
        return null;
    }

    @Override
    public List<ReservedRoom> getReservedRooms() {
        return null;
    }
}
