package ua.study.epam.service;

import java.time.LocalDate;

/**
 * Created by dima on 04.04.17.
 */
public interface ReservationService {
    boolean reservation(String clientLogin, LocalDate arrive, LocalDate departure, int[] reservedRooms);
}
