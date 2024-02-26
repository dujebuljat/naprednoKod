package view;

import dao.RoomsDaoImpl;
import dao.UsersDao;
import dao.UsersDaoImpl;
import model.Rooms;
import model.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminUsersPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private UsersDao usersDao;

    public AdminUsersPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Admin Users"));

        usersDao = new UsersDaoImpl();
        initTable();
        fetchAndDisplayData();
        initButtons();
    }

    private void initTable() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make all cells except the userID non-editable
            }
        };
        model.addColumn("User ID");
        model.addColumn("Username");
        model.addColumn("Password");

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make all cells except the userID non-editable
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void fetchAndDisplayData() {
        model.setRowCount(0); // Clear previous data
        List<Users> users = usersDao.getAll();
        for (Users user : users) {
            model.addRow(new Object[]{user.getUserID(), user.getUserName(), user.getPassword()}); // Add password to the row data
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
                    int userID = (int) table.getValueAt(selectedRowIndex, 0);
                    String userName = (String) table.getValueAt(selectedRowIndex, 1); // Get username from the table
                    String password = (String) table.getValueAt(selectedRowIndex, 2); // Get password from user input or any other source
                    Users updatedUser = new Users(userID, userName, password); // Create updated user object

                    // Update the record in the database
                    UsersDaoImpl usersDao = new UsersDaoImpl();
                    usersDao.update(updatedUser, userID);

                    // Refresh the table after the update
                    fetchAndDisplayData();
                    JOptionPane.showMessageDialog(AdminUsersPanel.this, "Edit user with ID: " + userID);
                } else {
                    JOptionPane.showMessageDialog(AdminUsersPanel.this, "Please select a row to edit", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex != -1) {
                    int userID = (int) table.getValueAt(selectedRowIndex, 0);

                    // Delete the reservation from the database using its ID
                    UsersDaoImpl usersDao = new UsersDaoImpl();
                    Users user = usersDao.getById(userID);
                    if (user != null) {
                        // Delete the reservation
                        usersDao.delete(user);

                        // Refresh the table to reflect the changes
                        fetchAndDisplayData();
                    }
                    JOptionPane.showMessageDialog(AdminUsersPanel.this, "Delete user with ID: " + userID);
                } else {
                    JOptionPane.showMessageDialog(AdminUsersPanel.this, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
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
