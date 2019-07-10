package com.mcboys.mcboys.Controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
public class CommandController {

    @GetMapping("/run")
    public String runCommand() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", "dir");
        Process process = processBuilder.start();
        String list = output(process.getInputStream());
        System.out.println(list);
        return list;
    }

    private static String output(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + System.getProperty("line.separator"));
            }
        } finally {
            br.close();
        }
        return sb.toString();
    }
}
