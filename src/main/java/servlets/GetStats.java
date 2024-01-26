/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.tables.EditBookingsTable;
import database.tables.EditPetKeepersTable;
import database.tables.EditReviewsTable;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
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
import mainClasses.PetKeeper;
import mainClasses.Review;

/**
 *
 * @author thead
 */
@WebServlet(name = "GetStats", urlPatterns = {"/GetStats"})
public class GetStats extends HttpServlet {

    // Helper method to calculate the total number of days a pet was kept
   private int calculateTotalDays(ArrayList<Booking> bookings) {
        if (bookings.isEmpty()) {
            return 0;
        }

        LocalDate minDate = parseDate(bookings.get(0).getFromDate());
        LocalDate maxDate = parseDate(bookings.get(0).getToDate());
       System.out.println("min date: " + minDate);
       System.out.println("max date: " + maxDate);
       if (minDate == null || maxDate == null) {
           return 0;
       }
        for (Booking booking : bookings) {
            LocalDate fromDate = parseDate(booking.getFromDate());
            LocalDate toDate = parseDate(booking.getToDate());

            if (fromDate != null && toDate != null) {
                if (fromDate.isBefore(minDate)) {
                    minDate = fromDate;
                }

                if (toDate.isAfter(maxDate)) {
                    maxDate = toDate;
                }
            }
       }
       System.out.println("min date: " + minDate);
       System.out.println("max date: " + maxDate);

        return (int) ChronoUnit.DAYS.between(minDate, maxDate) + 1;
    }

    private LocalDate parseDate(String dateString) {
        try {
            if (dateString != null && !dateString.isEmpty()) {
                return LocalDate.parse(dateString);
            } else {
                return null;
            }
        } catch (DateTimeParseException e) {

            return null;
        }
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            try {
                response.setStatus(200);
                EditPetKeepersTable petkeeper = new EditPetKeepersTable();
                PetKeeper pk = new PetKeeper();
                pk = petkeeper.databaseToPetKeepersUsername(session.getAttribute("loggedIn").toString());

                EditBookingsTable bookings = new EditBookingsTable();
                ArrayList<Booking> bk = bookings.getBookingsFinished(pk.getId());
                int numberOfBookings = bk.size();

                EditReviewsTable reviews = new EditReviewsTable();
                ArrayList<Review> r = reviews.databaseTokeeperReviews(pk.getId());
                int numberOfReviews = r.size();
                int totalDays;
                if (numberOfBookings == 0) {
                    totalDays = 0;
                } else {
                    totalDays = calculateTotalDays(bk);
                }


                JsonObject statsJson = new JsonObject();
                statsJson.addProperty("total_bookings", numberOfBookings);
                statsJson.addProperty("total_days", totalDays);
                statsJson.addProperty("total_reviews", numberOfReviews);

                Gson gson = new Gson();
                String json = gson.toJson(statsJson);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();  // Log the exception details
                throw new ServletException("Error processing request", ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.setStatus(403);
        }
    }
}
