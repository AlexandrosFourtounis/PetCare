/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import mainClasses.PetOwner;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditPetOwnersTable {

 
    public void addPetOwnerFromJSON(String json) throws ClassNotFoundException{
         PetOwner user=jsonToPetOwner(json);
         addNewPetOwner(user);
    }
    
     public PetOwner jsonToPetOwner(String json){
         Gson gson = new Gson();

        PetOwner user = gson.fromJson(json, PetOwner.class);
        return user;
    }
    
    public String petOwnerToJSON(PetOwner user){
         Gson gson = new Gson();

        String json = gson.toJson(user, PetOwner.class);
        return json;
    }
    
   
    
    public void updatePetOwner(String username,String personalpage) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE petowners SET personalpage='"+personalpage+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);
    }
   
    
    public PetOwner databaseToPetOwners(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetOwner user = gson.fromJson(json, PetOwner.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public String databasePetOwnerToJSON(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    public PetOwner databaseToPetOwnersUsername(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners WHERE username = '" + username + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetOwner user = gson.fromJson(json, PetOwner.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public PetOwner databaseToPetOwnersEmail(String email) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petowners WHERE email = '" + email + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetOwner user = gson.fromJson(json, PetOwner.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

     public void createPetOwnersTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE petowners "
                + "(owner_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    email VARCHAR(50) not null unique,	"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(30) not null,"
                + "    lastname VARCHAR(30) not null,"
                + "    birthdate DATE not null,"
                + "    gender  VARCHAR (7) not null,"
                + "    country VARCHAR(30) not null,"
                + "    city VARCHAR(50) not null,"
                + "    address VARCHAR(50) not null,"
                + "    personalpage VARCHAR(200) not null,"
                + "    job VARCHAR(200) not null,"
                + "    telephone VARCHAR(14),"
                  + "    lat DOUBLE,"
                + "    lon DOUBLE,"
                + " PRIMARY KEY (owner_id))";
        stmt.execute(query);
        stmt.close();
    }
    
    
    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void addNewPetOwner(PetOwner user) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " petowners (username,email,password,firstname,lastname,birthdate,gender,country,city,address,personalpage,"
                    + "job,telephone,lat,lon)"
                    + " VALUES ("
                    + "'" + user.getUsername() + "',"
                    + "'" + user.getEmail() + "',"
                    + "'" + user.getPassword() + "',"
                    + "'" + user.getFirstname() + "',"
                    + "'" + user.getLastname() + "',"
                    + "'" + user.getBirthdate() + "',"
                    + "'" + user.getGender() + "',"
                    + "'" + user.getCountry() + "',"
                    + "'" + user.getCity() + "',"
                    + "'" + user.getAddress() + "',"
                    + "'" + user.getPersonalpage() + "',"
                    + "'" + user.getJob() + "',"
                    + "'" + user.getTelephone() + "',"
                    + "'" + 0 + "',"
                    + "'" + 0 + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The pet owner was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditPetOwnersTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

}
