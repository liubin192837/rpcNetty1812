package service;

import annotations.ServiceName;

@ServiceName(name = "MsgService")
public interface MsgService {
    public String send(String msg);
}
