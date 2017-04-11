package ua.study.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.DaoFactory;
import ua.study.dao.impl.*;
import ua.study.dao.impl.transaction_helper.TransactionHelper;
import ua.study.domain.*;
import ua.study.domain.enums.ReservationStatus;
import ua.study.domain.enums.Bedspace;
import ua.study.domain.enums.RoomCategory;
import ua.study.service.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 04.04.17.
 */
public class ReservationServiceImpl implements ReservationService {
    private static final Logger LOGGER = LogManager.getLogger(ReservationServiceImpl.class.getName());
    private List<RoomType> roomTypes;
    private List<Room> rooms;

    @Override
    public Reservation reservation(String clientLogin, LocalDate arrive, LocalDate departure,
                                   int[] reservedRooms) {
        DaoFactory factory = DaoFactory.getInstance();

        ReservationDao reservationDao = factory.getDao("ReservationDao", ReservationDao.class);
        UserDao clientDao = factory.getDao("UserDao", UserDao.class);
        ReservedRoomDao reservedRoomDao = factory.getDao("ReservedRoomDao", ReservedRoomDao.class);
        RoomTypeDao roomTypeDao = factory.getDao("RoomTypeDao", RoomTypeDao.class);
        BillDao billDao = factory.getDao("BillDao", BillDao.class);
        RoomDao roomDao = factory.getDao("RoomDao", RoomDao.class);
        getRoomTypes(roomTypeDao);
        getRooms(roomDao);
        Reservation reservation = getReservation(clientDao, clientLogin, arrive, departure);

        TransactionHelper.getInstance().beginTransaction();
        Long reservationId = reservationDao.insert(reservation);
        if(reservationId == null) return rollback();
        List<ReservedRoom> reservedRoomsList = getReservedRooms(reservedRooms, reservationId);
        Boolean isSuccess = reservedRoomDao.insert(reservedRoomsList);
        if(!isSuccess) return rollback();
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();

        Reservation checkedReservation = new Reservation();
        checkedReservation.setReservationId(reservationId);
        checkedReservation.setStatus(checkDates(reservedRoomsList, reservationDao, reservedRoomDao,
                reservation, reservedRooms));
        updateReservationStatus(reservationDao, checkedReservation);
        insertBill(billDao, reservationId, reservedRoomsList);
        return checkedReservation;
    }

    @Override
    public List<Room> getAllRooms(){
        return rooms;
    }
    @Override
    public List<RoomType> getAllRoomTypes(){
        return roomTypes;
    }

    private Reservation rollback(){
        TransactionHelper.getInstance().rollbackTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return null;
    }

    private boolean insertBill(BillDao billDao, Long reservationId, List<ReservedRoom> reservedRooms){
        int totalPrice = 0;
        Bill bill = new Bill();
        bill.setReservationId(reservationId);
        for(ReservedRoom rr : reservedRooms){
            totalPrice += getRoomTypePrice(rr.getRoomTypeId());
        }
        bill.setTotalPrice(totalPrice);
        TransactionHelper.getInstance().beginTransaction();
        boolean isSuccess = billDao.insert(bill);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return isSuccess;
    }

    private int getRoomTypePrice(long id){
        for(RoomType rt : roomTypes){
            if(rt.getRoomTypeId() == id){
                return rt.getPrice();
            }
        }
        throw new IllegalArgumentException();
    }

    private boolean updateReservationStatus(ReservationDao reservationDao, Reservation reservation){
        TransactionHelper.getInstance().beginTransaction();
        boolean isSuccess = reservationDao.update(reservation);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return isSuccess;
    }

    private Reservation getReservation(UserDao clientDao, String clientLogin,
                                       LocalDate arrive, LocalDate departure){

        Reservation reservation = new Reservation();
        User user = new User();
        user.setLogin(clientLogin);
        TransactionHelper.getInstance().beginTransaction();
        User client = clientDao.get(user);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        reservation.setClientId(client.getUserId());
        reservation.setArrivingDate(arrive);
        reservation.setDepartureDate(departure);
        reservation.setStatus(ReservationStatus.NEW);
        return reservation;
    }

    private List<RoomType> getRoomTypes(RoomTypeDao roomTypeDao){
        if(roomTypes == null){
            TransactionHelper.getInstance().beginTransaction();
            roomTypes = roomTypeDao.get();
            TransactionHelper.getInstance().commitTransaction();
            TransactionHelper.getInstance().closeTransaction();
        }
        return roomTypes;
    }

