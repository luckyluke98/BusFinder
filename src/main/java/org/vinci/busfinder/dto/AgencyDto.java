package org.vinci.busfinder.dto;

public class AgencyDto {

    private String agencyId;
    private String agencyName;
    private String agencyUrl;
    private String agencyTimeZone;
    private String agencyLang;
    private String agencyPhone;
    private String agencyFareUrl;

    public AgencyDto() {}

    public AgencyDto(String agencyId, String agencyName, String agencyUrl, String agencyTimeZone, String agencyLang, String agencyPhone, String agencyFareUrl) {
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.agencyUrl = agencyUrl;
        this.agencyTimeZone = agencyTimeZone;
        this.agencyLang = agencyLang;
        this.agencyPhone = agencyPhone;
        this.agencyFareUrl = agencyFareUrl;
    }

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
