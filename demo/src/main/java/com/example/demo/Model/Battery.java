package com.example.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * A basic schema design for jpa to implement on our mysql database. 
 * We use @Id to indicate what the PRIMARY KEY (PK) will be 
 * @Column (name = "...") just indicated the title of each column in the schema 
 * Having getters and setters here should futher implmentation be desired 
 */
@Entity 
public class Battery {
    @Id
    @Column (name = "batteryID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //our PK for the DB, logically a BATTERY ID will be used here
    private long batteryID;
    @Column (name = "name")
    private String  name;
    @Column (name = "postcode") 
    private int postcode; 
    @Column (name = "wattage")
    private float wattage; 
    
    public long getBatteryID() {
        return batteryID;
    }

    public String getName() {
        return name;
    }

    public int getPostcode() {
        return postcode;
    }

    public float getWattage() {
        return wattage;
    }

    public void setBatteryID(long batteryID) {
        this.batteryID = batteryID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public void setWattage(float wattage) {
        this.wattage = wattage;
    }
}
