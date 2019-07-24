package com.mcboys.mcboys.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;


@RestController
public class OptionsController {
    @GetMapping("/get_backup_worlds")
    public void getBackupWorlds(){
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().equals("mc_server")) {
                System.out.println("File " + listOfFiles[i].getName());
                break;
            } else if (i+1 == listOfFiles.length) {
                listOfFiles = folder.getParentFile().listFiles();
                i=0;
            }
        }
    }

}
