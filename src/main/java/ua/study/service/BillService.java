package ua.study.service;

import ua.study.domain.Bill;

/**
 * Created by dima on 09.04.17.
 */
public interface BillService {
    Bill bill(Long reservationId);
}
