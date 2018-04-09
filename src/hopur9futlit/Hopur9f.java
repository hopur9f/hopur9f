/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9futlit;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sigrundish
 */
public class Hopur9f extends Application {

    /**
     * Connect to the PostgreSQL database
     * The connection here is not used, just nice to know 
     * if the connection is successful in the beginning.
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

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FlightsUI.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        getConnection();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
