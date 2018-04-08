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
 * @author Hópur 9F
 */
public class Flight {
   
    private String airline;
    private String flightNumber;
    private String origin;
    private String destination;
    private int planeCapacity;
    private int adultPrice;
    private int childPrice;
    private long duration;
    private boolean disabilityAccess;
    private boolean animalTransfer;
    private Date departure;
    private Date arrival;
    private int handLuggagePrice;
    private int luggagePrice;
    private List<String> availableSeatList;
    
    public Flight(String airline, String flightNumber, String origin, String destination, 
            int planeCapacity, int adultPrice, int childPrice,Date departure, 
            Date arrival, int handLuggagePrice, int luggagePrice, 
            boolean disabilityAccess, boolean animalTransfer)
    {
        
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.planeCapacity = planeCapacity;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.departure = departure;
        this.arrival = arrival;
        this.handLuggagePrice = handLuggagePrice;
        this.luggagePrice = luggagePrice;
        this.disabilityAccess = disabilityAccess; 
        this.animalTransfer = animalTransfer;
        this.planeCapacity = planeCapacity;
        
        //setFlightNumber();
        //generateSeatList(planeCapacity);
    }
    
    public String getAirline(){
        return airline;
    }
    
    public String getFlightNumber(){
        return flightNumber;
    }
    
    /*private void setFlightNumber(){
        // Búum til flugnúmer
    }
    */
    public String getOrigin(){
        return origin;
    }
    
    public String getDestination(){
        return destination;
    }
    
    public Date getDeparture(){
        return departure;
    }
    
    public Date getArrival(){
        return arrival;
    }
    
    public int getPrice(int age){
        if(age <= 12){
            return childPrice;
        } else {
            return adultPrice;
        }
    }
    
    public int getHandLuggagePrice(){
        return handLuggagePrice;
    }
    
    public int getLuggagePrice(){
        return luggagePrice;
    }
    
    public boolean getDisabilityAccess(){
        return disabilityAccess;
    }
    
    public boolean getAnimalTransfer(){
        return animalTransfer;
    }
    
    public List<String> getAvailableSeatList(){
        return availableSeatList;
    }
    
    private void generateSeatList(int planeCapacity){
        availableSeatList = new ArrayList<String>();
        String letter = "";
        String[] letters = {"A", "B", "C", "D", "E", "F"};
        
        for(int i =0; i<planeCapacity/6; i++){
           for(int j=0; j<6; j++){
               letter = letters[j];
               availableSeatList.add(letter + i);
           }
        }   
    }
    
    public void remuveFromSeatList(String seatNumber){
        availableSeatList.remove(seatNumber);
    }
    
    public boolean checkIfFullyBooked(){
        return availableSeatList.isEmpty();
    }
    
    private void updatePrices(){
        if(availableSeatList.size()/planeCapacity <= 0.75){ 
            adultPrice = (int)((double)adultPrice*0.1);
        } else {
            if(availableSeatList.size()/planeCapacity <= 0.5){ 
                adultPrice = (int)((double)adultPrice*0.1);
            } else{
                if(availableSeatList.size()/planeCapacity <= 0.25){ 
                    adultPrice = (int)((double)adultPrice*0.1);
                }
            }
        }
    }
}
