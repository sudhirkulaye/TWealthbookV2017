package com.twealthbook.controller;

import com.twealthbook.model.*;
import com.twealthbook.repository.SetupDatesRepository;
import com.twealthbook.service.TWealthbookApiService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(name = "TWealthbook REST APIs",
        description =  "TWealthbook REST APIs",
        stage= ApiStage.RC)
@EnableCaching
public class TWealthbookRESTApiController {

    @Autowired
    TWealthbookApiService apiService;
    @Autowired
    public SetupDatesRepository setupDatesRepository;

    public SetupDates setupDates;

    @RequestMapping(value = "/getsetupdates", method = RequestMethod.GET)
    @ApiMethod(description = "Get system dates")
    public SetupDates getSetupDates(){
        if (setupDates == null) {
            List<SetupDates> setupDatesList = setupDatesRepository.findAll();
            //TODO check only one object in the list
            for(SetupDates setupDatesListObject : setupDatesList) {
                setupDates = setupDatesListObject;
            }
        }
        return  setupDates;
    }

    @RequestMapping(value = "/getallportfolios", method = RequestMethod.GET)
    @ApiMethod(description = "Get all portfolios of logged in client's family members")
    public List<PortfolioViewModel> getAllPortfolios(){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return apiService.getPortfoliosOfClientsOfALoggedInUser(userDetails);
    }

    @RequestMapping(value = "/getportfoliocashflow/{clientId}/{portfolioId}", method = RequestMethod.GET)
    @ApiMethod(description = "Get portfolio's cashflow history upon request from UI")
    public List<PortfolioCashflow> getPortfolioCashflow(@PathVariable Long clientId, @PathVariable int portfolioId){
        System.out.println("/getPortfolioCashflow/" + clientId + "/" + portfolioId);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return apiService.getPortfolioCashflow(userDetails, clientId, portfolioId);
    }

    @RequestMapping(value = "/getportfolioholdings/{clientId}/{portfolioId}", method = RequestMethod.GET)
    @ApiMethod(description = "Get portfolio's Holdings upon request from UI")
    public List<PortfolioHoldings> getPortfolioHoldings(@PathVariable Long clientId, @PathVariable int portfolioId) {
        System.out.println("/getportfolioholdings/" + clientId + "/" + portfolioId);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return apiService.getPortfolioHoldings(userDetails, clientId, portfolioId);
    }

    @RequestMapping(value = "/getportfoliohistoricalholdings/{clientId}/{portfolioId}", method = RequestMethod.GET)
    @ApiMethod(description = "Get portfolio's Holdings upon request from UI")
    public List<PortfolioHistoricalHoldings> getPortfolioHistoricalHoldings(@PathVariable Long clientId, @PathVariable int portfolioId) {
        System.out.println("/getportfoliohistoricalholdings/" + clientId + "/" + portfolioId);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return apiService.getPortfolioHistoricalHoldings(userDetails, clientId, portfolioId);
    }

}
