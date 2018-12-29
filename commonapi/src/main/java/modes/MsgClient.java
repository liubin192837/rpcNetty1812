package modes;

import utils.StringUtil;

public class MsgClient {
    public String serviceName ;
    public String methodName;
    public String parameter;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getServiceName() {

        return serviceName;
    }

    public void setServiceName(String serviceName) {
        if (StringUtil.isEmpty(serviceName)) {
            throw new RuntimeException("serviceName is null or \" \""+".");
        }
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        if (StringUtil.isEmpty(methodName)) {
            throw new RuntimeException("methodName is null.");
        }
        this.methodName = methodName;
    }

    public String toString(){
        return serviceName+","+methodName+","+parameter;
    }
}
