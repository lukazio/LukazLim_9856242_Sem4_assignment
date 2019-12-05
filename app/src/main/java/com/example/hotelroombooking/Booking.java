package com.example.hotelroombooking;

public class Booking {
    private long id;
    private String owner;
    private int paid;
    private int floor,unit,price;

    //Default constructor
    public Booking(){}

    //Getters
    public long getId() {return id;}
    public String getOwner() {return owner;}
    public int isPaid() {return paid;}
    public int getPrice() {return price;}
    public int getFloor() {return floor;}
    public int getUnit() {return unit;}

    //Setters
    public void setId(long id) {this.id = id;}
    public void setOwner(String owner) {this.owner = owner;}
    public void setPrice(int price) {this.price = price;}
    public void setPaid(int paid) {this.paid = paid;}
    public void setFloor(int floor) {this.floor = floor;}
    public void setUnit(int unit) {this.unit = unit;}
}