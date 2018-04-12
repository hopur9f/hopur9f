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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This class is Service class for Passengers. Sends queries to the table
 * Passengers in the database.
 *
 * @author Hópur 9F
 */
public class BookingService {

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

    public long insertBooking(Booking booking, Flight flight, int[] passengersIdInt, int numberAdults, int numberChildren) {

        String SQL = "INSERT INTO bookings(flightNumber, flightid, numberAdults,numberChildren, passengers, cardHolder, cardNumber, expirationDate, csv) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";

        long id = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, flight.getFlightNumber());
            pstmt.setInt(2, flight.getId());
            pstmt.setInt(3, numberAdults);
            pstmt.setInt(4, numberChildren);
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < passengersIdInt.length; i++) {
                list.add(passengersIdInt[i]); 
            }
            Array array = conn.createArrayOf("int", list.toArray());
            pstmt.setArray(5, array);
            pstmt.setString(6, booking.getCardHolder());
            pstmt.setString(7, booking.getCardNumber());
            pstmt.setDate(8, java.sql.Date.valueOf(booking.getexpDay()));
            pstmt.setString(9, String.valueOf(booking.getCsv()));

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (pstmt != null) {
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
        System.out.println("Booking successfully added to database!");
        return id;
    }

}
