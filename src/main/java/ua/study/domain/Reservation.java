package ua.study.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class Reservation {
    private long reservationId;
    private long clientId;
    private LocalDate reservationDate;
    private LocalDate arrivingDate;
    private LocalDate departureDate;
    private List<ReservedRoom> reservedRooms;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDate getArrivingDate() {
        return arrivingDate;
    }

    public void setArrivingDate(LocalDate arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public List<ReservedRoom> getReservedRooms() {
        return reservedRooms;
    }

    public void setReservedRooms(List<ReservedRoom> reservedRooms) {
        this.reservedRooms = reservedRooms;
    }
}
