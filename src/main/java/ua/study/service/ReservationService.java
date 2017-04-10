package ua.study.service;

import ua.study.domain.Reservation;

import java.time.LocalDate;

/**
 * Created by dima on 04.04.17.
 */
public interface ReservationService {
    Reservation reservation(String clientLogin, LocalDate arrive, LocalDate departure, int[] reservedRooms);
}
