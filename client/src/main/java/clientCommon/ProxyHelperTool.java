package clientCommon;

import annotations.ServiceName;
import clientCommon.ClientHelper;
import modes.Constants;
import modes.MsgClient;
import utils.StringUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHelperTool {


    public ProxyHelperTool() {

    }

    public <T> T create(final Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        MsgClient msgClient = new MsgClient();
                        if(method.getDeclaringClass().getAnnotation(ServiceName.class) == null){
                            throw new RuntimeException("Annotation(ServiceName) is null.");
                        }
                        msgClient.setServiceName(method.getDeclaringClass().getAnnotation(ServiceName.class).name());
                        msgClient.setMethodName(method.getName());
                        msgClient.setParameter(args[0].toString());
                        ClientHelper client = new ClientHelper(Constants.HOST, Constants.PORT);
                        long time = System.currentTimeMillis();
                        String response = client.send(msgClient.toString());
                        if (response == null) {
                            throw new RuntimeException("response is null");
                        }
                        return response;
                    }
                }
        );
    }
}
