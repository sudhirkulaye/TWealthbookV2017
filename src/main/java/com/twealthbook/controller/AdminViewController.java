package com.twealthbook.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twealthbook.model.CompanyDailyDataB;
import com.twealthbook.model.CompanyDailyDataG;
import com.twealthbook.repository.CompanyDailyDataBRepository;
import com.twealthbook.repository.CompanyDailyDataGRepository;
import com.twealthbook.repository.MutualFundRepository;
import com.twealthbook.service.CSVUtils;
import com.twealthbook.service.ApiService;
import com.twealthbook.thirdpartydata.DailyDataB;
import com.twealthbook.model.MutualFund;
import com.twealthbook.thirdpartydata.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Controller
public class AdminViewController {
    public static final String AP_PROCESS_DAILY_DATA = "ap_process_daily_data";

    @Autowired
    CompanyDailyDataBRepository companyDailyDataBRepository;
    @Autowired
    CompanyDailyDataGRepository companyDailyDataGRepository;
    @Autowired
    MutualFundRepository mutualFundRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AdminViewController(Environment environment){ }

    @RequestMapping(value = "/admin/uploaddailydatag",method=RequestMethod.GET)
    public  String uploadDailyDataFromG(@AuthenticationPrincipal UserDetails userDetails){
        if (ApiService.isAdmin(userDetails)) {
            return "admin/uploaddailydatag";
        } else {
            return "access-denied";
        }
    }

