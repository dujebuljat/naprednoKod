import view.AdminMainFrame;
import view.LoginFrame;
import view.UserMainFrame;

import javax.swing.*;

public class AppTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new UserMainFrame();
//                new AdminMainFrame();
                new LoginFrame();
            }
        });
    }
}
