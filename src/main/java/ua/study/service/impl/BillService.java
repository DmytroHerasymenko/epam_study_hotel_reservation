package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.BillDao;
import ua.study.domain.Bill;
import ua.study.service.Service;

/**
 * Created by dima on 20.04.17.
 */
public class BillService implements Service {
    public boolean bill(Bill bill){
        BillDao billDao = DaoFactory.getInstance().getDao("BillDao", BillDao.class);
        return billDao.insert(bill);
    }
}
