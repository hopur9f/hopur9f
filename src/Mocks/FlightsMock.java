/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import hopur9f.Flight;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author sigrundish
 */
public class FlightsMock {
    

    void FlightsMock() {
    }

    public List getFlights(){
        List<Flight> flights = new ArrayList<Flight>();
        List<String> availableSeatList = Stream.of("1A", "1B", "1C", "1D", "1E", "1F").collect(Collectors.toList());
        Flight f1 = new Flight("Air Iceland Connect", "NY112", "Reykjavík", "Akureyri", 45, 10000, 5000, 2700, true, true, new Date(2018, 7, 1, 6, 15), new Date(2018, 7, 1, 7, 0), 500, 1000, availableSeatList );
        Flight f2 = new Flight("Air Iceland Connect", "NY326", "Reykjavík", "Egilstaðir", 60 , 15000, 7500, 2700, true, true, new Date(2018, 7, 1, 11, 15), new Date(2018, 7, 1, 12, 15), 500, 1000, availableSeatList );
        Flight f3 = new Flight("Air Iceland Connect", "NY016", "Reykjavík", "Ísafjörður", 55, 18000, 9000, 2700, true, true, new Date(2018, 7, 1, 15, 0), new Date(2018, 7, 1, 15, 55), 500, 1000, availableSeatList );
        flights.add(f1);
        flights.add(f2);
        flights.add(f3);
        return flights;
    }
   
}
