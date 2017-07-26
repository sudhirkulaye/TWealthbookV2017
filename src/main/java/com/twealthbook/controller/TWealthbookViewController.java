package com.twealthbook.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twealthbook.model.SetupDates;
import com.twealthbook.repository.SetupDatesRepository;
import com.twealthbook.service.TWealthbookApiService;
import com.twealthbook.thirdpartydata.DailyDataFromB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

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

    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin/uploaddailydatafromb";
        }
        return "redirect:/user/dashboard/";
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
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Home");
        return "index";
    }

    @RequestMapping(value = "/public/info/assetclasses")
    public String assetClasses(Model model){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Asset Classes");
        return "/public/info/assetclasses";
    }

    @RequestMapping(value = "/public/info/assetsubclasses")
    public String assetSubClasses(Model model){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Asset Sub Classes");
        return "/public/info/assetsubclasses";
    }

    @RequestMapping(value = "/public/info/index/indexconstituents")
    public String indexConstituents(Model model){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "Indices and Constituents");
        return "/public/info/index/indexconstituents";
    }

    @RequestMapping(value = "/userlogin", method = RequestMethod.GET)
    public String  login(Model model, String error, String logout){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Login");
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "userlogin";
    }

//    @RequestMapping(value = "/access-denied")
//    public String accessDenied(Model model){
//        return "access-denied";
//    }


    @RequestMapping(value = "/admin/uploaddailydatafromb",method=RequestMethod.GET)
    public  String uploadDailyDataFromB(){
       return "/admin/uploaddailydatafromb";
    }

    @RequestMapping(value=("/admin/uploadstatus"),headers=("content-type=multipart/*"),method=RequestMethod.POST)
    public String uploadStatus (Model model, @RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "redirect:/admin/uploaddailydatafromb";
        }
        try {

            byte[] bytes = file.getBytes();
            String jsonAsString = new String(bytes);

            ObjectMapper jsonMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            DailyDataFromB dailyDataFromBn = jsonMapper.readValue(jsonAsString, DailyDataFromB.class);
            assertNotNull(dailyDataFromBn);

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/admin/uploaddailydatafromb";
    }

    @RequestMapping(value = "/user/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Dashboard");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "/user/dashboard";
    }

    @RequestMapping(value = "/user/wealth")
    public String wealth(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook 360° Wealth");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "/user/wealth";
    }

    @RequestMapping(value = "/user/incomesavingsexpenses")
    public String familyincomesavingsexpenses(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Income,Savings & Expenses");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "/user/incomesavingsexpenses";
    }

    @RequestMapping(value = "/user/portfolios", method = RequestMethod.GET)
    public String portfolios(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Portfolios");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "/user/portfolios";
    }


    @RequestMapping(value = "/user/loans")
    public String loans(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Loans");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "/user/loans";
    }

    @RequestMapping(value = "/user/insurances")
    public String insurances(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Insurance");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "/user/insurances";
    }

    @RequestMapping(value = "/user/timeline")
    public String timeline(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Timeline");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "/user/timeline";
    }

    @RequestMapping(value = "/admin/rmdashboard")
    public String rmdashboard(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", getProcessingDate());
        model.addAttribute("title", "TWealthbook Dashboard - Relationship Manager");
        String welcomeMessage = "Welcome "+ tWealthbookApiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "/admin/rmdashboard";
    }

}
