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
@RestController
public class ApiController {
    @Autowired
    private BatteryRepo repo; 
    private double totalWattage; 

    @GetMapping("/")
    public String load(){
        return "Welcome to battery analysis graphing tool v1.0";
    }

   
    @GetMapping("/get")
    public String getBattery(@RequestParam int p1, @RequestParam int p2){
        StringBuilder temp = new StringBuilder(); 
        double averageWattage = 0.0;
        totalWattage = 0.0;
        List<Battery> tempList = null; 
        try{
            List<Battery> list = repo.findAll();
            tempList = list.stream().filter(b -> (b.getPostcode() >= p1)&&(b.getPostcode() <= p2))
            .collect(Collectors.toList());
            if(tempList.size() != 0){
                tempList.forEach(n-> temp.append("++++++++++++++++++++++" +"\n"
                                        + "Name: " +String.valueOf(n.getName()) +"\n"
                                        + "Postcode: " + String.valueOf(n.getPostcode()) +"\n"
                                        + "Wattage: "+String.valueOf(n.getWattage()+ "\n" 
                                        + "++++++++++++++++++++++"
                                        + "\n")));
                tempList.forEach(n -> totalWattage += n.getWattage());
                averageWattage = totalWattage/Double.valueOf(tempList.size());
                temp.append("Total Wattage: "+String.valueOf(totalWattage));
                temp.append("\n"+"Average Wattage: "+String.valueOf(averageWattage)); 
                temp.append("\n"+"Number of batteries: "+tempList.size());
            }else{
                temp.append("Error: No");
            }
        }catch(Exception e){
            temp.append(e.toString());
        } 
        return temp.toString();
    }

    @PostMapping(value = "/save")
    public String saveBattery(@RequestBody List<Battery> batteries){
    
        String temp = "List was null, please add batteries correctly";
        if(batteries.size() != 0 ){
            temp = "List was empty, please add batteries";
        }
        if(batteries != null){
            try{
                batteries.forEach(battery -> repo.save(battery));
                temp = "batteries succesfully added";
            }catch (NullPointerException e){
                temp = "THERE WAS AN ERROR IN THE LIST: "+e.toString(); 
            }
        }
        return temp; 
    }
}
