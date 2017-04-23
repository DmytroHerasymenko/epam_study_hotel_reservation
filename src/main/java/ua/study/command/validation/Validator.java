package ua.study.command.validation;

import ua.study.domain.RoomType;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by dima on 03.04.17.
 */
public class Validator {

    private static final Validator instance = new Validator();

    public static Validator getInstance(){
        return instance;
    }

    private Validator(){}

    public boolean isNameValid(String name){
        return name != null && name.matches("^[A-ZА-Я]{1}[A-Za-zА-Яа-я\\s-]{2,29}$");
    }

    public boolean isLoginValid(String login){
        return login != null && login.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$");
    }

    public boolean isPasswordValid(String password){
        return password != null && password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}");
    }

    public boolean isReservationDatesValid(LocalDate arrive, LocalDate departure){
        return !arrive.isBefore(LocalDate.now()) || !arrive.isAfter(LocalDate.now().plusYears(1))
                || !departure.isBefore(arrive.plusDays(1)) || !departure.isAfter(arrive.plusDays(30));
    }

    public boolean isReservedRoomsQuantityValid(Map<RoomType, Integer> reservedRoomTypes){
        boolean isValid = true;
        for(Map.Entry<RoomType, Integer> entry : reservedRoomTypes.entrySet()){
            if(entry.getValue() < 0) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public boolean isAnyRoomReserved(Map<RoomType, Integer> reservedRoomTypes){
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