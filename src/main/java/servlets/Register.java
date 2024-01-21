/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.*;
import database.tables.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thead
 */
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Register</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        JSON_Converter jc = new JSON_Converter();
        String JSON = jc.getJSONFromAjax(request.getReader());
        boolean iskeeper = JSON.contains("pet_keeper");
        if (iskeeper) {
            System.out.println("keeper sent!");
        }
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if (iskeeper) {
                EditPetKeepersTable petkeeper = new EditPetKeepersTable();
                PetKeeper pk = petkeeper.jsonToPetKeeper(JSON);
                String JsonString = petkeeper.petKeeperToJSON(pk);

                if (petkeeper.databaseToPetKeepersUsername(pk.getUsername()) != null
                        || petkeeper.databaseToPetKeepersEmail(pk.getEmail()) != null) {
                    response.setStatus(409);
                    JsonObject jo = new JsonObject();
                    jo.addProperty("error", "Username or Email already taken");
                    response.getWriter().write(jo.toString());
                } else {
                    petkeeper.addPetKeeperFromJSON(JsonString);
                    response.setStatus(200);
                    response.getWriter().write(JsonString);
                }
            } else {
                EditPetOwnersTable petowner = new EditPetOwnersTable();
                PetOwner po = petowner.jsonToPetOwner(JSON);
                String JsonString = petowner.petOwnerToJSON(po);

                if (petowner.databaseToPetOwnersUsername(po.getUsername()) != null
                        || petowner.databaseToPetOwnersEmail(po.getEmail()) != null) {
                    response.setStatus(409);
                    JsonObject jo = new JsonObject();
                    jo.addProperty("error", "Username or Email already taken");
                    response.getWriter().write(jo.toString());
                } else {
                    petowner.addPetOwnerFromJSON(JsonString);
                    response.setStatus(200);
                    response.getWriter().write(JsonString);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
