package ua.study.command.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.impl.DatesHandlerCommand;
import ua.study.domain.RoomType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Created by dima on 03.04.17.
 */
public class Validator {

    private static final Validator instance = new Validator();
    private static final Logger LOGGER = LogManager.getLogger(DatesHandlerCommand.class.getName());

    private Validator(){}

    public static Validator getInstance(){
        return instance;
    }

    public boolean isNameValid(String name){
        return name != null && name.matches("^[A-ZА-Я]{1}[A-Za-zА-Яа-я\\s-]{2,29}$");
    }

    public boolean isLoginValid(String login){
        return login != null && login.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$");
    }

    public boolean isPasswordValid(String password){
        return password != null && password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}");
    }

    public boolean isReservationDatesValid(String arriveDate, String departureDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate arrive;
        LocalDate departure;
        try {
            arrive = LocalDate.parse(arriveDate, formatter);
            departure = LocalDate.parse(departureDate, formatter);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        boolean isArriveValid = !arrive.isBefore(LocalDate.now()) && !arrive.isAfter(LocalDate.now().plusYears(1));
        boolean isDepartureValid = !departure.isBefore(arrive.plusDays(1)) && !departure.isAfter(arrive.plusDays(30));
        return isArriveValid && isDepartureValid;
    }

    public boolean isReservedRoomsQuantityValid(Object reservedRoomTypesMap){
        Map<RoomType, Integer> reservedRoomTypes;
        try {
            reservedRoomTypes = (Map<RoomType, Integer>) reservedRoomTypesMap;
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return isReservedRoomQuantityPositive(reservedRoomTypes) && isAnyRoomReserved(reservedRoomTypes);
    }

    private boolean isReservedRoomQuantityPositive(Map<RoomType, Integer> reservedRoomTypes){
        boolean isValid = true;
        for(Map.Entry<RoomType, Integer> entry : reservedRoomTypes.entrySet()){
            if(entry.getValue() < 0) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private boolean isAnyRoomReserved(Map<RoomType, Integer> reservedRoomTypes){
        boolean isValid = false;
        for(Map.Entry<RoomType, Integer> entry : reservedRoomTypes.entrySet()){
            if(entry.getValue() > 0) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}