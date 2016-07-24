package com.MinimalSoft.BrujulaUniversitaria.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Extra {

    private List<Integer> nearbyStationList = new ArrayList<Integer>();
    private String address;
    private String districtCode;
    private String status;
    private Integer uid;
    private String zip;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The nearbyStationList
     */
    public List<Integer> getNearbyStationList() {
        return nearbyStationList;
    }

    /**
     *
     * @param nearbyStationList
     * The NearbyStationList
     */
    public void setNearbyStationList(List<Integer> nearbyStationList) {
        this.nearbyStationList = nearbyStationList;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The districtCode
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     *
     * @param districtCode
     * The districtCode
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     *
     * @param uid
     * The uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     *
     * @return
     * The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     * The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
