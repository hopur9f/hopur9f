/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9fvinnsla;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This class is Service class for Flights. 
 * Sends queries to the table Flights in the database.
 * @author Hópur 9F
 */
public class FlightService {

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     * @throws java.net.URISyntaxException
     * @throws java.sql.SQLException
     */
    private static Connection getConnection() throws URISyntaxException, SQLException {
        Connection conn = null;
        try {
            URI dbUri = new URI("postgres://uyefpskpjkdwlw:f0e1254d03ae81c0ce719183f5985f211eefa45ed363bc013d58e6a221ac4b7a@ec2-54-247-81-88.eu-west-1.compute.amazonaws.com:5432/d23q86p0hkbsqb");
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Connection successful");
            return conn;
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Gets flights from the flights table in the database.
     * @param origin Origin of the flight.
     * @param dest Destination of the flight.
     * @param date The departure of the flight.
     * @return 
     */
    public List<Flight> getFlights(String origin, String dest, LocalDate date) {
        List<Flight> flights = new ArrayList<Flight>();
        Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM flights "
                    + "WHERE origin "
                    + "LIKE '%" + origin + "%' "
                    + "AND destination "
                    + "LIKE '%" + dest + "%' "
                    + "AND departure = '" + date + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String airline = rs.getString("airline");
                String flightnumber = rs.getString("flightnumber");
                origin = rs.getString("origin");
                String destination = rs.getString("destination");
                int adultPrice = rs.getInt("adultprice");
                int childPrice = rs.getInt("childPrice");
                Date departure = rs.getDate("departure");
                Date arrival = rs.getDate("arrival");
                int handLuggagePrice = rs.getInt("handluggageprice");
                int luggagePrice = rs.getInt("luggagePrice");
                boolean disabilityAccess = rs.getBoolean("disabilityaccess");
                boolean animalTransfer = rs.getBoolean("animaltransfer");
                Array availableSeatListArray = rs.getArray("availableseatlist");
                //Set availableSeatList on correct form.
                String[] availableSeatListString = (String[]) availableSeatListArray.getArray();
                List<String> availableSeatList = new ArrayList<String>(Arrays.asList(availableSeatListString));
                //Create new flight.
                Flight flight = new Flight(airline, flightnumber, origin, destination, adultPrice, childPrice, departure, arrival, handLuggagePrice, luggagePrice, disabilityAccess, animalTransfer);
                flight.setAvailableSeatList(availableSeatList);
                //Add the flight into the flight list.
                flights.add(flight);
            }
            rs.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return flights;
    }

}
