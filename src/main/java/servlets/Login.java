/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            HttpSession session = request.getSession(true);

            EditPetKeepersTable petkeeper = new EditPetKeepersTable();
            PetKeeper pk = new PetKeeper();

            if ((pk = petkeeper.databaseToPetKeepers(username, password)) != null) {
                session.setAttribute("loggedIn", username);
                response.setStatus(200);
                String json = petkeeper.petKeeperToJSON(pk);
                response.getWriter().write(json);
            } else {
                response.setStatus(403);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
                response.getWriter().write(pk.getUsername());
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.setStatus(403);
        }
    }
}
