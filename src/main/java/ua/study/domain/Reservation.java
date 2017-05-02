package ua.study.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class Reservation {
    private long reservationId;
    private String clientLogin;
    private LocalDate arrivingDate;
    private LocalDate departureDate;
    private List<ReservedRoom> reservedRooms;
    private double totalPrice;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
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
        for(ReservedRoom reservedRoom : this.reservedRooms){
            totalPrice += reservedRoom.getPrice();
        }
        double stayingPeriod = ChronoUnit.DAYS.between(arrivingDate, departureDate);
        totalPrice = totalPrice * stayingPeriod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
