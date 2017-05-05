package ua.study.command.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 03.04.17.
 */
public class Validator {

    private static final Validator instance = new Validator();
    private final Logger LOGGER = LogManager.getLogger(Validator.class.getName());

    private Validator(){}

    public static Validator getInstance(){
        return instance;
    }

    public boolean isNameValid(String name){
        String regex="^[A-Z]{1}[A-Za-z\\s-]{2,29}|[\\u0410-\\u042f\\u0401]{1}[\\u0410-\\u044f\\u0401\\u0451\\s-]{2,29}$";
        return name != null && name.matches(regex);
    }

    public boolean isLoginValid(String login){
        return login != null && login.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$");
    }

    public boolean isPasswordValid(String password){
        return password != null && password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}$");
    }

    public boolean isReservationDatesValid(String arriveDate, String departureDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate arrive;
        LocalDate departure;
        try {
            arrive = LocalDate.parse(arriveDate, formatter);
            departure = LocalDate.parse(departureDate, formatter);
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        boolean isArriveValid = !arrive.isBefore(LocalDate.now()) && !arrive.isAfter(LocalDate.now().plusYears(1));
        boolean isDepartureValid = !departure.isBefore(arrive.plusDays(1)) && !departure.isAfter(arrive.plusDays(30));
        return isArriveValid && isDepartureValid;
    }

    public boolean isReservedRoomsQuantityValid(String json){
        Map<Integer, Integer> reservedRoomTypes = new HashMap<>();
        String[] entry;
        int key;
        int value;
        try {
            String[] jsonArray = json.split("&");
            for(String s : jsonArray) {
                entry = s.split("=");
                key = Integer.valueOf(entry[0]);
                value = Integer.valueOf(entry[1]);
                reservedRoomTypes.put(key, value);
            }
        } catch (NumberFormatException e){
            LOGGER.error(e.getMessage());
            return false;
        }
        return isQuantityValid(reservedRoomTypes) && isAnyRoomReserved(reservedRoomTypes);
    }

    private boolean isQuantityValid(Map<Integer, Integer> reservedRoomTypes) {
        for(Map.Entry<Integer, Integer> entry : reservedRoomTypes.entrySet()){
            if(entry.getKey() < 0 || entry.getValue() < 0) return false;
        }
        return true;
    }

    private boolean isAnyRoomReserved(Map<Integer, Integer> reservedRoomTypes){
        for(Map.Entry<Integer, Integer> entry : reservedRoomTypes.entrySet()){
            if(entry.getValue() > 0) return true;
        }
        return false;
    }
}