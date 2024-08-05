package org.vinci.busfinder.model;

import jakarta.persistence.*;

@Entity
@Table(name = "agency")
public class Agency {

    @Id
    @Column(name = "agency_id")
    private String agencyId;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "agency_url")
    private String agencyUrl;

    @Column(name = "agency_time_zone")
    private String agencyTimeZone;

    @Column(name = "agency_lang")
    private String agencyLang;

    @Column(name = "agency_phone")
    private String agencyPhone;

    @Column(name = "agency_fare_url")
    private String agencyFareUrl;

    public Agency() {}

    @Override
    public String toString() {
        return String.format(
                "Agency[agency_id=%s, agency_name=%s, agency_url=%s, agency_time_zone=%s, " +
                        "agency_lang=%s, agency_phone=%s, agency_fare_url=%s]",
                agencyId, agencyName, agencyUrl, agencyTimeZone, agencyLang, agencyPhone, agencyFareUrl
        );
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyUrl() {
        return agencyUrl;
    }

    public void setAgencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
    }

    public String getAgencyTimeZone() {
        return agencyTimeZone;
    }

    public void setAgencyTimeZone(String agencyTimeZone) {
        this.agencyTimeZone = agencyTimeZone;
    }

    public String getAgencyLang() {
        return agencyLang;
    }

    public void setAgencyLang(String agencyLang) {
        this.agencyLang = agencyLang;
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    public String getAgencyFareUrl() {
        return agencyFareUrl;
    }

    public void setAgencyFareUrl(String agencyFareUrl) {
        this.agencyFareUrl = agencyFareUrl;
    }
}
