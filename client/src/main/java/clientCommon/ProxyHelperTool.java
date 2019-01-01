package clientCommon;

import annotations.ServiceName;
import modes.Constants;
import modes.RequestRpc;

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

                        ClientHelper client = new ClientHelper(Constants.HOST, Constants.PORT);
                        String response = client.send(requestRpc);
                        if (response == null) {
                            throw new RuntimeException("response is null");
                        }
                        return response;
                    }
                }
        );
    }
}
