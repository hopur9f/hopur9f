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

    private int id;
    final private String airline;
    final private String flightNumber;
    final private String origin;
    final private String destination;
    private int planeCapacity;
    private int adultPrice;
    final private int childPrice;
    private int duration;
    final private boolean disabilityAccess;
    final private boolean animalTransfer;
    final private Date departure;
    final private Date arrival;
    final private int handLuggagePrice;
    final private int luggagePrice;
    private ArrayList<String> availableSeatList;

    public Flight(String airline, String flightNumber, String origin, String destination,
            int adultPrice, int childPrice, Date departure,
            Date arrival,int duration, int handLuggagePrice, int luggagePrice,
            boolean disabilityAccess, boolean animalTransfer) {

        this.airline = airline;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.departure = departure;
        this.arrival = arrival;
        this.duration = duration;
        this.handLuggagePrice = handLuggagePrice;
        this.luggagePrice = luggagePrice;
        this.disabilityAccess = disabilityAccess;
        this.animalTransfer = animalTransfer;

        //setFlightNumber();
        //generateSeatList(planeCapacity);
    }

    public String getAirline() {
        return airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    /*private void setFlightNumber(){
        // Búum til flugnúmer
    }
     */
    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public Date getDeparture() {
        return departure;
    }

    public Date getArrival() {
        return arrival;
    }

    public int getAdultPrice() {
        return adultPrice;
    }

    public int getChildPrice() {
        return childPrice;
    }

    public int getDuration() {
        return duration;
    }

    public int getId() {
        return id;
    }

    public int getPrice(int age) {
        if (age <= 12) {
            return childPrice;
        } else {
            return adultPrice;
        }
    }

    public int getHandLuggagePrice() {
        return handLuggagePrice;
    }

    public int getLuggagePrice() {
        return luggagePrice;
    }

    public boolean getDisabilityAccess() {
        return disabilityAccess;
    }

    public boolean getAnimalTransfer() {
        return animalTransfer;
    }

    public List<String> getAvailableSeatList() {
        return availableSeatList;
    }

    private void generateSeatList(int planeCapacity) {
        availableSeatList = new ArrayList<>();
        String letter;
        String[] letters = {"A", "B", "C", "D", "E", "F"};

        for (int i = 0; i < planeCapacity / 6; i++) {
            for (int j = 0; j < 6; j++) {
                letter = letters[j];
                availableSeatList.add(letter + i);
            }
        }
    }

    public void setAvailableSeatListByPlaneCapacity(int planeCapacity) {
        generateSeatList(planeCapacity);
    }

    public void setAvailableSeatList(ArrayList<String> availableSeatList) {
        this.availableSeatList = availableSeatList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void removeFromSeatList(String seatNumber) {
        availableSeatList.remove(seatNumber);  
    }

    public boolean checkIfFullyBooked() {
        return availableSeatList.isEmpty();
    }

    private void updatePrices() {
        if (availableSeatList.size() / planeCapacity <= 0.75) {
            adultPrice = (int) ((double) adultPrice * 0.1);
        } else {
            if (availableSeatList.size() / planeCapacity <= 0.5) {
                adultPrice = (int) ((double) adultPrice * 0.1);
            } else {
                if (availableSeatList.size() / planeCapacity <= 0.25) {
                    adultPrice = (int) ((double) adultPrice * 0.1);
                }
            }
        }
    }

    @Override
    public String toString() {
        String infoString = "Airline: " + this.airline + "\n"
                + "Flight number: " + this.flightNumber + "\n"
                + "Origin: " + this.origin + "\n"
                + "Destination: " + this.destination + "\n"
                + "AdultPrice: " + this.adultPrice + "\n"
                + "ChildPrice: " + this.childPrice + "\n"
                + "Departure: " + this.departure.toString() + "\n"
                + "Arrival: " + this.arrival.toString() + "\n"
                + "Duration: " + this.duration + "\n"
                + "Disability access: " + this.disabilityAccess + "\n"
                + "Animal transfer: " + this.animalTransfer + "\n"
                + "Handluggage price: " + this.handLuggagePrice + "\n"
                + "Luggage price: " + this.luggagePrice + "\n"
                + "Available seatlist: " + this.availableSeatList.toString() + "\n";
        return infoString;
    }
}
