package com.alexkasko.robots.controller;

import com.alexkasko.robots.service.ActivityTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class WebController {

    private final ActivityTracker activityTracker;

    @Autowired
    public WebController(ActivityTracker activityTracker) {
        this.activityTracker = activityTracker;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("messagelogs", activityTracker.getMessageLog());
        return "index";
    }

    @GetMapping("/logview")
    public String getLogs(Model model) {
        model.addAttribute("messagelogs", activityTracker.getMessageLog());
        return "/WEB-INF/views/logview";
    }
}
