package view;

import dao.ReservationsDaoImpl;
import dao.RoomsDao;
import dao.RoomsDaoImpl;
import model.Reservations;
import model.Rooms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminRoomsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private RoomsDao roomsDao;

    public AdminRoomsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Admin Rooms"));

        roomsDao = new RoomsDaoImpl();
        initTable();
        fetchAndDisplayData();
        initButtons();
    }

    private void initTable() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make all cells except the roomID non-editable
            }
        };
        model.addColumn("Room ID");
        model.addColumn("Price");

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make all cells except the roomID non-editable
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void fetchAndDisplayData() {
        model.setRowCount(0); // Clear previous data
        List<Rooms> rooms = roomsDao.getAll();
        for (Rooms room : rooms) {
            model.addRow(new Object[]{room.getRoomID(), room.getPrice()});
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
                    int roomID = (int) table.getValueAt(selectedRowIndex, 0);
                    String priceString = (String) table.getValueAt(selectedRowIndex, 1);
                    int price = Integer.parseInt(priceString);


                    // Create a new Reservations object with the retrieved data
                    Rooms updatedRoom = new Rooms(roomID, price);

                    // Update the record in the database
                    RoomsDaoImpl roomsDao = new RoomsDaoImpl();
                    roomsDao.update(updatedRoom, roomID);

                    // Refresh the table after the update
                    fetchAndDisplayData();
                    JOptionPane.showMessageDialog(AdminRoomsPanel.this, "Edit room with ID: " + roomID);
                } else {
                    JOptionPane.showMessageDialog(AdminRoomsPanel.this, "Please select a row to edit", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex != -1) {
                    int roomID = (int) table.getValueAt(selectedRowIndex, 0);

                    // Delete the reservation from the database using its ID
                    RoomsDaoImpl roomsDao = new RoomsDaoImpl();
                    Rooms room = roomsDao.getById(roomID);
                    if (room != null) {
                        // Delete the reservation
                        roomsDao.delete(room);

                        // Refresh the table to reflect the changes
                        fetchAndDisplayData();
                    }
                    JOptionPane.showMessageDialog(AdminRoomsPanel.this, "Delete room with ID: " + roomID);
                } else {
                    JOptionPane.showMessageDialog(AdminRoomsPanel.this, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center-align buttons
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
