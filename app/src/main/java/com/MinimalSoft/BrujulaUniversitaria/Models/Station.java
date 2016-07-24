package com.MinimalSoft.BrujulaUniversitaria.Models;

import java.util.HashMap;
import java.util.Map;

public class Station {

    private String _id;
    private String empty_slots;
    private String free_bikes;
    private String totalBikes;
    private Geo geo;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return _id;
    }

    /**
     *
     * @param _id
     * The _id
     */
    public void setId(String _id) {
        this._id = _id;
    }

    /**
     *
     * @return
     * The emptySlots
     */
    public String getEmptySlots() {
        return empty_slots;
    }

    /**
     *
     * @param empty_slots
     * The empty_slots
     */
    public void setEmptySlots(String empty_slots) {
        this.empty_slots = empty_slots;
    }

    /**
     *
     * @return
     * The free_bikes
     */
    public String getFreeBikes() {
        return free_bikes;
    }

    /**
     *
     * @param free_bikes
     * The free_bikes
     */
    public void setFreeBikes(String free_bikes) {
        this.free_bikes = free_bikes;
    }

    /**
     *
     * @return
     * The totalBikes
     */
    public String getTotalBikes() {
        return totalBikes;
    }

    /**
     *
     * @param totalBikes
     * The totalBikes
     */
    public void setTotalBikes(String totalBikes) {
        this.totalBikes = totalBikes;
    }
    /**
     *
     * @return
     * The geo
     */
    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
