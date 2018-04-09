/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9fvinnsla;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Heiðdís Anna
 */
public class Booking {
    
    private Flight flight;
    private int numberAdults;
    private int numberChildren;
    private String bookingNumber;
    private List<Passenger> passengers;
    
    public Booking(Flight flight, List<Passenger> passengers){
        this.flight = flight;
        this.passengers = passengers;
        setBookingNumber();
        
        for(int i =0; i < passengers.size(); i++){
            if(passengers.get(i).isAdult()){
                numberAdults++;
            } else {
                numberChildren++;
            }
        }
    }
    
    private void setBookingNumber(){
        // Hvernig ætlum við að útfæra þetta?
    }
    
    // ATH þetta fall skilar dasetningu brottfarar
    public Date getDate(){
        return flight.getArrival();
    }
    
    public String getBookingNumber(){
        return bookingNumber;
    }
    
    public String getAirline(){
        return flight.getAirline();
    }
    
    
}