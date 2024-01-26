/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.tables.EditPetOwnersTable;
import java.io.BufferedReader;
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
import mainClasses.PetOwner;

/**
 *
 * @author georgia_tsanta
 */
@WebServlet(name = "OnwersProfile", urlPatterns = {"/OnwersProfile"})
public class OnwersProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            try {
                response.setStatus(200);
                EditPetOwnersTable petkeeper = new EditPetOwnersTable();
                PetOwner pk = new PetOwner();
                pk = petkeeper.databaseToPetOwnersUsername(session.getAttribute("loggedIn").toString());
                Gson gson = new Gson();
                String json = gson.toJson(pk);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (SQLException ex) {
                Logger.getLogger(OnwersProfile.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();  // Log the exception details
                throw new ServletException("Error processing request", ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OnwersProfile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.setStatus(403);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            try {
                response.setStatus(200);
                Gson gson = new Gson();
                BufferedReader reader = request.getReader();
                PetOwner updatedPetOwner = gson.fromJson(reader, PetOwner.class);

                EditPetOwnersTable petownerTable = new EditPetOwnersTable();
                petownerTable.updatePetOwnerInfo(
                        session.getAttribute("loggedIn").toString(),
                        updatedPetOwner.getFirstname(),
                        updatedPetOwner.getLastname(),
                        updatedPetOwner.getBirthdate(),
                        updatedPetOwner.getGender(),
                        updatedPetOwner.getCountry(),
                        updatedPetOwner.getCity(),
                        updatedPetOwner.getAddress(),
                        updatedPetOwner.getPersonalpage(),
                        updatedPetOwner.getJob(),
                        updatedPetOwner.getTelephone()
                );


                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(gson.toJson(updatedPetOwner));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OnwersProfile.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500);
            } catch (SQLException ex) {
                Logger.getLogger(OnwersProfile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.setStatus(403);
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
