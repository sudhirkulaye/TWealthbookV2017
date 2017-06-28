package com.twealthbook.controller;

import com.twealthbook.model.Portfolio;
import com.twealthbook.service.TWealthbookApiService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@Api(name = "TWealthbook REST APIs",
        description =  "TWealthbook REST APIs",
        stage= ApiStage.RC)
public class TWealthbookRESTApiController {

    @Autowired
    TWealthbookApiService apiService;

    @RequestMapping(value = "/getallportfolios", method = RequestMethod.POST)
    @ApiMethod(description = "Get all portfolios of logged in client's family members")
    public Map<String, Portfolio> getAllPortfolios(){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println("apiService.getPortfoliosOfClientsOfALoggedInUser(userDetails):" + userDetails.getUsername());
        return apiService.getPortfoliosOfClientsOfALoggedInUser(userDetails);
    }

}
