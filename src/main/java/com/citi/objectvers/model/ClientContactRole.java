package com.citi.objectvers.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CLIENT_CONT_ROLE", schema = "ATISADMIN")
public class ClientContactRole {

    @Id
    @Column(name = "ROLE_NO", length = 10)
    private String roleNo;

    @Column(name = "CLIENT_REFNO", length = 9)
    private String clientRefNo;

    @Column(name = "ROLE_CODE", length = 20)
    private String roleCode;

    @Column(name = "INST_TYP_CODE", length = 20)
    private String instTypeCode;

    @Column(name = "CONTACT", length = 96)
    private String contact;

    @Column(name = "TEL", length = 30)
    private String tel;

    @Column(name = "EMAIL", length = 400)
    private String email;

    @Column(name = "TELEX", length = 50)
    private String telex;

    @Column(name = "TELEX_ANS_BAK", length = 50)
    private String telexAnsBak;

    @Column(name = "FAX", length = 100)
    private String fax;

    @Column(name = "SWIFT_ID", length = 12)
    private String swiftId;

    @Column(name = "FEDWIRE", length = 12)
    private String fedwire;

    @Column(name = "CHIPS", length = 3)
    private String chips;

    @Column(name = "SRT_SORT", length = 8)
    private String srtSort;

    @Column(name = "IS_NAME_ONLY", length = 1)
    private String isNameOnly;

    @Column(name = "STATUS", length = 4)
    private String status;

    @Column(name = "ACCESS_STAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accessStamp;

    @Column(name = "MAKER", length = 14)
    private String maker;

    @Column(name = "MAKER_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date makerDt;

    @Column(name = "CHECKER", length = 14)
    private String checker;

    @Column(name = "CHECKER_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkerDt;

    @Column(name = "IS_CHEQUE_PAYMENT_ALLOWED", length = 1)
    private String isChequePaymentAllowed;

    @Column(name = "IS_CLAIM_SUPPRESSED", length = 1)
    private String isClaimSuppressed;

    @Column(name = "PREFERENCE_MODE", length = 10)
    private String preferenceMode;

    @Column(name = "RATE_NFIX_NOTICE_PERIOD")
    private Integer rateNfixNoticePeriod;

    @Column(name = "TAX_TYPE", length = 20)
    private String taxType;

    @Column(name = "TAX_ID", length = 9)
    private String taxId;

    @Column(name = "VLT_LOCN", length = 4)
    private String vltLocn;

    @Column(name = "SWIFTNET_ADDR", length = 100)
    private String swiftnetAddr;

    @Column(name = "IS_SWIFT_CONNECTED", length = 1)
    private String isSwiftConnected;

    @Column(name = "MEMBER_ID", length = 4)
    private String memberId;

    @Column(name = "CREATION_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDt;

    @Column(name = "REF_REF_NO", length = 20)
    private String refRefNo;

    @Column(name = "TM_PRODUCT", length = 20)
    private String tmProduct;

    @Column(name = "IS_ROLE_CANCELLED", length = 1)
    private String isRoleCancelled;

    @Column(name = "CANCELLED_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelledDt;

    @Column(name = "CITI_CLIENT_REFNO", length = 9)
    private String citiClientRefNo;

    @Column(name = "FMSM_BRKDW", length = 1)
    private String fmsBreakdown;

    // Getters and Setters
}

