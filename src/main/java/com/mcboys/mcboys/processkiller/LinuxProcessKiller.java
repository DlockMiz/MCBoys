package com.mcboys.mcboys.processkiller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;


public class LinuxProcessKiller implements ProcessKiller {
    @Value("${mc_server.kill.pid.command}")
    private String killPidCommand;
    @Autowired
    SimpMessageSendingOperations template;
    ProcessBuilder pb = new ProcessBuilder();

    public void killProcess(String pid) throws Exception{
        String end_command = killPidCommand +" -9 "+ pid;
        Runtime.getRuntime().exec(end_command);

        this.template.convertAndSend("/logs", "Success: Server Terminated!");
        this.template.convertAndSend("/server_status", false);
    }
}
