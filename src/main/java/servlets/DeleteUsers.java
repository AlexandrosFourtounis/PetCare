/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditPetKeepersTable;
import database.tables.EditPetOwnersTable;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.PetKeeper;
import mainClasses.PetOwner;

/**
 *
 * @author thead
 */
@WebServlet(name = "DeleteUsers", urlPatterns = {"/DeleteUsers"})
public class DeleteUsers extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("userId");
        String type = request.getParameter("userType");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println("id == " + id + "  type == " + type);
        EditPetKeepersTable petkeeper = new EditPetKeepersTable();
        PetKeeper pk = new PetKeeper();
        EditPetOwnersTable petowner = new EditPetOwnersTable();
        PetOwner po = new PetOwner();

        if (type.equals("petkeeper")) {
            try {
                petkeeper.deletePetKeeper(parseInt(id));
            } catch (SQLException ex) {
                Logger.getLogger(DeleteUsers.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DeleteUsers.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                petowner.deletePetOwner(parseInt(id));
            } catch (SQLException ex) {
                Logger.getLogger(DeleteUsers.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DeleteUsers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        response.setStatus(200);
    }
}
