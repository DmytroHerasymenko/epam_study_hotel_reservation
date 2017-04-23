package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.domain.Reservation;
import ua.study.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by dima on 28.03.17.
 */
public class ReservationDao extends AbstractDao<Reservation> {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(ReservationDao.class.getName());

    public ReservationDao(){
        init();
    }

    @Override
    public void create() {
        getExecutor().executorUpdate(properties.getProperty("create.reservation"));
    }

    public Long insert(Reservation domain) {
        return getExecutor().insertReservation(domain, properties.getProperty("insert.reservation"));
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/sql.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }


    public List<Reservation> get(User domain){
        String query = "SELECT * FROM reservations r JOIN users u ON r.client_id = u.user_id WHERE login = ?";

        List<Reservation> reservations = new ArrayList<>();
        getExecutor().getReservation(query, domain, result -> {
            if(!(result.next())) return null;
            do {
                Reservation reservation = new Reservation();
                reservation.setReservationId(result.getLong(1));
                reservation.setClientId(result.getLong(2));
                reservation.setReservationDate(result.getDate(3).toLocalDate());
                reservation.setArrivingDate(result.getDate(4).toLocalDate());
                reservation.setDepartureDate(result.getDate(5).toLocalDate());
                reservations.add(reservation);
            } while (result.next());
            return reservations;
        } );
        return reservations;
    }

}
