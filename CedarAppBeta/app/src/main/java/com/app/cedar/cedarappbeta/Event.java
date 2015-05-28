package com.app.cedar.cedarappbeta;

import java.util.ArrayList;

/**
 * Created by Saboor Salaam on 11/29/2014.
 */
public class Event {

    public String name;
    public String major;
    public String description;
    public String date;
    public String location;
    public String type;

    public Event()
    {

    }

    public Event(String name, String major, String description, String date, String location, String type) {

        this.name = name;

        if(major == null || major.equals("")) {
            this.major = "computer science";
        }

        this.description = description;

        if(date == null || date.equals("")) {
            this.date = "2015-01-05 05:10";
        }
        this.location = location;
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

