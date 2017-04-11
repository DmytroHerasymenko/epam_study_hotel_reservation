package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.transaction_helper.TransactionHelper;
import ua.study.domain.Bill;
import ua.study.dao.impl.BillDao;
import ua.study.service.BillService;

/**
 * Created by dima on 09.04.17.
 */
public class BillServiceImpl implements BillService {

    @Override
    public Bill bill(Long reservationId) {
        DaoFactory factory = DaoFactory.getInstance();
        BillDao billDao = factory.getDao("BillDao", BillDao.class);
        TransactionHelper.getInstance().beginTransaction();
        Bill bill = billDao.get(reservationId);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return bill;
    }
}
