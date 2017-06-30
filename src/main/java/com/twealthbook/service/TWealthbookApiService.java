package com.twealthbook.service;

import com.twealthbook.model.*;
import com.twealthbook.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TWealthbookApiService {

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

    /**
     * Return true if Roles contains a ADMIN Role
     * @param roles
     * @return
     */
    private  boolean isHavingAdminRole(Set<Role> roles){
        for (Role role : roles){
            if (role.getRoleName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return  false;
    }

    /**
     * Return User Object for logged-in user
     * @param userDetails
     * @return
     * @throws UsernameNotFoundException
     */
    private User getLoggedInUser(UserDetails userDetails) throws  UsernameNotFoundException {
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
        return (List<PortfolioCashflow>) portfolioCashflowRepository.findAllByportfolioCashflowKeyClientIdAndPortfolioCashflowKeyPortfolioId(clientId, portfolioId);
    }

    public List<PortfolioHoldings> getPortfolioHoldings
            (@AuthenticationPrincipal final UserDetails userDetails, Long clientId, int portfolioId)
            throws UsernameNotFoundException{
        FamilyMember familyMember = familyMemberRepository.findOneByClientId(clientId);
        if (!familyMember.getUserLoginId().equals(userDetails.getUsername())) {
            throw new UsernameNotFoundException("Clinet is not a family memebr of user");
        }
        return (List<PortfolioHoldings>) portfolioHoldingsRepository.findAllByPortfolioHoldingsKeyClientIdAndPortfolioHoldingsKeyPortfolioId(clientId, portfolioId);
    }

}
