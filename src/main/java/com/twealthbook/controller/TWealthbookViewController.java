package com.twealthbook.controller;

import com.twealthbook.model.SetupDates;
import com.twealthbook.repository.SetupDatesRepository;
import com.twealthbook.service.TWealthbookApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@EnableCaching
public class TWealthbookViewController {

    @Autowired
    TWealthbookApiService tWealthbookApiService;
    @Autowired
    SetupDatesRepository setupDatesRepository;

    @Autowired
    public TWealthbookViewController(Environment environment){

    }

    private java.sql.Date getProcessingDate(){
        Iterable<SetupDates> setupDates = setupDatesRepository.findAll();
        //TODO: check only once entry
        java.sql.Date dateToday = new java.sql.Date((new java.util.Date()).getTime());
        for (SetupDates setupDate : setupDates) {
            dateToday = setupDate.getDateToday();
        }
        return dateToday;
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
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "dashboard";
    }

    @RequestMapping(value = "/wealth")
    public String wealth(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook 360° Wealth");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "wealth";
    }

    @RequestMapping(value = "/incomesavingsexpenses")
    public String familyincomesavingsexpenses(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Income,Savings & Expenses");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "incomesavingsexpenses";
    }

    @RequestMapping(value = "/portfolios", method = RequestMethod.GET)
    public String portfolios(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Portfolios");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "portfolios";
    }


    @RequestMapping(value = "/loans")
    public String loans(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Loans");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "loans";
    }

    @RequestMapping(value = "/insurances")
    public String insurances(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Insurance");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "insurances";
    }

    @RequestMapping(value = "/timeline")
    public String timeline(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Timeline");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "timeline";
    }

    @RequestMapping(value = "/rmdashboard")
    public String rmdashboard(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("datetime", new Date());
        model.addAttribute("title", "TWealthbook Dashboard - Relationship Manager");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "rmdashboard";
    }

}
