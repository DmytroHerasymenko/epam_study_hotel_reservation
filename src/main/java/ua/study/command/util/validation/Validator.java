package ua.study.command.util.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.domain.RoomType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dima on 03.04.17.
 */
public class Validator {

    private final Logger LOGGER = LogManager.getLogger(Validator.class);

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
        try {
            LocalDate arrive = LocalDate.parse(arriveDate, formatter);
            LocalDate departure = LocalDate.parse(departureDate, formatter);
            return isArriveDataValid(arrive) && isDepartureDataValid(arrive, departure);
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean isReservedRoomsQuantityValid(Map<RoomType, String> reservedRoomQuantity){
        try {
            Map<RoomType, Integer> reservedRoomTypes = reservedRoomQuantity.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> Integer.parseInt(entry.getValue())
                    ));
            return isQuantityValid(reservedRoomTypes) && isAnyRoomReserved(reservedRoomTypes);
        } catch (NumberFormatException e){
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    private boolean isQuantityValid(Map<RoomType, Integer> reservedRoomTypes) {
        for(Map.Entry<RoomType, Integer> entry : reservedRoomTypes.entrySet()){
            if(entry.getValue() < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isAnyRoomReserved(Map<RoomType, Integer> reservedRoomTypes){
        for(Map.Entry<RoomType, Integer> entry : reservedRoomTypes.entrySet()){
            if(entry.getValue() > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isArriveDataValid(LocalDate arrive){
        return !arrive.isBefore(LocalDate.now()) && !arrive.isAfter(LocalDate.now().plusYears(1));
    }

    private boolean isDepartureDataValid(LocalDate arrive, LocalDate departure){
        return !departure.isBefore(arrive.plusDays(1)) && !departure.isAfter(arrive.plusDays(30));
    }
}