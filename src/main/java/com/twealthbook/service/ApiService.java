package com.twealthbook.service;

import com.twealthbook.model.*;
import com.twealthbook.repository.*;
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
    private UserRepository userRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioCashflowRepository portfolioCashflowRepository;

    @Autowired
    private PortfolioHoldingsRepository portfolioHoldingsRepository;

    @Autowired
    private PortfolioHistoricalHoldingsRepository portfolioHistoricalHoldingsRepository;

    @Autowired
    public SetupDatesRepository setupDatesRepository;

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    private SetupDates setupDates;

    /**
     * Return single record in SetupDates
     * @return SeupDates
     */
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

    public java.sql.Date getProcessingDate(){
        return getSetupDates().getDateToday();
    }

    /**
     * Return true if Roles contains a ADMIN Role
     * @param roles
     * @return
     */
    private  boolean isHavingAdminRole(Set<Role> roles){
        for (Role role : roles){
            if (role.getRoleId() == ADMIN_ROLE_ID) {
                return true;
            }
        }
        return  false;
    }

    public boolean isAdmin(@AuthenticationPrincipal UserDetails userDetails){
        boolean isAdmin = false;
        Set<Role> roles = this.getLoggedInUser(userDetails).getRoles();
        return isHavingAdminRole(roles);
    }

    /**
     * Return User Object for logged-in user
     * @param userDetails
     * @return
     * @throws UsernameNotFoundException
     */
    public User getLoggedInUser(UserDetails userDetails) throws  UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserLoginId(userDetails.getUsername());

        optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("User Login Id Not Found"));

        return optionalUser.map(User::new).get();

    }

    /**
     * If logged-in user is assigned a ADMIN role then return information of all end users
     * @param userDetails
     * @return
     * @throws InsufficientAuthenticationException
     */
    protected Iterable<User> getAllUsers (@AuthenticationPrincipal final UserDetails userDetails)
            throws UsernameNotFoundException, InsufficientAuthenticationException {
        User user = getLoggedInUser(userDetails);
        boolean isAdminUser = isHavingAdminRole(user.getRoles());
        if (isAdminUser)
            return userRepository.findAll();
        else
            throw  new InsufficientAuthenticationException("User is not authorized");
    }

    /**
     * If logged-in user is assigned a ADMIN role then return information of all clients
     * @param userDetails
     * @return
     * @throws UsernameNotFoundException
     * @throws InsufficientAuthenticationException
     */
    protected Iterable<Client> getAllClients (@AuthenticationPrincipal final UserDetails userDetails)
            throws UsernameNotFoundException, InsufficientAuthenticationException {
        User user = getLoggedInUser(userDetails);
        boolean isAdminUser = isHavingAdminRole(user.getRoles());
        if (isAdminUser)
            return clientRepository.findAll();
        else
            throw new InsufficientAuthenticationException("User is not authorized");
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
     * Returns User Information of a Logged-in user
     * @param userDetails
     * @return
     * @throws UsernameNotFoundException
     */
    public User getLoggedinUserInfo(@AuthenticationPrincipal final UserDetails userDetails)
            throws UsernameNotFoundException{
        return getLoggedInUser(userDetails);
    }

    /**
     * Returns a map of all Clients combined as a Family Members of logged-in user.
     * Key of mapped object is Relationship with logged-in user.
     * @param userDetails
     * @return
     * @throws UsernameNotFoundException
     */
    public HashMap<String, Client> getFamilyMembersAsClientsOfALoggedInUser(
            @AuthenticationPrincipal final UserDetails userDetails)
            throws UsernameNotFoundException{
        User user = getLoggedInUser(userDetails);
        HashMap clients = new HashMap();
        for (FamilyMember  familyMember : user.getFamilyMembers()){
            Client client = clientRepository.findByClientId(familyMember.getClientId());
            clients.put(familyMember.getFamilyRelationship()+ ":"+ client.getClientFirstName(), client);
        }
        return clients;
    }

    /**
     * Returns a map of all Portfolios of all Clients combined as a Family Members of logged-in user.
     * Key of mapped object is Relationship with logged-in user.
     * @param userDetails
     * @return
     * @throws UsernameNotFoundException
     */
    public List<PortfolioViewModel> getPortfoliosOfClientsOfALoggedInUser(
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


    public List<PortfolioCashflow> getPortfolioCashflow
            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId)
            throws UsernameNotFoundException{
        FamilyMember familyMember = familyMemberRepository.findOneByClientId(clientId);
        if (!familyMember.getUserLoginId().equals(userDetails.getUsername())) {
            throw new UsernameNotFoundException("Clinet is not a family memebr of user");
        }
        return (List<PortfolioCashflow>) portfolioCashflowRepository.
                findAllByportfolioCashflowKeyClientIdAndPortfolioCashflowKeyPortfolioId(clientId, portfolioId);
    }

    public List<PortfolioHoldings> getPortfolioHoldings
            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId)
            throws UsernameNotFoundException{
        FamilyMember familyMember = familyMemberRepository.findOneByClientId(clientId);
        if (!familyMember.getUserLoginId().equals(userDetails.getUsername())) {
            throw new UsernameNotFoundException("Clinet is not a family memebr of user");
        }
        return (List<PortfolioHoldings>) portfolioHoldingsRepository.
                findAllByPortfolioHoldingsKeyClientIdAndPortfolioHoldingsKeyPortfolioId(clientId, portfolioId);
    }

    public List<PortfolioHistoricalHoldings> getPortfolioHistoricalHoldings
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

    public XIRRReturns getPortfolioXIRRReturns
            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId)
            throws UsernameNotFoundException{

        List<PortfolioCashflow> portfolioCashflows = getPortfolioCashflow(userDetails,clientId,portfolioId);

        XIRRReturns xirrReturns = new XIRRReturns();
        double[] payments = new double[portfolioCashflows.size()+1];
        Date[] days = new Date[portfolioCashflows.size()+1];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        XIRRCalculation xirrCalculation = new XIRRCalculation();

        for (int i = 0; i < portfolioCashflows.size(); i++) {
            PortfolioCashflow portfolioCashflow = portfolioCashflows.get(i);
            java.sql.Date date = portfolioCashflow.getPortfolioCashflowKey().getCashflowDate();
            try {
                days[i] = simpleDateFormat.parse(date.toString());
                payments[i] = portfolioCashflow.getCashflowAmount().doubleValue();
            } catch (ParseException e) {
                logger.error(String.format("Error in parsing date for /getportfolioxirrreturns/%s/%s", clientId, portfolioId));
                e.printStackTrace();
                return null;
            }
        }
        try {
            if (setupDates == null) getSetupDates();
            days[portfolioCashflows.size()] = simpleDateFormat.parse(setupDates.getDateToday().toString());
            Portfolio.PortfolioKey portfoliokey = new Portfolio.PortfolioKey();
            portfoliokey.setClientId(clientId);
            portfoliokey.setPortfolioId(portfolioId);
            payments[portfolioCashflows.size()] = portfolioRepository.findOne(portfoliokey).getPortfolioValue().doubleValue();
        } catch (ParseException e) {
            logger.error(String.format("Error in parsing dateToday for /getportfolioxirrreturns/%s/%s", clientId, portfolioId));
            e.printStackTrace();
            return  null;
        }

        BigDecimal returnsSinceIncpetion = new BigDecimal(XIRRCalculation.Newtons_method(0.1,payments,days));
        System.out.println("returnsSinceIncpetion: "+ returnsSinceIncpetion);
        xirrReturns.setReturnsSinceInception(returnsSinceIncpetion);
        return  xirrReturns;

    }
}
