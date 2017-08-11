package com.twealthbook.service;

import com.twealthbook.model.*;
import com.twealthbook.repository.*;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthBasicUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Service
@EnableCaching
public class ApiService {

    public static final int ADMIN_ROLE_ID = 1;
    @Autowired
    private static UserRepository userRepository;
    @Autowired
    public static SetupDatesRepository setupDatesRepository;
    @Autowired
    public static BenchmarkRepository benchmarkRepository;

    @Autowired
    private static FamilyMemberRepository familyMemberRepository;
    @Autowired
    private static ClientRepository clientRepository;
    @Autowired
    private static PortfolioRepository portfolioRepository;
    @Autowired
    private static PortfolioCashflowRepository portfolioCashflowRepository;
    @Autowired
    private static PortfolioHoldingsRepository portfolioHoldingsRepository;
    @Autowired
    private static PortfolioHistoricalHoldingsRepository portfolioHistoricalHoldingsRepository;
    @Autowired
    private static PortfolioTwrrSummaryRepository portfolioTwrrSummaryRepository;
    @Autowired
    private static PortfolioIrrSummaryRepository portfolioIrrSummaryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);
    private static SetupDates setupDates;

    @Autowired
    public void setSetupDatesRepository(SetupDatesRepository setupDatesRepository){
        ApiService.setupDatesRepository = setupDatesRepository;
    }

    @Autowired
    public  void setUserRepository(UserRepository userRepository){
        ApiService.userRepository = userRepository;
    }

    @Autowired
    public  void setBenchmarkRepository(BenchmarkRepository benchmarkRepository){
        ApiService.benchmarkRepository = benchmarkRepository;
    }

    @Autowired
    public void setFamilyMemberRepository(FamilyMemberRepository familyMemberRepository){
        ApiService.familyMemberRepository = familyMemberRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository){
        ApiService.clientRepository = clientRepository;
    }

    @Autowired
    public  void setPortfolioRepository(PortfolioRepository portfolioRepository){
        ApiService.portfolioRepository = portfolioRepository;
    }

    @Autowired
    public void setPortfolioCashflowRepository(PortfolioCashflowRepository  portfolioCashflowRepository){
        ApiService.portfolioCashflowRepository = portfolioCashflowRepository;
    }

    @Autowired
    public void setPortfolioHoldingsRepository(PortfolioHoldingsRepository portfolioHoldingsRepository){
        ApiService.portfolioHoldingsRepository = portfolioHoldingsRepository;
    }

    @Autowired
    public void setPortfolioHistoricalHoldingsRepository(PortfolioHistoricalHoldingsRepository portfolioHistoricalHoldingsRepository){
        ApiService.portfolioHistoricalHoldingsRepository = portfolioHistoricalHoldingsRepository;
    }

    @Autowired
    public void setPortfolioTwrrSummaryRepository(PortfolioTwrrSummaryRepository portfolioTwrrSummaryRepository){
        ApiService.portfolioTwrrSummaryRepository = portfolioTwrrSummaryRepository;
    }

    @Autowired
    public void setPortfolioIrrSummaryRepository(PortfolioIrrSummaryRepository portfolioIrrSummaryRepository){
        ApiService.portfolioIrrSummaryRepository = portfolioIrrSummaryRepository;
    }

    /**
     * Return single record in SetupDates
     * @return SeupDates
     */
    public static SetupDates getSetupDates(){
        List<SetupDates> setupDatesList = setupDatesRepository.findAll();
        //TODO check only one object in the list
        for(SetupDates setupDatesListObject : setupDatesList) {
            setupDates = setupDatesListObject;
        }
        return  setupDates;
    }

    public static java.sql.Date getProcessingDate(){
        return getSetupDates().getDateToday();
    }

    /**
     * Return true if Roles contains a ADMIN Role
     * @param roles
     * @return
     */
    private static  boolean isHavingAdminRole(Set<Role> roles){
        for (Role role : roles){
            if (role.getRoleId() == ADMIN_ROLE_ID) {
                return true;
            }
        }
        return  false;
    }

    public static boolean isAdmin(@AuthenticationPrincipal UserDetails userDetails){
        boolean isAdmin = false;
        Set<Role> roles = getLoggedInUser(userDetails).getRoles();
        return isHavingAdminRole(roles);
    }

    /**
     * Return User Object for logged-in user
     * @param userDetails
     * @return
     * @throws UsernameNotFoundException
     */
    public static User getLoggedInUser(UserDetails userDetails) throws  UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserLoginId(userDetails.getUsername());

        optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("User Login Id Not Found"));

        return optionalUser.map(User::new).get();

    }



    /**
     * Creates a new Client if logged-in user ia assigned a ADMIN role.
     * And returns updated Client Object (with clientId)
     * @param userDetails
     * @param client
     * @return
     * @throws UsernameNotFoundException
     * @throws InsufficientAuthenticationException
     */
    protected Client createANewClient (@AuthenticationPrincipal final UserDetails userDetails, Client client)
            throws UsernameNotFoundException, InsufficientAuthenticationException {
        User user = getLoggedInUser(userDetails);
        boolean isAdminUser = isHavingAdminRole(user.getRoles());
        if (isAdminUser) {
            clientRepository.save(client);
            return clientRepository.findByClientFirstNameAndClientMiddleNameAndClientLastName(
                    client.getClientFirstName(), client.getClientMiddleName(), client.getClientLastName());
        } else
            throw new InsufficientAuthenticationException("User is not authorized");
    }

    /**
     * Creates a new User if logged-in user ia assigned a ADMIN role.
     * And returns updated User Object (with userId)
     * @param userDetails
     * @param user
     * @return
     * @throws UsernameNotFoundException
     * @throws InsufficientAuthenticationException
     */
    protected User createANewUser (@AuthenticationPrincipal final UserDetails userDetails, User user)
            throws UsernameNotFoundException, InsufficientAuthenticationException {
        User loggedInUser = getLoggedInUser(userDetails);
        boolean isAdminUser = isHavingAdminRole(loggedInUser.getRoles());
        if (isAdminUser) {
            userRepository.save(user);
            // Assuming just saved user will not be null
            return userRepository.findByUserLoginId(user.getUserLoginId()).map(CustomUserDetails::new).get();
        } else
            throw new InsufficientAuthenticationException("User is not authorized");
    }

    /**
     * Returns a map of all Portfolios of all Clients combined as a Family Members of logged-in user.
     * Key of mapped object is Relationship with logged-in user.
     * @param userDetails
     * @return
     * @throws UsernameNotFoundException
     */
    public static List<PortfolioViewModel> getPortfoliosOfClientsOfALoggedInUser(
            @AuthenticationPrincipal final UserDetails userDetails)
            throws UsernameNotFoundException{
        User user = getLoggedInUser(userDetails);
        List<PortfolioViewModel> portfolioViewModels = new ArrayList<>();

        for (FamilyMember familyMember : user.getFamilyMembers()){
            Client client = clientRepository.findByClientId(familyMember.getClientId());
            String relationship = familyMember.getFamilyRelationship()+ ":"+ client.getClientFirstName();

            for (Portfolio portfolio : portfolioRepository.findAllByPortfolioKeyClientId(familyMember.getClientId())){
                PortfolioViewModel portfolioViewModel = new PortfolioViewModel(relationship, portfolio);
                portfolioViewModels.add(portfolioViewModel);
            }
        }
        return portfolioViewModels;
    }


    public static List<PortfolioCashflow> getPortfolioCashflow
            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId)
            throws UsernameNotFoundException{
        FamilyMember familyMember = familyMemberRepository.findOneByClientId(clientId);
        if (!familyMember.getUserLoginId().equals(userDetails.getUsername())) {
            throw new UsernameNotFoundException("Clinet is not a family memebr of user");
        }
        return (List<PortfolioCashflow>) portfolioCashflowRepository.
                findAllByportfolioCashflowKeyClientIdAndPortfolioCashflowKeyPortfolioId(clientId, portfolioId);
    }

    public static List<PortfolioHoldings> getPortfolioHoldings
            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId)
            throws UsernameNotFoundException{
        FamilyMember familyMember = familyMemberRepository.findOneByClientId(clientId);
        if (!familyMember.getUserLoginId().equals(userDetails.getUsername())) {
            throw new UsernameNotFoundException("Clinet is not a family memebr of user");
        }
        return (List<PortfolioHoldings>) portfolioHoldingsRepository.
                findAllByPortfolioHoldingsKeyClientIdAndPortfolioHoldingsKeyPortfolioId(clientId, portfolioId);
    }

    public static List<PortfolioHistoricalHoldings> getPortfolioHistoricalHoldings
            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId)
            throws UsernameNotFoundException{
        FamilyMember familyMember = familyMemberRepository.findOneByClientId(clientId);
        if (!familyMember.getUserLoginId().equals(userDetails.getUsername())) {
            throw new UsernameNotFoundException("Clinet is not a family memebr of user");
        }
        return (List<PortfolioHistoricalHoldings>) portfolioHistoricalHoldingsRepository.
                findAllByPortfolioHistoricalHoldingsKeyClientIdAndPortfolioHistoricalHoldingsKeyPortfolioId
                        (clientId, portfolioId);
    }

