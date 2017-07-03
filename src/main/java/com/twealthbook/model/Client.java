package com.twealthbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

@Entity
@Table(name = "client")
public class Client implements Serializable{

    private Long clientId;
    private int clientActiveStatus; //'1-Active Fee Paying 2-Active Non Fee Paying 4-Inactive Closed'
    private String clientFirstName;
    private String clientMiddleName;
    private String clientLastName;
    private Date clientBrithDate;
    private int clientGender; //1: Male 2: Female 3: Other
    private int clientMaritalStatus; //1-Single, 2-Married, 3-Divorcee, 4-Widow
    private Date clientJoiningDate;
    private BigInteger clientCellNo;
    private String clientEmailId;
    private String clientPanCardNo;
    private String clientAadharCardNo;

    @Id
    @TableGenerator(name="SequenceNextHiValue", table = "Sequence_Next_Hi_Value", pkColumnName = "id", pkColumnValue = "client_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SequenceNextHiValue")
    @Column(name = "client_id")
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Column(name = "client_active_status")
    public int getClientActiveStatus() {
        return clientActiveStatus;
    }
    public void setClientActiveStatus(int clientActiveStatus) {
        this.clientActiveStatus = clientActiveStatus;
    }

    @Column(name = "client_first_name", nullable = false)
    public String getClientFirstName() {
        return clientFirstName;
    }
    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    @Column(name = "client_middle_name")
    public String getClientMiddleName() {
        return clientMiddleName;
    }
    public void setClientMiddleName(String clientMiddleName) {
        this.clientMiddleName = clientMiddleName;
    }

    @Column(name = "client_last_name")
    public String getClientLastName() {
        return clientLastName;
    }
    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    @Column(name = "client_birth_date")
    public Date getClientBrithDate() {
        return clientBrithDate;
    }
    public void setClientBrithDate(Date clientBrithDate) {
        this.clientBrithDate = clientBrithDate;
    }

    @Column(name = "client_gender")
    public int getClientGender() {
        return clientGender;
    }
    public void setClientGender(int clientGender) {
        this.clientGender = clientGender;
    }

    @Column(name = "client_marital_status")
    public int getClientMaritalStatus() {
        return clientMaritalStatus;
    }
    public void setClientMaritalStatus(int clientMaritalStatus) {
        this.clientMaritalStatus = clientMaritalStatus;
    }

    @Column(name = "client_joining_date")
    public Date getClientJoiningDate() {
        return clientJoiningDate;
    }
    public void setClientJoiningDate(Date clientJoiningDate) {
        this.clientJoiningDate = clientJoiningDate;
    }

    @Column(name = "client_cell_no")
    public BigInteger getClientCellNo() {
        return clientCellNo;
    }
    public void setClientCellNo(BigInteger clientCellNo) {
        this.clientCellNo = clientCellNo;
    }

    @Column(name = "client_email_id")
    public String getClientEmailId() {
        return clientEmailId;
    }
    public void setClientEmailId(String clientEmailId) {
        this.clientEmailId = clientEmailId;
    }

    @Column(name = "client_pan_card_no")
    public String getClientPanCardNo() {
        return clientPanCardNo;
    }
    public void setClientPanCardNo(String clientPanCardNo) {
        this.clientPanCardNo = clientPanCardNo;
    }

    @Column(name = "client_aadhar_card_no")
    public String getClientAadharCardNo() {
        return clientAadharCardNo;
    }
    public void setClientAadharCardNo(String clientAadharCardNo) {
        this.clientAadharCardNo = clientAadharCardNo;
    }

}
