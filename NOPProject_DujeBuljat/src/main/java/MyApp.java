import model.Reservations;
import model.Rooms;
import model.Users;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import view.LoginFrame;

import javax.swing.*;

public class MyApp {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        // Initialize Hibernate session factory
        initializeSessionFactory();

        // Start the application
        SwingUtilities.invokeLater(() -> {
            // Create and display the login frame
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });

        // Add shutdown hook to close session factory when the application exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        }));
    }

    private static void initializeSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(Reservations.class);
            configuration.addAnnotatedClass(Users.class);
            configuration.addAnnotatedClass(Rooms.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
