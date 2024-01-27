/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.tables.EditPetKeepersTable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mainClasses.PetKeeper;

/**
 *
 * @author thead
 */
@WebServlet(name = "GetMSGS", urlPatterns = {"/GetMSGS"})
public class GetMSGS extends HttpServlet {

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

//                // Get booking_ids parameter from the request
//                String[] bookingIdsArray = request.getParameterValues("booking_ids");
//
//                if (bookingIdsArray != null && bookingIdsArray.length > 0) {
                    // Convert booking IDs to integers
//                    List<Integer> bookingIds = Arrays.stream(bookingIdsArray)
//                            .map(Integer::parseInt)
//                            .collect(Collectors.toList());

//                    EditMessagesTable mm = new EditMessagesTable();
//                    ArrayList<Message> messages = mm.getMessagesForBookings(pk.getId(), bookingIds);

                    Gson gson = new Gson();
//                    String json = gson.toJson(messages);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
//                    response.getWriter().write(json);
//                }
//                else {
                    // Handle the case when no booking_ids are provided
                    response.setStatus(400); // Bad Request
                //}
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();  // Log the exception details
                throw new ServletException("Error processing request", ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.setStatus(403); // Forbidden
        }
    }

}
