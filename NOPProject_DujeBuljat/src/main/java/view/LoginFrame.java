package view;

import model.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import test_server_db.HibernateUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static test_server_db.HibernateUtil.closeSessionFactory;

public class LoginFrame extends JFrame {

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginFrame() {
        super("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 20, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 160, 25);
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 160, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 100, 80, 25);
        loginButton.addActionListener(e -> login());
        add(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 100, 80, 25);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                closeSessionFactory();
            }
        });
        add(cancelButton);
    }

    private void login() {
        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Create Hibernate session factory
        try (SessionFactory factory = HibernateUtil.createSessionFactory()) {

            // Create Hibernate session
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            // Query database for user
            Users user = session.createQuery("FROM Users WHERE userName=:username AND password=:password", Users.class)
                    .setParameter("username", userName)
                    .setParameter("password", password)
                    .uniqueResult();

            session.getTransaction().commit();
            session.close();

            // Check if user exists and determine user type
            if (user != null) {
                if (user.getUserName().equals("admin")) {
                    // Open admin frame
                    AdminMainFrame adminFrame = new AdminMainFrame();
                    adminFrame.setVisible(true);
                    System.out.println("Admin logged in");
                    user.setUserID(user.getUserID());
                    System.out.println(user.getUserID());
                    LoginFrame.this.dispose();
                } else {
                    // Open user frame
                    UserMainFrame userFrame = new UserMainFrame();
                    userFrame.setVisible(true);
                    System.out.println("User logged in");
                    user.setUserID(user.getUserID());
                    System.out.println(user.getUserID());
                    LoginFrame.this.dispose();
                }
            } else {
                // Show error message for invalid credentials
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
