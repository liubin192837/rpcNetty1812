package utils;

import annotations.ServiceName;
import service.MsgService;

public class GetServiceNameHelper {
    public static String getServiceName(Object object) {
        if (object == null) {
            throw new RuntimeException("getName: object is null");
        }
        ServiceName serviceName = object.getClass().getAnnotation(ServiceName.class);
        if (serviceName == null) {
            throw new RuntimeException("getName: serviceName is null, please add Annotation(@ServiceName)");
        }
        return serviceName.name();
    }
}
