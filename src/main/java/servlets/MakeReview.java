/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditReviewsTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.JSON_Converter;
import mainClasses.Review;

/**
 *
 * @author georgia_tsanta
 */
@WebServlet(name = "MakeReview", urlPatterns = {"/MakeReview"})
public class MakeReview extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSON_Converter jc = new JSON_Converter();
        String JSON = jc.getJSONFromAjax(request.getReader());

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            EditReviewsTable reviewsTable = new EditReviewsTable();
            Review review = reviewsTable.jsonToReview(JSON);
            /*
            // Check if the booking status is finished
            if (!isBookingFinished(review.getBookingStatus())) {
                response.setStatus(400); // Bad Request
                JsonObject jo = new JsonObject();
                jo.addProperty("error", "Booking status must be finished to write a review.");
                response.getWriter().write(jo.toString());
                return;
            }*/

            reviewsTable.addReviewFromJSON(JSON);

            response.setStatus(200);
            response.getWriter().write(JSON);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MakeReview.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*private boolean isBookingFinished(String bookingStatus) {
        return "finished".equalsIgnoreCase(bookingStatus);
    }*/

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
