package org.vinci.busfinder.dto;

public class RouteDto {

    private int routeId;
    private String agencyId;
    private String routeShortName;
    private String routeLongName;
    private String routeDesc;
    private String routeType;
    private String routeUrl;
    private String routeColor;
    private String routeTextColor;

    public RouteDto() {}

    public RouteDto(String routeTextColor, String routeColor, String routeUrl, String routeType, String routeDesc, String routeLongName, String routeShortName, String agencyId, int routeId) {
        this.routeTextColor = routeTextColor;
        this.routeColor = routeColor;
        this.routeUrl = routeUrl;
        this.routeType = routeType;
        this.routeDesc = routeDesc;
        this.routeLongName = routeLongName;
        this.routeShortName = routeShortName;
        this.agencyId = agencyId;
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return String.format(
                "RouteDto[route_id=%d, route_short_name=%s, route_long_name=%s, agency_id=%s, route_desc=%s, route_type=%s, route_url=%s, route_color=%s, route_text_color=%s]",
                routeId, routeShortName, routeLongName, agencyId, routeDesc, routeType, routeUrl, routeColor, routeTextColor
        );
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public String getRouteTextColor() {
        return routeTextColor;
    }

    public void setRouteTextColor(String routeTextColor) {
        this.routeTextColor = routeTextColor;
    }
}