    private List<Room> getRooms(RoomDao roomDao){
        if(rooms == null){
            TransactionHelper.getInstance().beginTransaction();
            rooms = roomDao.get();
            TransactionHelper.getInstance().commitTransaction();
            TransactionHelper.getInstance().closeTransaction();
        }
        return rooms;
    }

    private ReservationStatus checkDates(List<ReservedRoom> reservedRoomsList, ReservationDao reservationDao,
                                         ReservedRoomDao reservedRoomDao, Reservation reservation,
                                         int[] reservedRooms){

        TransactionHelper.getInstance().beginTransaction();
        List<ReservedRoom> confirmedReservedRooms = reservationDao.checkDates(reservation);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        ReservationStatus status = countReservedRoomTypes(reservedRooms, confirmedReservedRooms);

        if(status == ReservationStatus.CONFIRMED){
            TransactionHelper.getInstance().beginTransaction();
            reservedRoomDao.update(setRoomNumbers(reservedRoomsList, confirmedReservedRooms));
            TransactionHelper.getInstance().commitTransaction();
            TransactionHelper.getInstance().closeTransaction();
        }
        return status;
    }

    private List<ReservedRoom> getReservedRooms(int[] reservedRooms, Long reservationId){
        List<ReservedRoom> reservedRoomsList = new ArrayList<>();

        ReservedRoom reservedRoom;
        long id;

        for(int i = 0; i < reservedRooms.length; i++){
            id = getRoomTypeId(i);
            for(int j = 0; j < reservedRooms[i]; j++){
                reservedRoom = new ReservedRoom();
                reservedRoom.setRoomTypeId(id);
                reservedRoom.setReservationId(reservationId);
                reservedRoomsList.add(reservedRoom);
            }
        }
        return reservedRoomsList;
    }

    private Long getRoomTypeId(int x) {
        switch (x) {
            case 0: return getId(RoomCategory.STANDARD, Bedspace.SINGLE);
            case 1: return getId(RoomCategory.STANDARD, Bedspace.DOUBLE);
            case 2: return getId(RoomCategory.STANDARD, Bedspace.TWIN);
            case 3: return getId(RoomCategory.SUITE, Bedspace.DOUBLE);
            case 4: return getId(RoomCategory.SUITE, Bedspace.TWIN);
            case 5: return getId(RoomCategory.DELUXE, Bedspace.DOUBLE);
            default: throw new IllegalArgumentException();
        }
    }

    private Long getId(RoomCategory category, Bedspace bedspace){
        for (RoomType roomType : roomTypes) {
            if (roomType.getRoomCategory().equals(category) && roomType.getBedspace().equals(bedspace))
                return roomType.getRoomTypeId();
        }
        return null;
    }

    private List<ReservedRoom> setRoomNumbers(List<ReservedRoom> reservedRoomsList,
                                              List<ReservedRoom> confirmedReservedRooms){
        List<Room> freeRooms = new ArrayList<>();
        freeRooms.addAll(rooms);
        int size;
        for(ReservedRoom rr : confirmedReservedRooms){
            size = freeRooms.size();
            for(int i=0; i < size;i++){
                if(freeRooms.get(i).getRoomNumber() == rr.getRoomNumber()){
                    freeRooms.remove(i);
                    i--;
                    break;
                }
            }
        }
        for(ReservedRoom rr : reservedRoomsList){
            size = freeRooms.size();
            for(int i = 0; i < size; i++){
                if(rr.getRoomTypeId() == freeRooms.get(i).getRoomTypeId()){
                    rr.setRoomNumber(freeRooms.get(i).getRoomNumber());
                    freeRooms.remove(i);
                    i--;
                    break;
                }
            }
        }
        return reservedRoomsList;
    }

    private ReservationStatus countReservedRoomTypes(int[] reservedRooms,
                                                     List<ReservedRoom> confirmedReservedRooms){
        int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0;
        for (ReservedRoom r : confirmedReservedRooms) {
            switch ((int) r.getRoomTypeId()) {
                case 1: count1++;
                    break;
                case 2: count2++;
                    break;
                case 3: count3++;
                    break;
                case 4: count4++;
                    break;
                case 5: count5++;
                    break;
                case 6: count6++;
                    break;
                default: throw new IllegalArgumentException("wrong room type");
            }
        }
        if(reservedRooms[0] > (5-count1) || reservedRooms[1] > (5-count2) || reservedRooms[2] > (5-count3)
                || reservedRooms[3] > (3-count4) || reservedRooms[4] > (3-count5) || reservedRooms[5] > (2-count6)){
            return ReservationStatus.REJECTED;
        } else {
            return ReservationStatus.CONFIRMED;
        }
    }
}