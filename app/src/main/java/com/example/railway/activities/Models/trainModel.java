package com.example.railway.activities.Models;

public class trainModel {
    String name;
    String destination;
    String city;
    String date;
    String trainnnum;
    String id;

    public trainModel(String name, String destination, String city, String date, String trainnnum, String id) {
        this.name = name;
        this.destination = destination;
        this.city = city;
        this.date = date;
        this.trainnnum = trainnnum;
        this.id = id;
    }

    public trainModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrainnnum() {
        return trainnnum;
    }

    public void setTrainnnum(String trainnnum) {
        this.trainnnum = trainnnum;
    }
}
