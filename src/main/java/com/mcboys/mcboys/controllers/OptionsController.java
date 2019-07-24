package com.mcboys.mcboys.controllers;

import com.mcboys.mcboys.models.ServerList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;

@RestController
public class OptionsController {
    @Value("${mc_server.home.directory}")
    private String serverDirectory;
    ServerList serverList = new ServerList();


    @GetMapping("/get_backup_worlds")
    public ServerList getBackupWorlds(){
        return locateBackupFolder();
    }

    @GetMapping("/create_backup_world")
    public String createBackupWorld() throws Exception{
        ProcessBuilder pb = new ProcessBuilder();
        File mc_server_location = new File(serverDirectory);
        pb.directory(mc_server_location);
        pb.command("./backup.sh");
        pb.start();
        return "Success";
    }

    public ServerList locateBackupFolder(){
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().equals("mc_server_backup")) {
                serverList.setList(listOfFiles[i].list());
                serverList.setErrorMessage("Success");
                return serverList;
            } else if (i+1 == listOfFiles.length) {
                folder = listOfFiles[0].getParentFile().getParentFile();
                listOfFiles = folder.listFiles();
                i=0;
            }
        }
        serverList.setErrorMessage("Couldn't Find Folder");
        return serverList;
    }
}
