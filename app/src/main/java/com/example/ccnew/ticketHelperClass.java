package com.example.ccnew;

public class ticketHelperClass  {
    String to;
    String from;
    String fare;
    int id;

    public ticketHelperClass() {
    }

    public ticketHelperClass(String to, String from, String fare, int id) {
        this.to = to;
        this.from = from;
        this.fare = fare;
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}