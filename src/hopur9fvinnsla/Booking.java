/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9fvinnsla;

import java.time.LocalDate;
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
    private String cardHolder;
    private String cardNumber;
    private LocalDate expDay;
    private String csv;

    public Booking(Flight flight, List<Passenger> passengers, String cardHolder, String cardNumber, LocalDate expDay, String csv) {
        this.flight = flight;
        this.passengers = passengers;
        setBookingNumber();
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.expDay = expDay;
        this.csv = csv;

        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).isAdult()) {
                numberAdults++;
            } else {
                numberChildren++;
            }
        }
    }

    private void setBookingNumber() {
        // Hvernig ætlum við að útfæra þetta?
    }

    // ATH þetta fall skilar dasetningu brottfarar
    public Date getDate() {
        return flight.getArrival();
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public String getAirline() {
        return flight.getAirline();
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    
    public LocalDate getexpDay() {
        return expDay;
    }
    
    public String getCsv() {
        return csv;
    }

}
