package ua.study.command.validation;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dima on 03.04.17.
 */
public class Validator {

    public boolean isNameIllegal(String name){
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я]{3,30}");
        Matcher matcher = pattern.matcher(name);
        return name == null || !matcher.matches();
    }

    public boolean isLoginIllegal(String login){
        Pattern patternLogin = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$");
        Matcher matcherLogin = patternLogin.matcher(login);
        return login == null || !matcherLogin.matches();
    }

    public boolean isPasswordIllegal(String password){
        Pattern patternPassword = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}");
        Matcher matcherPassword = patternPassword.matcher(password);
        return password == null || !matcherPassword.matches();
    }

    public boolean isSessionValid(HttpSession session){
        return session != null && session.getAttribute("login") != null;
    }

    public boolean isReservationDataIllegal(LocalDate arrive, LocalDate departure, int[] reservedRooms){

        if(reservedRooms[0] == 0 && reservedRooms[1] == 0 && reservedRooms[2] == 0 && reservedRooms[3] == 0 &&
                reservedRooms[4] == 0 && reservedRooms[5] == 0){
            return true;
        }
        return arrive.isBefore(LocalDate.now()) || departure.isBefore(arrive.plusDays(1))
                || departure.isAfter(arrive.plusDays(30))
                || reservedRooms[0] < 0 || reservedRooms[0] > 5 || reservedRooms[1] < 0 || reservedRooms[1] > 5
                || reservedRooms[2] < 0 || reservedRooms[2] > 5 || reservedRooms[3] < 0 || reservedRooms[3] > 3
                || reservedRooms[4] < 0 || reservedRooms[4] > 3 || reservedRooms[5] < 0 || reservedRooms[5] > 2;
     }
}