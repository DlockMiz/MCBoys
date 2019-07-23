package com.mcboys.mcboys.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptionsController {
    @GetMapping("/collect_backups")
    public void collectBackups(){

    }

}
