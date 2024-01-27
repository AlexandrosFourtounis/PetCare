/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.tables.EditBookingsTable;
import database.tables.EditPetsTable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mainClasses.Booking;
import mainClasses.Pet;

/**
 *
 * @author thead
 */
@WebServlet(name = "GetDogs", urlPatterns = {"/GetDogs"})
public class GetDogs extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            try {
                response.setStatus(200);
                EditPetsTable tb = new EditPetsTable();
                ArrayList<Pet> dogs = tb.databaseToPetsDogs();
                ArrayList<Pet> cats = tb.databaseToPetsCats();
                int no_dogs = dogs.size();
                int no_cats = cats.size();

                EditBookingsTable b = new EditBookingsTable();
                ArrayList<Booking> bb = b.getAllBookingsFinished();
                int sumKeepers = 0;
                int sumApp = 0;

                for (Booking booking : bb) {
                    int bookingPrice = booking.getPrice();
                    int keeperEarnings = (int) (bookingPrice * 0.85);
                    int appEarnings = (int) (bookingPrice * 0.15);

                    sumKeepers += keeperEarnings;
                    sumApp += appEarnings;
                }

                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("no_dogs", no_dogs);
                jsonData.addProperty("no_cats", no_cats);
                jsonData.addProperty("sum_app", sumApp);
                jsonData.addProperty("sum_keepers", sumKeepers);

                Gson gson = new Gson();
                String json = gson.toJson(jsonData);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);

                throw new ServletException("Error processing request", ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.setStatus(403);
        }
    }
}
