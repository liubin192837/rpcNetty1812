package clientCommon;

import annotations.ServiceName;
import modes.Constants;
import modes.RequestRpc;
import utils.StringUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class ProxyHelperTool {
    public volatile static ClientHelper client;
    public ProxyHelperTool() {
        client = ClientHelper.getClientHelper();
    }
    public <T> T create(final Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    //@Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getDeclaringClass().getAnnotation(ServiceName.class) == null) {
                            throw new RuntimeException("Annotation(ServiceName) is null.");
                        }
                        RequestRpc requestRpc = new RequestRpc();
                        requestRpc.setMethodName(method.getName());
                        requestRpc.setServiceName(method.getDeclaringClass().getAnnotation(ServiceName.class).name());
                        requestRpc.setParameters(args);
                        requestRpc.setParameterTypes(method.getParameterTypes());
                        requestRpc.setRequestId(StringUtil.getUiid());
                        ClientHandler.waitingRPC.put(requestRpc.getRequestId(),requestRpc);
                        ProxyHelperTool.client.send(requestRpc);
                        synchronized(requestRpc){
                            requestRpc.wait();
                        }
                        return requestRpc.getResult();
                    }
                }
        );
    }
}
