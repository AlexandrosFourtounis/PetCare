/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.tables.EditBookingsTable;
import database.tables.EditPetOwnersTable;
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
import mainClasses.PetOwner;

/**
 *
 * @author georgia_tsanta
 */
@WebServlet(name = "GetBookingsOwner", urlPatterns = {"/GetBookingsOwner"})
public class GetBookingsOwner extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            try {
                response.setStatus(200);
                EditPetOwnersTable petOwnerTable = new EditPetOwnersTable();
                PetOwner petOwner = petOwnerTable.databaseToPetOwnersUsername(session.getAttribute("loggedIn").toString());

                EditBookingsTable bookingsTable = new EditBookingsTable();
                ArrayList<Booking> bookings = bookingsTable.getBookings(petOwner.getOwner_id());

                Gson gson = new Gson();
                String json = gson.toJson(bookings);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(GetBookingsOwner.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();  // Log the exception details
                throw new ServletException("Error processing request", ex);
            }

        } else {
            response.setStatus(403);
        }
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");

        if (bookingId != null && !bookingId.isEmpty()) {
            try {
                int bookingID = Integer.parseInt(bookingId);
                // Update the booking status to "finished"
                EditBookingsTable bookingsTable = new EditBookingsTable();
                bookingsTable.updateBooking(bookingID, "finished");

                response.setStatus(200);
                response.getWriter().write("Booking ended successfully!");
            } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                Logger.getLogger(GetBookingsOwner.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500);
                response.getWriter().write("Error ending booking: " + ex.getMessage());
            }
        } else {
            response.setStatus(400);
            response.getWriter().write("Invalid parameters");
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
