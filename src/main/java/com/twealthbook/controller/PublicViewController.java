package com.twealthbook.controller;

import com.twealthbook.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableCaching
public class PublicViewController {

    @RequestMapping(value = "/access-denied")
    public String accessDenied(Model model){
        return "/access-denied";
    }

    @RequestMapping(value = "/")
    public String index(Model model){
        model.addAttribute("processingDate", ApiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Home");
        return "index";
    }

    @RequestMapping(value = "/userlogin", method = RequestMethod.GET)
    public String  login(Model model, String error, String logout){
        model.addAttribute("processingDate", ApiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Login");
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "userlogin";
    }

    @RequestMapping(value = "/public/info/assetclasses")
    public String assetClasses(Model model){
        model.addAttribute("processingDate", ApiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Asset Classes");
        return "public/info/assetclasses";
    }

    @RequestMapping(value = "/public/info/assetsubclasses")
    public String assetSubClasses(Model model){
        model.addAttribute("processingDate", ApiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Asset Sub Classes");
        return "public/info/assetsubclasses";
    }

    @RequestMapping(value = "/public/info/index/indexconstituents")
    public String indexConstituents(Model model){
        model.addAttribute("processingDate", ApiService.getProcessingDate());
        model.addAttribute("title", "Indices and Constituents");
        return "public/info/index/indexconstituents";
    }

}
