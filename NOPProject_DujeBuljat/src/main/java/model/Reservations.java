package model;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "reservations")
public class Reservations implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationID;

    @Column
    private int roomID;

    @Column
    private int userID;

    @Column
    private String fName;

    @Column
    private String lName;

    @Column
    private int phoneNumber;

    @Column
    private String email;

    @Column
    private String dateIn;

    @Column
    private String dateOut;

    @Column
    private String roomType;

    @Column
    private String paymentMethod;

    @Column
    private int totalPrice;

    public Reservations() {
    }

    public Reservations(int reservationID, int roomID, int userID, String fName, String lName, int phoneNumber, String email, String dateIn, String dateOut, String roomType, String paymentMethod, int totalPrice) {
        this.reservationID = reservationID;
        this.roomID = roomID;
        this.userID = userID;
        this.fName = fName;
        this.lName = lName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.roomType = roomType;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationID=" + reservationID +
                ", roomID=" + roomID +
                ", userID=" + userID +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", checkInDate='" + dateIn + '\'' +
                ", checkOutDate='" + dateOut + '\'' +
                ", roomType='" + roomType + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
