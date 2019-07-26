package com.mcboys.mcboys.controllers;

import com.mcboys.mcboys.models.ServerList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class OptionsController {
    @Value("${mc_server.home.directory}")
    private String serverDirectory;
    ServerList serverList = new ServerList();


    @GetMapping("/get_backup_worlds")
    public ServerList getBackupWorlds(){
        locateBackupFolder();
        return serverList;
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

    @GetMapping(value = "/download_backup_world/{filename}")
    public ResponseEntity<FileSystemResource> downloadBackupWorld(@PathVariable String filename) throws Exception{
        File backupLocation = locateBackupFolder();
        File backupWorld = locateWorld(filename, backupLocation);

        FileSystemResource resource = new FileSystemResource(System.getProperty("user.dir")+"/"+filename+".zip");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+backupWorld.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(backupWorld.length())
                .body(resource);
    }

    public File locateBackupFolder(){
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().equals("mc_server_backup")) {
                serverList.setList(listOfFiles[i].list());
                serverList.setErrorMessage("Success");
                return listOfFiles[i];
            } else if (i+1 == listOfFiles.length) {
                folder = listOfFiles[0].getParentFile().getParentFile();
                listOfFiles = folder.listFiles();
                i=0;
            }
        }
        serverList.setErrorMessage("Couldn't Find Folder");
        return folder;
    }

    public File locateWorld(String world, File backups) throws Exception{
        File folder = new File(backups.getAbsolutePath());
        File[] listOfFiles = folder.listFiles();
        System.out.println(folder.getName());
        System.out.println(world);

        for (int i = 0; i < listOfFiles.length; i++) {
            System.out.println(listOfFiles[i].getName());
            if (listOfFiles[i].getName().equals(world)) {
                folder = listOfFiles[i];
            }
        }

        System.out.println(folder.getName());


        FileOutputStream fos = new FileOutputStream(folder.getName()+".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        zipFile(folder, folder.getName(), zipOut);
        zipOut.close();
        fos.close();


        return folder;
    }


    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
