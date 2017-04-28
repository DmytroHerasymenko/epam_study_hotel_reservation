package ua.study.domain;

/**
 * Created by dima on 28.03.17.
 */
public class Bill {
    private long reservationId;
    private double totalPrice;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
