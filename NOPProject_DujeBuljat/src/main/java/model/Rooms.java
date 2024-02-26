package model;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "rooms")
public class Rooms implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomID;

    @Column
    private int price;

    public Rooms() {
    }

    public Rooms(int roomID, int price) {
        this.roomID = roomID;
        this.price = price;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Rooms{" +
                "roomID=" + roomID +
                ", price=" + price +
                '}';
    }
}
