package ua.study.domain;

import java.time.LocalDate;

/**
 * Created by dima on 15.05.17.
 */
public class Dates {
    private LocalDate arrivingDate;
    private LocalDate departureDate;

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
}
