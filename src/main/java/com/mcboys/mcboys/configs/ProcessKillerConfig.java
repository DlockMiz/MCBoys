package com.mcboys.mcboys.configs;

import com.mcboys.mcboys.processkiller.LinuxProcessKiller;
import com.mcboys.mcboys.processkiller.WindowsProcessKiller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessKillerConfig {
    @Bean("processKiller")
    @Conditional(WindowsCondition.class)
    public WindowsProcessKiller windowsProcessKiller(){
        return new WindowsProcessKiller();
    }

    @Bean("processKiller")
    @Conditional(LinuxCondition.class)
    public LinuxProcessKiller linuxProcessKiller(){
        return new LinuxProcessKiller();
    }
}
