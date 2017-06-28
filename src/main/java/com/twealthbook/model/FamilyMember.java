package com.twealthbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "family_member")
public class FamilyMember implements Serializable {

    private Long clientId;
    private String userLoginId;
    private String familyRelationship;
    private User user;

    public FamilyMember(){}

    @Id
    @Column(name = "client_id")
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Column(name = "user_login_id")
    public String getUserLoginId() {
        return userLoginId;
    }
    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    @Column(name = "family_relationship")
    public String getFamilyRelationship() {
        return familyRelationship;
    }
    public void setFamilyRelationship(String familyRelationship) {
        this.familyRelationship = familyRelationship;
    }

    @ManyToOne
    @JsonIgnore
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
