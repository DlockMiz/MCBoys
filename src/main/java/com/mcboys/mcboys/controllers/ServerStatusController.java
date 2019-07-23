package com.mcboys.mcboys.controllers;

import com.mcboys.mcboys.logstream.LogStream;
import com.mcboys.mcboys.models.McServer;
import com.mcboys.mcboys.processkiller.ProcessKiller;
import com.mcboys.mcboys.repositories.McServerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@RestController
public class ServerStatusController {
    @Value("${mc_server.start.command}")
    private List<String> startCommand;
    @Value("${system.jps.command}")
    private List<String> jpsCommand;
    @Value("${mc_server.home.directory}")
    private String serverDirectory;
    SimpMessageSendingOperations template;
    ProcessKiller pk;
    LogStream stream;
    ProcessBuilder pb = new ProcessBuilder();
    McServerRepository repo;
    McServer server;
    Process process;


    public ServerStatusController(McServerRepository repo, LogStream stream, @Qualifier("processKiller") ProcessKiller pk, SimpMessageSendingOperations template) {
        this.repo = repo;
        this.stream = stream;
        this.pk = pk;
        this.template = template;
    }

    @GetMapping("/server_status")
    public String getServerStatus(){
        return server.serverStatus.name();
    }

    @GetMapping("/delete")
    public void delete(){
        repo.deleteAll();
    }

    @GetMapping("/start_server")
    public String startServer() throws Exception {
        if(server.serverStatus.equals(McServer.Status.ON))
            return "Server Already On";

        File file = new File(System.getProperty("user.dir")+serverDirectory);
        pb.command(startCommand);
        pb.directory(file);

        server.setServerStatus(McServer.Status.ON);
        template.convertAndSend("/server_status", true);
        repo.save(server);

        process = pb.start();
        stream.streamLogs(process.getInputStream());
        stream.streamLogs(process.getErrorStream());

        return "Process Complete";
    }

    @GetMapping("stop_server")
    public void stopServer() throws Exception{
        pb.command(jpsCommand);
        Process processList = pb.start();
        String pidNum = stream.grabServerPid(processList.getInputStream());
        server.setPid(pidNum);
        server.setServerStatus(McServer.Status.OFF);
        repo.save(server);

        pk.killProcess(pidNum);
    }

    @PostConstruct
    private void collectServerFromDatabase(){
        McServer found_server = repo.findByServerName("McServer");
        if(found_server == null) {
            server = new McServer();
            server.setServerName("McServer");
            server.setServerStatus(McServer.Status.OFF);
            repo.save(server);
        } else {
            server = found_server;
        }
    }
}
