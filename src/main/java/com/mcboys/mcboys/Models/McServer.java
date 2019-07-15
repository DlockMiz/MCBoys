package com.mcboys.mcboys.Models;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("McServer")
public class McServer{
    public enum Status{
        ON, OFF
    }

    @Id
    public String id;
    public Status server_status;

    public void setServerStatus(Status status){
        server_status = status;
    }
}
