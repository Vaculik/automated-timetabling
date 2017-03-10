package cz.muni.fi.webapp.controller;

import cz.muni.fi.TimetablingApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {

    @Autowired
    private TimetablingApp timetablingApp;

    @RequestMapping("/")
    public String homepage() {
        timetablingApp.startProcessing();
        return "homepage";
    }
}
