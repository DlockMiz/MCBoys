package com.mcboys.mcboys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;

@RestController
public class OptionsController {
    @Autowired
    SimpMessageSendingOperations template;

    @GetMapping("/get_backup_worlds")
    public String getBackupWorlds(){
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            template.convertAndSend("/logs", listOfFiles[i].getName());
            if (listOfFiles[i].getName().equals("mc_server")) {
                System.out.println("File " + listOfFiles[i].getName());
                return listOfFiles[i].getName();
            } else if (i+1 == listOfFiles.length) {
                listOfFiles = folder.getParentFile().listFiles();
                i=0;
            }
        }
        return "Nothing";
    }
}
