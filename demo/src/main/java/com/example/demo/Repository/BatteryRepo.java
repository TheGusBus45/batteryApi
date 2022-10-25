package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.Battery;

//The interface for our DATABASE
public interface BatteryRepo extends JpaRepository<Battery, Long>{
    
}
