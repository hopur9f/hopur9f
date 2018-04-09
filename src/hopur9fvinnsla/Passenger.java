/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9fvinnsla;

import java.time.LocalDate;
import java.time.Year;

/**
 *
 * @author Heiðdís Anna
 */
public class Passenger {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String residency;
    private int numberHandLuggage;
    private int numberLuggage;
    private String seatNumber;

    public Passenger(String firstName, String lastName, String email, LocalDate birthDate,
            String residency, int numberHanLuggage, int numberLuggage, String SeatNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.residency = residency;
        this.numberHandLuggage = numberHanLuggage;
        this.numberLuggage = numberLuggage;
        this.seatNumber = seatNumber;
    }

    public boolean isAdult() {
        int yearNow = Year.now().getValue();
        int birthYear = birthDate.getYear();

        if (yearNow - birthYear > 12) {
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        String name = firstName + lastName;
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthDate;
    }

    public int getNumberHandLuggage() {
        return numberHandLuggage;
    }

    public int getNumberLuggage() {
        return numberLuggage;
    }

    public String getSeatsNumber() {
        return seatNumber;
    }

    public int getId() {
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
}
