/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import database.tables.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Pet;
import mainClasses.PetKeeper;

/**
 *
 * @author georgia_tsanta
 */
@WebServlet(name = "GetAvailablePetKeepers", urlPatterns = {"/GetAvailablePetKeepers"})
public class GetAvailablePetKeepers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            try {
                response.setStatus(200);
                EditPetKeepersTable petKeepersTable = new EditPetKeepersTable();

                // Fetch the pet type of the currently logged-in pet owner
                EditPetsTable petsTable = new EditPetsTable();
                String ownerId = session.getAttribute("loggedIn").toString();
                Pet petOfOwner = petsTable.petOfOwner(ownerId);
                String petType = petOfOwner.getType();

                // Retrieve available pet keepers based on pet type
                ArrayList<PetKeeper> availablePetKeepers = petKeepersTable.getAvailableKeepersforOwner(petType);

                Gson gson = new Gson();
                String json = gson.toJson(availablePetKeepers);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(GetAvailablePetKeepers.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500);
                response.getWriter().write("Error processing request");
            }
        } else {
            response.setStatus(403);
            response.getWriter().write("Forbidden");
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