//    public static PortfolioIrrSummary getPortfolioInternalRateOfReturns
//            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId)
//            throws UsernameNotFoundException{
//        /**
//         *  getPortfolioCashflow will check link between family member.
//         *  So no need to check additionally if "Clinet is not a family memebr of user"
//          */
//        List<PortfolioCashflow> portfolioCashflows = getPortfolioCashflow(userDetails,clientId,portfolioId);
//
//        PortfolioIrrSummary portfolioIrrSummary = new PortfolioIrrSummary();
//        double[] payments = new double[portfolioCashflows.size()+1];
//        Date[] days = new Date[portfolioCashflows.size()+1];
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        XIRRCalculation xirrCalculation = new XIRRCalculation();
//
//        for (int i = 0; i < portfolioCashflows.size(); i++) {
//            PortfolioCashflow portfolioCashflow = portfolioCashflows.get(i);
//            java.sql.Date date = portfolioCashflow.getPortfolioCashflowKey().getCashflowDate();
//            try {
//                days[i] = simpleDateFormat.parse(date.toString());
//                payments[i] = portfolioCashflow.getCashflowAmount().doubleValue();
//            } catch (ParseException e) {
//                logger.error(String.format("Error in parsing date for /getportfolioxirrreturns/%s/%s", clientId, portfolioId));
//                e.printStackTrace();
//                return null;
//            }
//        }
//        try {
//            if (setupDates == null) getSetupDates();
//            days[portfolioCashflows.size()] = simpleDateFormat.parse(setupDates.getDateToday().toString());
//            Portfolio.PortfolioKey portfoliokey = new Portfolio.PortfolioKey();
//            portfoliokey.setClientId(clientId);
//            portfoliokey.setPortfolioId(portfolioId);
//            payments[portfolioCashflows.size()] = portfolioRepository.findOne(portfoliokey).getPortfolioValue().doubleValue();
//        } catch (ParseException e) {
//            logger.error(String.format("Error in parsing dateToday for /getportfolioxirrreturns/%s/%s", clientId, portfolioId));
//            e.printStackTrace();
//            return  null;
//        }
//
//        BigDecimal returnsSinceIncpetion = new BigDecimal(XIRRCalculation.Newtons_method(0.1,payments,days));
//        System.out.println("returnsSinceIncpetion: "+ returnsSinceIncpetion);
//        portfolioIrrSummary.setReturnsIrrSinceInception(returnsSinceIncpetion);
//        return portfolioIrrSummary;
//
//    }

    public static String getBenchmarkDescription(int benchmarkId){
        return  benchmarkRepository.findOneByBenchmarkId(benchmarkId).getBenchmarkDescription();
    }

    public static PortfolioIrrSummary getPortfolioInternalRateOfReturns
            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId, int benchmarkId )
            throws UsernameNotFoundException{
        FamilyMember familyMember = familyMemberRepository.findOneByClientId(clientId);
        if (!familyMember.getUserLoginId().equals(userDetails.getUsername())) {
            throw new UsernameNotFoundException("Clinet is not a family memebr of user");
        }
        return portfolioIrrSummaryRepository.findOneByPortfolioIrrSummaryKeyClientIdAndPortfolioIrrSummaryKeyPortfolioIdAndPortfolioIrrSummaryKeyBenchmarkId
                (clientId,portfolioId,benchmarkId);

    }

    public static PortfolioTwrrSummary getPortfolioTimeWeightedRateOfReturns
            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId,int benchmarkId)
            throws UsernameNotFoundException {
        FamilyMember familyMember = familyMemberRepository.findOneByClientId(clientId);
        if (!familyMember.getUserLoginId().equals(userDetails.getUsername())) {
            throw new UsernameNotFoundException("Clinet is not a family memebr of user");
        }
//        return portfolioTwrrSummaryRepository.findOneByPortfolioTwrrSummaryKeyClientIdAndPortfolioTwrrSummaryKeyPortfolioIdAndPortfolioTwrrSummaryKeyBenchmarkId
//                (clientId,portfolioId,benchmarkId);
        List<PortfolioTwrrSummary> portfolioTwrrSummaries = portfolioTwrrSummaryRepository.findAllByPortfolioTwrrSummaryKeyClientIdAndPortfolioTwrrSummaryKeyPortfolioId(clientId,portfolioId);
        for (PortfolioTwrrSummary portfolioTwrrSummary : portfolioTwrrSummaries) {
            if (portfolioTwrrSummary.getPortfolioTwrrSummaryKey().getBenchmarkId() == benchmarkId){
                return portfolioTwrrSummary;
            }
        }
        return new PortfolioTwrrSummary();
    }
}
