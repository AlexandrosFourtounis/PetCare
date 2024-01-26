/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import mainClasses.PetKeeper;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditPetKeepersTable {

 
    public void addPetKeeperFromJSON(String json) throws ClassNotFoundException{
         PetKeeper user=jsonToPetKeeper(json);
         addNewPetKeeper(user);
    }
    
     public PetKeeper jsonToPetKeeper(String json){
         Gson gson = new Gson();

        PetKeeper user = gson.fromJson(json, PetKeeper.class);
        return user;
    }
    
    public String petKeeperToJSON(PetKeeper user){
         Gson gson = new Gson();

        String json = gson.toJson(user, PetKeeper.class);
        return json;
    }
    
   
    
    public void updatePetKeeper(String username,String personalpage) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE petkeepers SET personalpage='"+personalpage+"' WHERE username = '"+username+"'";
        stmt.executeUpdate(update);
    }


    public void updatePetKeeperInfo(String username, String firstname, String lastname, String birthdate, String gender,
            String country, String city, String address, String personalpage, String job, String telephone, String property,
            String propertydescription, String catkeeper, String dogkeeper, double catprice, double dogprice) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        String update = "UPDATE petkeepers SET firstname=?, lastname=?, birthdate=?, gender=?, country=?, city=?, address=?, personalpage=?, job=?, telephone=?, property=?, propertydescription=?, catkeeper=?, dogkeeper=?, catprice=?, dogprice=? WHERE username=?";
        try (PreparedStatement pstmt = con.prepareStatement(update)) {
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, birthdate);
            pstmt.setString(4, gender);
            pstmt.setString(5, country);
            pstmt.setString(6, city);
            pstmt.setString(7, address);
            pstmt.setString(8, personalpage);
            pstmt.setString(9, job);
            pstmt.setString(10, telephone);
            pstmt.setString(11, property);
            pstmt.setString(12, propertydescription);
            pstmt.setString(13, catkeeper);
            pstmt.setString(14, dogkeeper);
            pstmt.setDouble(15, catprice);
            pstmt.setDouble(16, dogprice);
            pstmt.setString(17, username);

            pstmt.executeUpdate();
        }
    }

    public void printPetKeeperDetailsToPage(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='" + password + "'");
            while (rs.next()) {

                System.out.println("===Result===");
                DB_Connection.printResults(rs);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public void printPetKeeperDetails(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='"+password+"'");
            while (rs.next()) {
                System.out.println("===Result===");
                DB_Connection.printResults(rs);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
    
    public PetKeeper databaseToPetKeepers(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper user = gson.fromJson(json, PetKeeper.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    

    
     public ArrayList<PetKeeper> getAvailableKeepers(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<PetKeeper> keepers = new ArrayList<PetKeeper>();
        ResultSet rs = null;
        try {
            //if(type=="catkeeper")
            if("all".equals(type))     
            rs = stmt.executeQuery("SELECT * FROM `petKeepers` WHERE  `petKeepers`.`keeper_id` not in (select keeper_id "
                 + "from `bookings` where `status`='requested' or  `status`='accepted')\n" +"");
            else if ("catKeepers".equals(type))
                 rs = stmt.executeQuery("SELECT * FROM `petKeepers` WHERE `petKeepers`.`catkeeper`='true' AND `petKeepers`.`keeper_id` not in (select keeper_id "
                 + "from `bookings` where `status`='requested' or  `status`='accepted')");         
             else if ("dogKeepers".equals(type))
                 rs = stmt.executeQuery("SELECT * FROM `petKeepers` WHERE `petKeepers`.`dogkeeper`='true' AND `petKeepers`.`keeper_id` not in (select keeper_id "
                 + "from `bookings` where `status`='requested' or  `status`='accepted')");
        
           
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
                keepers.add(keeper);
            }
            return keepers;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<PetKeeper> getAvailableKeepersforOwner(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<PetKeeper> keepers = new ArrayList<>();
        ResultSet rs = null;

        try {
            // Use prepared statement to avoid SQL injection
            String query = "SELECT * FROM `petKeepers` WHERE `keeper_id` NOT IN (SELECT `keeper_id` FROM `bookings` WHERE `status`='requested' OR `status`='accepted') AND ";

            if (type == null || "all".equals(type)) {
                query += "1"; // Include all keepers
            } else if ("cat".equals(type)) {
                query += "`catkeeper`='true'";
            } else if ("dog".equals(type)) {
                query += "`dogkeeper`='true'";
            } else {
                // Handle other types if needed
                throw new IllegalArgumentException("Invalid pet type: " + type);
            }

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
                keepers.add(keeper);
            }

            return keepers;
        } catch (Exception e) {
            e.printStackTrace();  // Add this line to print the stack trace
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            stmt.close();
            con.close();
        }

        return null;
    }


    
    
    public ArrayList<PetKeeper> getKeepers(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<PetKeeper> keepers = new ArrayList<PetKeeper>();
        ResultSet rs = null;
        try {
            if("catkeeper".equals(type))
                 rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE catkeeper= '" + "true" + "'");
            else if ("dogkeeper".equals(type))
                  rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE dogkeeper= '" + "true" + "'");

           
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
                keepers.add(keeper);
            }
            return keepers;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    
    public String databasePetKeeperToJSON(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }


     public void createPetKeepersTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE petkeepers "
                + "(keeper_id INTEGER not NULL AUTO_INCREMENT, "
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
                + "    property VARCHAR(10) not null,"
                + "    propertydescription VARCHAR(200),"
                + "    catkeeper VARCHAR(10) not null,"
                + "    dogkeeper VARCHAR(10) not null,"
                + "    catprice INTEGER,"
                + "    dogprice INTEGER,"
                + " PRIMARY KEY (keeper_id))";
        stmt.execute(query);
        stmt.close();
    }
    public PetKeeper databaseToPetKeepersUsername(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper user = gson.fromJson(json, PetKeeper.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public PetKeeper databaseToPetKeepersEmail(String email) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE email = '" + email + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper user = gson.fromJson(json, PetKeeper.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void addNewPetKeeper(PetKeeper user) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " petkeepers (username,email,password,firstname,lastname,birthdate,gender,country,city,address,personalpage,"
                    + "job,telephone,lat,lon,property,propertydescription,catkeeper,dogkeeper,catprice,dogprice)"
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
                    + "'" + 0 + "',"
                    + "'" + user.getProperty() + "',"
                    + "'" + user.getPropertydescription()+ "',"
                    + "'" + user.getCatkeeper() + "',"
                    + "'" + user.getDogkeeper() + "',"
                    + "'" + user.getCatprice() + "',"
                    + "'" + user.getDogprice() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The pet owner was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditPetKeepersTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

}
