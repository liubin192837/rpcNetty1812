package serverCommon;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import service.MsgService;
import utils.GetServiceNameHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MsgHandler extends SimpleChannelInboundHandler<String> {
    //map 本地的服务类
    private Map<String, Object> handlerMap = new HashMap<>();

    public MsgHandler() {
        initMapServices();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //test client have really been blocked
        //Thread.sleep(2000);
        channelHandlerContext.writeAndFlush(handle(s)).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Server:" + cause);
        ctx.close();
    }

    private Object handle(String msg) throws Exception {
        /*
        根据接受的参数,判定调用的服务,方法,参数.通过反射调用

         parameters[0] is serviceName
         parameters[1] is methodName
         parameters[2] is args
         */
        String[] parameters = msg.split(",");
        Object object = handlerMap.get(parameters[0]);
        Class<?> serviceClass = object.getClass();
        Method method = serviceClass.getMethod(parameters[1], String.class);
        method.setAccessible(true);
        Object[] args = {parameters[2]};
        return method.invoke(object, args);
    }

    private void initMapServices() {
        MsgService msgService = new MsgServiceImpl();
        handlerMap.put(GetServiceNameHelper.getServiceName(msgService), msgService);
    }
}
