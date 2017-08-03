package com.twealthbook.controller;

import com.twealthbook.model.SetupDates;
import com.twealthbook.repository.SetupDatesRepository;
import com.twealthbook.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Controller
@EnableCaching
public class UserViewController {

    @Autowired
    ApiService apiService;
    @Autowired
    SetupDatesRepository setupDatesRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserViewController.class);

    @Autowired
    public UserViewController(Environment environment){
    }

    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        logger.debug(String.format("New Login As: /%s", userDetails.getUsername()));
        if (apiService.isAdmin(userDetails)){
            return "redirect:admin/uploaddailydatag";
        }
        return "redirect:user/portfolios/";
    }

    @RequestMapping(value = "/user/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("processingDate", apiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Dashboard");
        String welcomeMessage = "Welcome "+ apiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "user/dashboard";
    }

    @RequestMapping(value = "/user/wealth")
    public String wealth(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("processingDate", apiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook 360° Wealth");
        String welcomeMessage = "Welcome "+ apiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "user/wealth";
    }

    @RequestMapping(value = "/user/incomesavingsexpenses")
    public String familyincomesavingsexpenses(Model model, @AuthenticationPrincipal final UserDetails userDetails){
        model.addAttribute("processingDate", apiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Income,Savings & Expenses");
        String welcomeMessage = "Welcome "+ apiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "user/incomesavingsexpenses";
    }

    @RequestMapping(value = "/user/portfolios", method = RequestMethod.GET)
    public String portfolios(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", apiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Portfolios");
        String welcomeMessage = "Welcome "+ apiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "user/portfolios";
    }


    @RequestMapping(value = "/user/loans")
    public String loans(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", apiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Loans");
        String welcomeMessage = "Welcome "+ apiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "user/loans";
    }

    @RequestMapping(value = "/user/insurances")
    public String insurances(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", apiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Insurance");
        String welcomeMessage = "Welcome "+ apiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "user/insurances";
    }

    @RequestMapping(value = "/user/timeline")
    public String timeline(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("processingDate", apiService.getProcessingDate());
        model.addAttribute("title", "TWealthbook Timeline");
        String welcomeMessage = "Welcome "+ apiService.getLoggedInUser(userDetails).getUserLastName() + " family";
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "user/timeline";
    }


}
