package view;

import dao.ReservationsDaoImpl;
import model.Reservations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminTablePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public AdminTablePanel() {
        setLayout(new BorderLayout()); // Set BorderLayout for the panel
        setBorder(BorderFactory.createTitledBorder("Admin Table"));

        initTable();
        fetchAndDisplayData();
        initButtons();
    }

    private void initTable() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make specific columns non-editable
                return !(column == 0 || column == 1 || column == 2 || column == 11);
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(550, 500));
        add(scrollPane, BorderLayout.CENTER); // Add scroll pane to the center of the panel
    }

    private void fetchAndDisplayData() {
        model.setRowCount(0); // Clear previous data

        ReservationsDaoImpl reservationsDao = new ReservationsDaoImpl();
        List<Reservations> reservationsList = reservationsDao.getAll();

        if (reservationsList != null) {
            String[] columns = {"Reservation ID", "Room ID", "User ID", "First Name", "Last Name",
                    "Phone Number", "Email", "Date In", "Date Out", "Room Type", "Payment Method", "Total Price"};

            model.setColumnIdentifiers(columns);

            for (Reservations reservation : reservationsList) {
                Object[] rowData = {
                        reservation.getReservationID(),
                        reservation.getRoomID(),
                        reservation.getUserID(),
                        reservation.getfName(),
                        reservation.getlName(),
                        reservation.getPhoneNumber(),
                        reservation.getEmail(),
                        reservation.getDateIn(),
                        reservation.getDateOut(),
                        reservation.getRoomType(),
                        reservation.getPaymentMethod(),
                        reservation.getTotalPrice()
                };
                model.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to fetch reservations data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initButtons() {
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex != -1) {
                    int reservationID = (int) table.getValueAt(selectedRowIndex, 0);
                    int roomID = (int) table.getValueAt(selectedRowIndex, 1);
                    int userID = (int) table.getValueAt(selectedRowIndex, 2);
                    String fName = (String) table.getValueAt(selectedRowIndex, 3);
                    String lName = (String) table.getValueAt(selectedRowIndex, 4);
                    int phoneNumber = (int) table.getValueAt(selectedRowIndex, 5);
                    String email = (String) table.getValueAt(selectedRowIndex, 6);
                    String dateIn = (String) table.getValueAt(selectedRowIndex, 7);
                    String dateOut = (String) table.getValueAt(selectedRowIndex, 8);
                    String roomType = (String) table.getValueAt(selectedRowIndex, 9);
                    String paymentMethod = (String) table.getValueAt(selectedRowIndex, 10);
                    int totalPrice = (int) table.getValueAt(selectedRowIndex, 11);

                    // Create a new Reservations object with the retrieved data
                    Reservations updatedReservation = new Reservations(reservationID, roomID, userID, fName, lName,
                            phoneNumber, email, dateIn, dateOut, roomType, paymentMethod, totalPrice);

                    // Update the record in the database
                    ReservationsDaoImpl reservationsDao = new ReservationsDaoImpl();
                    reservationsDao.update(updatedReservation, reservationID);

                    // Refresh the table after the update
                    fetchAndDisplayData();

                    // Display a notification about the status of the edit
                    JOptionPane.showMessageDialog(AdminTablePanel.this, "Reservation with ID: " + reservationID + " has been edited");
                } else {
                    JOptionPane.showMessageDialog(AdminTablePanel.this, "Please select a row to edit", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex != -1) {
                    // Retrieve the reservation ID from the selected row
                    int reservationID = (int) table.getValueAt(selectedRowIndex, 0);

                    // Delete the reservation from the database using its ID
                    ReservationsDaoImpl reservationsDao = new ReservationsDaoImpl();
                    Reservations reservation = reservationsDao.getById(reservationID);
                    if (reservation != null) {
                        // Delete the reservation
                        reservationsDao.delete(reservation);

                        // Refresh the table to reflect the changes
                        fetchAndDisplayData();
                    } else {
                        JOptionPane.showMessageDialog(AdminTablePanel.this, "Failed to delete reservation", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(AdminTablePanel.this, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // Set BoxLayout for button panel
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom of the panel
    }
}
