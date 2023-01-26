package com.example.railway.activities.Models;

public class trainbookmodel {
    String name;
    String destination;
    String city;
    String date;
    String trainnnum;
    String trainclass;
    String trainseats;
    String id;
    public trainbookmodel(){

    }

    public trainbookmodel(String name, String destination, String city, String date, String trainnnum, String trainclass, String trainseats, String id) {
        this.name = name;
        this.destination = destination;
        this.city = city;
        this.date = date;
        this.trainnnum = trainnnum;
        this.trainclass = trainclass;
        this.trainseats = trainseats;
        this.id = id;
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

    public String getTrainclass() {
        return trainclass;
    }

    public void setTrainclass(String trainclass) {
        this.trainclass = trainclass;
    }

    public String getTrainseats() {
        return trainseats;
    }

    public void setTrainseats(String trainseats) {
        this.trainseats = trainseats;
    }
}
