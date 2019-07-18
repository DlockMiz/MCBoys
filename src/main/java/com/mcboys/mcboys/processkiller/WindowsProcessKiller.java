package com.mcboys.mcboys.processkiller;
import com.mcboys.mcboys.logstream.LogStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.List;


public class WindowsProcessKiller implements ProcessKiller {
    @Value("${mc_server.kill.pid.command}")
    private List<String> killPidCommand;
    @Autowired
    SimpMessageSendingOperations template;
    ProcessBuilder pb = new ProcessBuilder();

    public void killProcess(String pid) throws Exception{
        String end_command = killPidCommand.get(2) + " " + pid + "/F";
        killPidCommand.set(2, end_command);
        pb.command(killPidCommand);

        Process killProcess = pb.start();
        this.template.convertAndSend("/logs", "Success: Server Terminated!");
        this.template.convertAndSend("/server_status", false);
        LogStream stream = new LogStream();
        stream.streamLogs(killProcess.getErrorStream());
    }
}
