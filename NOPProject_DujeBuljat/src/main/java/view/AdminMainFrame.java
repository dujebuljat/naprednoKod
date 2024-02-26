package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static test_server_db.HibernateUtil.closeSessionFactory;

public class AdminMainFrame extends JFrame {
    private ReservationFormPanel adminFormPanel;
    private AdminTablePanel adminTablePanel;
    private JButton roomsUsersButton;

    public AdminMainFrame() {
        setTitle("Admin Main Frame");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        adminFormPanel = new ReservationFormPanel();
        adminTablePanel = new AdminTablePanel();

        // Set preferred sizes for the panels
        adminFormPanel.setPreferredSize(new Dimension(350, 600));
        adminTablePanel.setPreferredSize(new Dimension(850, 600));

        // Create the "Rooms and users" button
        roomsUsersButton = new JButton("Rooms and users");
        roomsUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRoomsUsersFrame();
            }
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(roomsUsersButton);

        // Create a panel for the main content with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS)); // Horizontal layout
        mainPanel.add(Box.createHorizontalGlue()); // Add glue to push panels to the sides
        mainPanel.add(adminFormPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Add some spacing between panels
        mainPanel.add(adminTablePanel);
        mainPanel.add(Box.createHorizontalGlue()); // Add glue to push panels to the sides

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showRoomsUsersFrame() {
        RoomsUsersFrame roomsUsersFrame = new RoomsUsersFrame(this);
        roomsUsersFrame.setVisible(true);
    }
}

class RoomsUsersFrame extends JFrame {
    private AdminRoomsPanel adminRoomsPanel;
    private AdminUsersPanel adminUsersPanel;

    public RoomsUsersFrame(JFrame parentFrame) {
        setTitle("Rooms and Users");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentFrame);

        adminRoomsPanel = new AdminRoomsPanel();
        adminUsersPanel = new AdminUsersPanel();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this frame
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(adminRoomsPanel);
        mainPanel.add(adminUsersPanel);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
}
