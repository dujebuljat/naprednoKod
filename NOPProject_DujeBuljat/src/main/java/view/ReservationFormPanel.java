package view;

import com.github.lgooddatepicker.components.DatePicker;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import test_server_db.HibernateUtil;
import java.util.List;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationFormPanel extends JPanel {

    private JLabel fNameLabel;
    private JLabel lNameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel dateInLabel;
    private JLabel dateOutLabel;
    private JLabel roomTypeLabel;
    private JLabel paymentOptionLabel;

    private JTextField fNameField;
    private JTextField lNameField;
    private JTextField phoneField;
    private JTextField emailField;
    private DatePicker dateInField;
    private DatePicker dateOutField;
    private JComboBox<RoomTypeEnum> roomTypeCombo;
    private JComboBox<String> paymentOptionCombo;

    private JButton addButton;

    private int price;

    private PaymentStrategy paymentStrategy;
//    private UserMainFrame userMainFrame;

    public ReservationFormPanel() {
        initComps();
        layoutComps();
    }

    private void initComps() {
        fNameLabel = new JLabel("First Name:");
        lNameLabel = new JLabel("Last Name:");
        phoneLabel = new JLabel("Phone Number:");
        emailLabel = new JLabel("Email:");
        dateInLabel = new JLabel("Date In:");
        dateOutLabel = new JLabel("Date Out:");
        roomTypeLabel = new JLabel("Room Type:");
        paymentOptionLabel = new JLabel("Payment Option:");

        fNameField = new JTextField(15);
        lNameField = new JTextField(15);
        phoneField = new JTextField(15);
        emailField = new JTextField(15);
        dateInField = new DatePicker();
        dateOutField = new DatePicker();
        roomTypeCombo = new JComboBox<>();
        paymentOptionCombo = new JComboBox<>();

        addButton = new JButton("Add");

        roomTypeCombo.setModel(new DefaultComboBoxModel<>(RoomTypeEnum.values()));
        paymentOptionCombo.setModel(new DefaultComboBoxModel<>());
        paymentOptionCombo.addItem("Cash");
        paymentOptionCombo.addItem("Credit Card");
        paymentOptionCombo.addItem("Google Pay");

        price = 0;

        paymentStrategy = null;
    }

    private void layoutComps() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.weighty = 0.25;
        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(fNameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(fNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lNameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(dateInLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(dateInField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(dateOutLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(dateOutField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(roomTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(roomTypeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(paymentOptionLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(paymentOptionCombo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Command addCommandUser = new AddCommandUser(ReservationFormPanel.this);
                addCommandUser.executeCommand();

            }
        });


    }


    // Method to perform add action

    public void addReservation() {
        Session session = null;
        try (SessionFactory factory = HibernateUtil.createSessionFactory()) {
            session = factory.getCurrentSession();
            session.beginTransaction();

            String fName = fNameField.getText();
            String lName = lNameField.getText();
            int phone = Integer.parseInt(phoneField.getText());
            String email = emailField.getText();
            LocalDate dateIn = dateInField.getDate();
            LocalDate dateOut = dateOutField.getDate();
            String roomType = roomTypeCombo.getSelectedItem().toString();
            String selectedPaymentMethod = (String) paymentOptionCombo.getSelectedItem();

            // Retrieve the first available room ID for the given time frame
            int roomID = getFirstAvailableRoomID(session, dateIn, dateOut);

            // Calculate total price
            int totalPrice = calculateTotalPrice(session, roomType, dateIn, dateOut);

            if (roomID != -1) {
                // Create a new reservation instance
                Reservations reservation = new Reservations(0, roomID, 0, fName, lName, phone, email, dateIn.toString(), dateOut.toString(), roomType, selectedPaymentMethod, totalPrice);

                // Save the reservation using Hibernate
                session.merge(reservation);
                System.out.println("Reservation added: " + reservation);
            } else {
                System.out.println("No available rooms for the selected time frame");
                // Handle case where no available room is found
            }

            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
            System.out.println("Error adding reservation");
            // Handle exception as needed
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    public int calculateTotalPrice(Session session, String roomType, LocalDate dateIn, LocalDate dateOut) {
        // Get the roomID based on roomType and selected dates
        int roomID = getFirstAvailableRoomID(session, dateIn, dateOut);

        if (roomID == -1) {
            // No available room found for the selected time frame
            return -1; // Return an invalid value to indicate an error
        }

        // Calculate base price based on room type
        int basePrice = calculateBasePrice(roomType);

        // Check if dateIn is before dateOut
        if (dateIn.isAfter(dateOut)) {
            System.err.println("Invalid date selection: Check-out date must be after check-in date");
            return -1; // Invalid value to indicate an error
        }

        // Calculate the duration of stay in days
        int numberOfDays = (int) ChronoUnit.DAYS.between(dateIn, dateOut);

        // Retrieve the price of the room based on roomID
        int roomPrice = getRoomPrice(roomID);

        // Calculate total price based on base price, room price, and duration of stay
        int totalPrice = (basePrice + roomPrice) * numberOfDays;

        return totalPrice;
    }

    // Method to get the price of the room based on roomID
    private int getRoomPrice(int roomID) {
        try (Session session = HibernateUtil.createSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            // Retrieve the room object based on roomID
            Rooms room = session.get(Rooms.class, roomID);

            session.getTransaction().commit();

            if (room != null) {
                return room.getPrice();
            } else {
                System.err.println("Room not found with ID: " + roomID);
                return 0; // Return a default value or handle this case as needed
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0; // Return a default value or handle the exception as needed
        }
    }

// Your existing methods...


//    // Method to calculate the total price considering the duration of stay
//    public int calculateTotalPrice(String roomType, LocalDate dateIn, LocalDate dateOut) {
//        int basePrice = calculateBasePrice(roomType);
//
//        // Check if dateIn is before dateOut
//        if (dateIn.isAfter(dateOut)) {
//            System.err.println("Invalid date selection: Check-out date must be after check-in date");
//            // You can handle this case as needed, maybe return a default value or throw an exception
//            return -1; // Invalid value to indicate an error
//        }
//
//        // Calculate the duration of stay in days
//        int numberOfDays = (int) ChronoUnit.DAYS.between(dateIn, dateOut);
//
//        // Calculate total price based on base price and duration of stay
//        int totalPrice = basePrice * numberOfDays;
//
//        return totalPrice;
//    }


    public int calculateBasePrice(String roomType) {
        int basePrice = 0;

        // Determine base price based on room type
        switch (roomType) {
            case "OneBed":
                basePrice = 50;
                break;
            case "TwoBeds":
                basePrice = 100;
                break;
            case "ThreeBeds":
                basePrice = 150;
                break;
            case "Apartment":
                basePrice = 200;
                break;
            default:
                System.err.println("Invalid room type: " + roomType);
                // You can handle this case as needed, maybe return a default value or throw an exception
        }

        // Calculate total price based on base price and any additional charges
        // For simplicity, let's assume no additional charges for now
        return basePrice;
    }

    public int getFirstAvailableRoomID(Session session, LocalDate dateIn, LocalDate dateOut) {
        try {
            session.beginTransaction();

            // Query to find rooms that don't have reservations overlapping with the specified time frame
            String hql = "SELECT r.roomID " +
                    "FROM Rooms r " +
                    "WHERE NOT EXISTS (" +
                    "    SELECT 1 FROM Reservations rs " +
                    "    WHERE rs.roomID = r.roomID " +
                    "    AND NOT (rs.dateOut < :dateIn OR rs.dateIn > :dateOut)" +
                    ")";

            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("dateIn", dateIn);
            query.setParameter("dateOut", dateOut);

            // Retrieve the first available room ID
            List<Integer> availableRoomIDs = query.setMaxResults(1).getResultList();

            session.getTransaction().commit();

            if (!availableRoomIDs.isEmpty()) {
                return availableRoomIDs.get(0);
            } else {
                System.out.println("No available rooms for the selected time frame");
                return -1; // No available room found
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1; // Error occurred
        }
    }


//    public void addReservation() {
//        // Your existing add action logic
//        int id = 0;
//        String fName = fNameField.getText();
//        String lName = lNameField.getText();
//        int phone = Integer.parseInt(phoneField.getText());
//        String email = emailField.getText();
//        String dateIn = dateInField.getDateStringOrEmptyString();
//        String dateOut = dateOutField.getDateStringOrEmptyString();
//        String roomType = roomTypeCombo.getSelectedItem().toString();
//
//        String selectedPaymentMethod = (String) paymentOptionCombo.getSelectedItem();
//
//        switch (selectedPaymentMethod) {
//            case "Cash" -> paymentStrategy = new CashPayment();
//            case "Credit Card" -> paymentStrategy = new CreditCardPayment();
//            case "Google Pay" -> paymentStrategy = new GooglePayPayment();
//        }
//
//        String paymentMethod = null;
//        if (paymentStrategy != null) {
//            paymentMethod = paymentStrategy.pay();
//        } else {
//            System.out.println("Payment method not selected");
//        }
//
//        int totalPrice = calculateTotalPrice(roomType, dateInField.getDate(), dateOutField.getDate());
//
//        Reservations reservations = new Reservations(id, 10, 15, fName, lName, phone, email, dateIn, dateOut, roomType, paymentMethod, totalPrice);
//
//        System.out.println();
//        System.out.println(reservations.toString());
//    }
}

