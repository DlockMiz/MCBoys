package com.mcboys.mcboys.Controllers;
import com.mcboys.mcboys.Models.McServer;
import com.mcboys.mcboys.Repositories.McServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.Optional;

@RestController
public class CommandController {
    McServerRepository repo;

    @Value("${mc_server.start.command}")
    private List<String> start_command;
    private final SimpMessageSendingOperations template;

    public CommandController(SimpMessageSendingOperations template, McServerRepository repo) {
        this.template = template;
        this.repo = repo;
    }


    @GetMapping("/start_server")
    public void startServer() throws Exception {
        ProcessBuilder pb = new ProcessBuilder();
        File file = new File(System.getProperty("user.dir")+"/mc_server");
        pb.command(start_command);

        pb.directory(file);
        Process process = pb.start();

        output(process.getInputStream());
        output(process.getErrorStream());
    }

    @GetMapping("/test")
    public void test(){
        McServer server = new McServer();
        server.setServerStatus(McServer.Status.OFF);
        repo.save(server);
    }

    @GetMapping("/plz")
    public void plz(){
       Iterable<McServer> servers = repo.findAll();
       for(McServer server : servers){
           System.out.println(server.id);
       }
    }

    private void output(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + System.getProperty("line.separator"));
                this.template.convertAndSend("/mc_server", line);
                System.out.println(line);
            }
        } finally {
            br.close();
        }
    }
}
