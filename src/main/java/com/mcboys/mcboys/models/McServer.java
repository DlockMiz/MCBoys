package com.mcboys.mcboys.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("McServer")
public class McServer{
    public enum Status{
        ON, OFF
    }

    @Id
    private String id;
    @Indexed
    private String serverName;
    public Status serverStatus;
    private String pidProcess;

    public void setServerStatus(Status status){
        serverStatus = status;
    }
    public void setServerName(String name){serverName = name;}
    public String getServerName(){return serverName;}
    public void setPid(String pid){pidProcess = pid;}
    public String getPid(){return pidProcess;}
}
