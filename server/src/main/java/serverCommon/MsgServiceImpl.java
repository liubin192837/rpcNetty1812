package serverCommon;

import annotations.ServiceName;
import service.MsgService;

@ServiceName(name = "MsgService")
public class MsgServiceImpl implements MsgService {
    private String name = null;
    public String send(String msg) {
        System.out.println("Server get msg of client:"+ "\n" +"..."+msg);
        return "Hello, I'm server:"+msg;
    }
}
