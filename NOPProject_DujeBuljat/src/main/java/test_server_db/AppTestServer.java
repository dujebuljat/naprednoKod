package test_server_db;

public class AppTestServer {

    public static void main(String[] args) {

        TestDBServer test = new TestDBServer();

        test.connect();
        test.disconnect();
    }
}
