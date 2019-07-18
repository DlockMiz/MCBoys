package com.mcboys.mcboys.logstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class LogStream {
    StringBuilder sb = new StringBuilder();
    BufferedReader br;

    @Autowired
    SimpMessageSendingOperations template;

    @Value("${mc_server.pid.name}")
    private String pidName;

    public void streamLogs(InputStream inputStream) throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                this.template.convertAndSend("/logs", line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
    }

    public String grabServerPid(InputStream inputStream) throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                if(line.contains(pidName)) {
                    String pidNum = line.split(pidName)[0];
                    br.close();
                    return pidNum;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return null;
    }
}
