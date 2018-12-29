package serverTest;


import serverCommon.ServerService;

public class ServerTest {
    public static void main(String[] args) throws Exception {
        ServerService serverService = new ServerService();
        serverService.start();
    }
}