    @RequestMapping(value=("/admin/uploaddailydatagstatus"),headers=("content-type=multipart/*"),method=RequestMethod.POST)
    public String uploadDailyDataGStatus (Model model, @RequestParam("file") MultipartFile file, @RequestParam("date") String date){
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "redirect:admin/uploaddailydatag";
        }
        if (date.isEmpty()) {
            model.addAttribute("message", "Please enter valid date");
            return "redirect:admin/uploaddailydatag";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date companyDailyDate = null;
        try {
            companyDailyDate = new java.sql.Date(format.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            File csvFile = new File(file.getOriginalFilename());
            csvFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(csvFile);
            fos.write(file.getBytes());
            fos.close();
//            file.transferTo(csvFile);
            Scanner scanner = new Scanner(csvFile);
            boolean isHeader = true;
            CSVUtils csvUtils = new CSVUtils();
            List<CompanyDailyDataG> companyDailyDataGS = new ArrayList<>();
            while (scanner.hasNext()) {
                List<String> line = csvUtils.parseLine(scanner.nextLine());
                if(isHeader){
                    isHeader = false;
                    continue;
                }
                String companyTicker = line.get(1);
                String companyShortName = line.get(0);
                BigDecimal companyDailyLastPrice = line.get(2) != null && !line.get(2).isEmpty() ? new BigDecimal(line.get(2)) : new BigDecimal(0);
                BigDecimal companyDailyChange = line.get(3) != null && !line.get(3).isEmpty() ? new BigDecimal(line.get(3)) : new BigDecimal(0);
                BigDecimal companyDailyMarketCap = line.get(4) != null && !line.get(4).isEmpty() ? new BigDecimal(line.get(4)) : new BigDecimal(0);
                BigDecimal companyDailyVolume = line.get(5) != null && !line.get(5).isEmpty() ? new BigDecimal(line.get(5)) : new BigDecimal(0);
                BigDecimal companyDailyOpenPrice = line.get(6) != null && !line.get(6).isEmpty() ? new BigDecimal(line.get(6)) : new BigDecimal(0);
                BigDecimal companyDailyHighPrice = line.get(7) != null && !line.get(7).isEmpty() ? new BigDecimal(line.get(7)) : new BigDecimal(0);
                BigDecimal companyDailyLowPrice = line.get(8) != null && !line.get(8).isEmpty() ? new BigDecimal(line.get(8)) : new BigDecimal(0);
                BigDecimal companyDailyVolumeNo = companyDailyVolume.multiply(companyDailyLastPrice);
                BigDecimal companyDailyRupeeVolume = companyDailyVolume.multiply(companyDailyLastPrice);
                BigDecimal companyDailyMarketCapNo = companyDailyMarketCap.multiply(new BigDecimal(1000000));
                BigDecimal companyDailyMarketCapRank = new BigDecimal(0);

                CompanyDailyDataG companyDailyDataG = new CompanyDailyDataG(companyTicker,
                        companyDailyDate,
                        companyShortName,
                        companyDailyLastPrice,
                        companyDailyChange,
                        companyDailyMarketCap,
                        companyDailyVolume,
                        companyDailyOpenPrice,
                        companyDailyHighPrice,
                        companyDailyLowPrice,
                        companyDailyVolumeNo,
                        companyDailyRupeeVolume,
                        companyDailyMarketCapNo,
                        companyDailyMarketCapRank
                );
                companyDailyDataGS.add(companyDailyDataG);
            }
            companyDailyDataGS.sort(Comparator.comparing(CompanyDailyDataG::getCompanyDailyMarketCapNo).reversed());
            companyDailyDataGRepository.save(companyDailyDataGS);
            scanner.close();
            csvFile.delete();

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "admin/uploaddailydatag";
    }

    @RequestMapping(value = "/admin/uploaddailydatab",method= RequestMethod.GET)
    public  String uploadDailyDataFromB(@AuthenticationPrincipal UserDetails userDetails){
        if (ApiService.isAdmin(userDetails)) {
            return "admin/uploaddailydatab";
        } else {
            return "access-denied";
        }
    }

    @RequestMapping(value=("/admin/uploaddailydatabstatus"),headers=("content-type=multipart/*"),method=RequestMethod.POST)
    public String uploadDailyDataBStatus (Model model, @RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "redirect:admin/uploaddailydatab";
        }
        try {
            byte[] bytes = file.getBytes();
            String jsonAsString = new String(bytes);

            ObjectMapper jsonMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            DailyDataB dailyDataB = jsonMapper.readValue(jsonAsString, DailyDataB.class);
            assertNotNull(dailyDataB);

            List<CompanyDailyDataB> companyDailyDataBList = new ArrayList<>();
            for(Ticker ticker : dailyDataB.getTickers()){
                CompanyDailyDataB companyDailyDataB = new CompanyDailyDataB(ticker);
                companyDailyDataBList.add(companyDailyDataB);
//                companyDailyDataBRepository.save(companyDailyDataB);
            }
            companyDailyDataBList.sort(Comparator.comparing(CompanyDailyDataB::getCompanyDailyMarketCap).reversed());
            companyDailyDataBRepository.save(companyDailyDataBList);

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "admin/uploaddailydatab";
    }

    @RequestMapping(value = "/admin/uploaddailydatamf",method= RequestMethod.GET)
    public  String uploadDailyDataFromMF(@AuthenticationPrincipal UserDetails userDetails){
        if (ApiService.isAdmin(userDetails)) {
            return "admin/uploaddailydatamf";
        } else {
            return "access-denied";
        }
    }

    @RequestMapping(value=("/admin/uploaddailydatamfstatus"),headers=("content-type=multipart/*"),method=RequestMethod.POST)
    public String uploadDailyDataMFStatus (Model model, @RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "redirect:admin/uploaddailydatamf";
        }

        try {
            File csvFile = new File(file.getOriginalFilename());
            csvFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(csvFile);
            fos.write(file.getBytes());
            fos.close();
//            file.transferTo(csvFile);
            Scanner scanner = new Scanner(csvFile);
            boolean isHeader = true;
            CSVUtils csvUtils = new CSVUtils();
            List<MutualFund> mutualFunds = new ArrayList<>();
            while (scanner.hasNext()) {
                List<String> line = csvUtils.parseLine(scanner.nextLine(),';');
                if(isHeader){
                    isHeader = false;
                    continue;
                }
                if(line.isEmpty() || line.size() != 6){ //Earlier it was 8
                    continue;
                }

                MutualFund mutualFund = new MutualFund(line);
                mutualFunds.add(mutualFund);
            }
            mutualFunds.sort(Comparator.comparing(l->l.getMutualFundKey().getSchemeCode()));
            mutualFundRepository.deleteAllInBatch();
            mutualFundRepository.save(mutualFunds);
            scanner.close();
            csvFile.delete();

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "admin/uploaddailydatamf";
    }

    @RequestMapping(value = "/admin/processdailydata",method= RequestMethod.GET)
    public  String processDailyData(@AuthenticationPrincipal UserDetails userDetails){
        if (ApiService.isAdmin(userDetails)) {
            return "admin/processdailydata";
        } else {
            return "access-denied";
        }
    }

    @RequestMapping(value=("/admin/processdailydatastatus"),method=RequestMethod.POST)
    public String processDailyDataStatus(Model model, @RequestParam("confirmation") String confirmation){
        if (confirmation.equalsIgnoreCase("yes")) {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(AP_PROCESS_DAILY_DATA);
            boolean result = storedProcedure.execute();
            if (!result) {
                model.addAttribute("message", "Successfully processed daily data");
            } else {
                model.addAttribute("message", "Failed to process daily data successfully. Check DB logs.");
            }
            return "admin/processdailydata";
        } else {
            model.addAttribute("message", "Please confirm Yes/No to process daily data");
            return "redirect:admin/processdailydata";
        }
    }

    @RequestMapping(value = "/admin/updateportfolios",method= RequestMethod.GET)
    public  String updatePortfolios(@AuthenticationPrincipal UserDetails userDetails){
        if (ApiService.isAdmin(userDetails)) {
            return "admin/updateportfolios";
        } else {
            return "access-denied";
        }
    }

    @RequestMapping(value=("/admin/updateportfoliosstatus"),method=RequestMethod.POST)
    public String updatePortfoliosStatus(Model model, @RequestParam("confirmation") String confirmation) {
        if (confirmation.equalsIgnoreCase("yes")) {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("ap_update_portfolios");
            boolean result = storedProcedure.execute();
            //List<Object[]> storedProcedureResults = storedProcedure.getResultList();
            //result = (boolean) storedProcedureResults.get(0)[0];
            if (!result) {
                model.addAttribute("message", "Successfully updated portfolios data");
            } else {
                model.addAttribute("message", "Failed to update portfolio data. Check DB logs.");
            }
            return "admin/updateportfolios";
        } else {
            model.addAttribute("message", "Please confirm Yes to process daily data");
            return "redirect:admin/updateportfolios";
        }
    }


}
