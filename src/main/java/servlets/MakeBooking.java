/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.JsonObject;
import database.tables.EditBookingsTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Booking;
import mainClasses.JSON_Converter;

/**
 *
 * @author georgia_tsanta
 */
@WebServlet(name = "MakeBooking", urlPatterns = {"/MakeBooking"})
public class MakeBooking extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSON_Converter jc = new JSON_Converter();
        String JSON = jc.getJSONFromAjax(request.getReader());

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            EditBookingsTable bookingsTable = new EditBookingsTable();
            Booking booking = bookingsTable.jsonToBooking(JSON);
            booking.setStatus("requested"); // Set default status

            String jsonString = bookingsTable.bookingToJSON(booking);

            // Perform validation or additional logic if needed
            bookingsTable.createNewBooking(booking);

            response.setStatus(200);
            response.getWriter().write(jsonString);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MakeBooking.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500); // Internal Server Error
            JsonObject jo = new JsonObject();
            jo.addProperty("error", "Internal Server Error");
            response.getWriter().write(jo.toString());
        }
    }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
