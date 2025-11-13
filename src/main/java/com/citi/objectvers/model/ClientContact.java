package com.citi.objectvers.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CCLIENT_CONT", schema = "ATISADMIN")
public class ClientContact {

    @Id
    @Column(name = "CLIENT_REFNO", length = 9)
    private String clientRefNo;

    @Column(name = "LCN", length = 7)
    private String lcn;

    @Column(name = "ADDR", length = 300)
    private String addr;

    @Column(name = "COUNTRY_CODE", length = 3)
    private String countryCode;

    @Column(name = "SOV_COUNTRY_CODE", length = 3)
    private String sovCountryCode;

    @Column(name = "NAME", length = 150)
    private String name;

    @Column(name = "ADDR1", length = 150)
    private String addr1;

    @Column(name = "ADDR2", length = 150)
    private String addr2;

    @Column(name = "ADDR3", length = 150)
    private String addr3;

    @Column(name = "ADDR4", length = 150)
    private String addr4;

    @Column(name = "ADDR5", length = 300)
    private String addr5;

    @Column(name = "BOE_GROUP", length = 1)
    private String boeGroup;

    @Column(name = "IS_BUILD_SOC", length = 1)
    private String isBuildSoc;

    @Column(name = "IS_UK_AUTH_INST", length = 1)
    private String isUkAuthInst;

    @Column(name = "GFCID", length = 10)
    private String gfcid;

    @Column(name = "NOMINEE_CLIENT_REFNO", length = 9)
    private String nomineeClientRefNo;

    @Column(name = "DOL_NO", length = 20)
    private String dolNo;

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

    @Column(name = "SAFEKACC", length = 10)
    private String safekacc;

    @Column(name = "MEMBER_ID", length = 4)
    private String memberId;

    @Column(name = "BASE_NUMBER", length = 10)
    private String baseNumber;

    @Column(name = "CREATION_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDt;

    @Column(name = "IS_CLIENT_CANCELLED", length = 1)
    private String isClientCancelled;

    @Column(name = "CANCELLED_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelledDt;

    @Column(name = "LEI_CODE", length = 20)
    private String leiCode;

    @Column(name = "IS_CITI_CLIENT", length = 1)
    private String isCitiClient;

}
