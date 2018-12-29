package clientTest;

import clientCommon.ProxyHelperTool;
import service.MsgService;

public class TestClient {
    public static void main(String[] args) throws Exception {
        ProxyHelperTool proxyHelperTool = new ProxyHelperTool();
        MsgService msgService = proxyHelperTool.create(MsgService.class);
        String reslut = msgService.send("I'm test msg");
        System.out.println("Client get mag:"+ "\n" +"..."+reslut);
    }
}
