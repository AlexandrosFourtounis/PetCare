/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.tables.EditPetKeepersTable;
import database.tables.EditReviewsTable;
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
import mainClasses.PetKeeper;
import mainClasses.Review;

/**
 *
 * @author thead
 */
@WebServlet(name = "GetReviews", urlPatterns = {"/GetReviews"})
public class GetReviews extends HttpServlet {

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

                EditReviewsTable reviews = new EditReviewsTable();
                ArrayList<Review> r = reviews.databaseTokeeperReviews(pk.getId());

                Gson gson = new Gson();
                String json = gson.toJson(r);
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
