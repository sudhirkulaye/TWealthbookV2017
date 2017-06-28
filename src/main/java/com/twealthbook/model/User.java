package com.twealthbook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
//@Proxy(lazy = false)
@Table(name = "user")
public class User {

    private Long userId;
    private String userLoginId;
    private String userPassword;
    private int userActiveStatus; //'1-Active 2-Inactive Closed'
    private String userFirstName;
    private String userLastName;
    private Date userJoiningDate;
    private String userEmailId;
    private String userAddLine1;
    private String userAddLine2;
    private String userAddLine3;
    private String userCity;
    private String userState;
    private String userPIN;
    private BigInteger userContactNo;
    private BigInteger userContactNoAlternate;
    private Date userLastLoginTime;
    private Set<Role> roles;
    private List<FamilyMember> familyMembers;

    public User(){}
    public User(User user){
        this.userId = user.getUserId();
        this.userLoginId = user.getUserLoginId();
        this.userPassword = user.getUserPassword();
        this.userActiveStatus = user.getUserActiveStatus();
        this.userFirstName = user.getUserFirstName();
        this.userLastName = user.getUserLastName();
        this.userJoiningDate = user.getUserJoiningDate();
        this.userEmailId = user.getUserEmailId();
        this.userAddLine1 = user.getUserAddLine1();
        this.userAddLine2 = user.getUserAddLine2();
        this.userAddLine3 = user.getUserAddLine3();
        this.userCity = user.getUserCity();
        this.userState = user.getUserState();
        this.userPIN = user.getUserPIN();
        this.userContactNo = user.getUserContactNo();
        this.userContactNoAlternate = user.getUserContactNoAlternate();
        this.userLastLoginTime = user.getUserLastLoginTime();
        this.roles = user.getRoles();
        this.familyMembers = user.getFamilyMembers();
    }

    @Id
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "user_login_id", nullable = false)
    public String getUserLoginId() {
        return userLoginId;
    }
    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    @Column(name = "user_Password", nullable = false)
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Column(name = "user_active_status")
    public int getUserActiveStatus() {
        return userActiveStatus;
    }
    public void setUserActiveStatus(int userActiveStatus) {
        this.userActiveStatus = userActiveStatus;
    }

    @Column(name = "user_first_name", nullable = false)
    public String getUserFirstName() {
        return userFirstName;
    }
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @Column(name = "user_last_name", nullable = false)
    public String getUserLastName() {
        return userLastName;
    }
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    @Column(name = "user_joining_date")
    public Date getUserJoiningDate() {
        return userJoiningDate;
    }
    public void setUserJoiningDate(Date userJoiningDate) {
        this.userJoiningDate = userJoiningDate;
    }

    @Column(name = "user_emailid", nullable = false)
    public String getUserEmailId() {
        return userEmailId;
    }
    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    @Column(name = "user_add_line1")
    public String getUserAddLine1() {
        return userAddLine1;
    }
    public void setUserAddLine1(String userAddLine1) {
        this.userAddLine1 = userAddLine1;
    }

    @Column(name = "user_add_line2")
    public String getUserAddLine2() {
        return userAddLine2;
    }
    public void setUserAddLine2(String userAddLine2) {
        this.userAddLine2 = userAddLine2;
    }

    @Column(name = "user_add_line3")
    public String getUserAddLine3() {
        return userAddLine3;
    }
    public void setUserAddLine3(String userAddLine3) {
        this.userAddLine3 = userAddLine3;
    }

    @Column(name = "user_city")
    public String getUserCity() {
        return userCity;
    }
    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    @Column(name = "user_state")
    public String getUserState() {
        return userState;
    }
    public void setUserState(String userState) {
        this.userState = userState;
    }

    @Column(name = "user_pin")
    public String getUserPIN() {
        return userPIN;
    }
    public void setUserPIN(String userPIN) {
        this.userPIN = userPIN;
    }

    @Column(name = "user_contact_no", nullable = false)
    public BigInteger getUserContactNo() {
        return userContactNo;
    }
    public void setUserContactNo(BigInteger userContactNo) {
        this.userContactNo = userContactNo;
    }

    @Column(name = "user_contact_no_alternate")
    public BigInteger getUserContactNoAlternate() {
        return userContactNoAlternate;
    }
    public void setUserContactNoAlternate(BigInteger userContactNoAlternate) {
        this.userContactNoAlternate = userContactNoAlternate;
    }

    @Column(name = "user_last_login_time")
    public Date getUserLastLoginTime() {
        return userLastLoginTime;
    }
    public void setUserLastLoginTime(Date userLastLoginTime) {
        this.userLastLoginTime = userLastLoginTime;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    //@JoinColumn(name = "user_id")
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(targetEntity = FamilyMember.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }
    public void setFamilyMembers(List<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

}
