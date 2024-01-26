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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.PetKeeper;

/**
 *
 * @author georgia_tsanta
 */
@WebServlet(name = "KeepersList", urlPatterns = {"/KeepersList"})
public class KeepersList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            EditPetKeepersTable edt = new EditPetKeepersTable();
            ArrayList<PetKeeper> keepers = edt.getKeepersUser("all");

            Gson gson = new Gson();
            String json = gson.toJson(keepers);

            response.getWriter().write(json);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(KeepersList.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("Error retrieving all pet keepers");
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
