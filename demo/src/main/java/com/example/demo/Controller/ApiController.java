package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.BatteryRepo;
import com.example.demo.Model.Battery;
/*
 * The Controller for our API calls, here we will determine the functionality and behaviour of the HTTP endpoint calls made.
 * METHODS: load, getBattery, saveBattery
 */
@RestController
public class ApiController {
    @Autowired
    private BatteryRepo repo; 
    private double totalWattage; 

    /*
     * A default http endpoint to display that the service is up and running 
     */
    @GetMapping("/")
    public String load(){
        return "Welcome to battery analysis graphing tool v1.0";
    }

    /*
     * The parameter are two postcodes (int) to use as the lower and upper bounds for the battery search.
     * Should the list be empty or null, the user is told so. 
     */
    @GetMapping("/get")
    public String getBattery(@RequestParam int p1, @RequestParam int p2){
        StringBuilder temp = new StringBuilder(); 
        double averageWattage = 0.0;
        totalWattage = 0.0;
        List<Battery> tempList = null; 
        try{
            //Get the list stored in the repo, if it's empty or null, we will check for it here
            List<Battery> list = repo.findAll();
            //Filter out the batteries that are not within the ranges given by the user
            //Save the new list to a tempList to pass around 
            tempList = list.stream().filter(b -> (b.getPostcode() >= p1)&&(b.getPostcode() <= p2))
            .collect(Collectors.toList());
            if(tempList.size() != 0){
                //The tempList has all the batteries within range, and we append their details in a clean format
                tempList.forEach(n-> temp.append("++++++++++++++++++++++" +"\n"
                                        + "Name: " +String.valueOf(n.getName()) +"\n"
                                        + "Postcode: " + String.valueOf(n.getPostcode()) +"\n"
                                        + "Wattage: "+String.valueOf(n.getWattage()+ "\n" 
                                        + "\n")));
                //For all the batteries, get the sum of their wattage
                tempList.forEach(n -> totalWattage += n.getWattage());
                //For the sum of the wattage, get the average 
                averageWattage = totalWattage/Double.valueOf(tempList.size());
                //Append clean output for the user, although most of this could be done in a single line, it's easier to read like this
                temp.append("++++++++++++++++++++++" +"\n"+
                "Total Wattage: "+String.valueOf(totalWattage));
                temp.append("\n"+"Average Wattage: "+String.valueOf(averageWattage)); 
                temp.append("\n"+"Number of batteries: "+tempList.size());
                //If the postcodes are unrealistically out of range
            }else if((p1 <= 1000)||(p2 <1000)){
                temp.append("Error: Can't have a postcode range <1000");
            }
            else{
                temp.append("Error: No batteries in list");
            }
        }catch(Exception e){
            temp.append(e.toString());
        } 
        //return the string for display 
        return temp.toString();
    }
    /*
     * Save a list of Batteries in a JSON body to our database
     */
    @PostMapping(value = "/save")
    public String saveBattery(@RequestBody List<Battery> batteries){
        //should the user not actually put anything
        String temp = "List was null, please add batteries correctly";
        if(batteries.size() != 0 ){
            temp = "List was empty, please add batteries";
        }
        //if there is a null list
        if(batteries != null){
            try{
                //add each battery to our database
                batteries.forEach(battery -> repo.save(battery));
                temp = "batteries succesfully added";
            }catch (NullPointerException e){
                temp = "THERE WAS AN ERROR IN THE LIST: "+e.toString(); 
            }
        }
        //let the user know that it worked
        return temp; 
    }
}
