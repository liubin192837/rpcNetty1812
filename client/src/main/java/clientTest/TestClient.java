package clientTest;

import clientCommon.ClientHelper;
import clientCommon.ProxyHelperTool;
import service.MsgService;

import java.util.concurrent.CountDownLatch;

public class TestClient {
    public volatile static int ii=0;
    public static ProxyHelperTool proxyHelperTool = new ProxyHelperTool();
    public static void main(String[] args) throws Exception {
        int threadNumber = 15;
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        for(int i=0;i<threadNumber;i++){
            new Thread(){
                @Override
                public void run() {
                    //通过传递当前线程的名称（Thread.currentThread().getName）给服务端；
                    //服务端服务 组合收到的字符 再次发回来。通过对比，可见各个线程收到的是否是自己发送的。
                    MsgService msgService = proxyHelperTool.create(MsgService.class);
                    String reslut = msgService.send(Thread.currentThread().getName());
                    System.out.println("Client("+Thread.currentThread().getName()+") get mag"+ii+":" + "\n" + "..." + reslut);
                    countDownLatch.countDown();
                }
            }.start();
        }
        countDownLatch.await();
        ClientHelper.getClientHelper().close();
    }

}
