package clientTest;

import clientCommon.ProxyHelperTool;
import service.MsgService;

import java.util.concurrent.CountDownLatch;

public class TestClient {
    public static ProxyHelperTool proxyHelperTool = new ProxyHelperTool();
    public static void main(String[] args) throws Exception {
        MsgService msgService = proxyHelperTool.create(MsgService.class);
        String reslut = msgService.send("I'm test msg");
        System.out.println("Client get mag:" + "\n" + "..." + reslut);
    }

}
