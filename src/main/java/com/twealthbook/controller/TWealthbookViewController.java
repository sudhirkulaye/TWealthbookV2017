package com.twealthbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class TWealthbookViewController {

    @Autowired
    public TWealthbookViewController(Environment environment){

    }

    @RequestMapping(value = "/")
    public String index(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Home");
        return "index";
    }

    /*
    @RequestMapping(value = "/userlogin")
    public ModelAndView login(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userlogin");
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Login");
        //return "userlogin";
        return modelAndView;
    }
    */

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Dashboard");
        model.addAttribute("username", userDetails.getUsername());
        return "dashboard";
    }

    @RequestMapping(value = "/wealth")
    public String wealth(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook 360° Wealth");
        return "wealth";
    }

    @RequestMapping(value = "/incomesavingsexpenses")
    public String familyincomesavingsexpenses(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Income,Savings & Expenses");
        return "incomesavingsexpenses";
    }

    @RequestMapping(value = "/portfolios", method = RequestMethod.GET)
    public String portfolios(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Portfolios");
        return "portfolios";
    }

    @RequestMapping(value = "/portfoliodetails")
    public String portfoliodetails(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Portfolio Details");
        return "portfoliodetails";
    }

    @RequestMapping(value = "/loans")
    public String loans(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Loans");
        return "loans";
    }

    @RequestMapping(value = "/insurances")
    public String insurances(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Insurance");
        return "insurances";
    }

    @RequestMapping(value = "/timeline")
    public String timeline(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Timeline");
        return "timeline";
    }

    @RequestMapping(value = "/rmdashboard")
    public String rmdashboard(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Dashboard - Relationship Manager");
        return "rmdashboard";
    }

}
