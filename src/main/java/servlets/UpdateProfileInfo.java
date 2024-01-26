/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.tables.EditPetKeepersTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mainClasses.JSON_Converter;
import mainClasses.PetKeeper;

/**
 *
 * @author thead
 */
@WebServlet(name = "UpdateProfileInfo", urlPatterns = {"/UpdateProfileInfo"})
public class UpdateProfileInfo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        JSON_Converter jc = new JSON_Converter();
        String JSON = jc.getJSONFromAjax(request.getReader());

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (session.getAttribute("loggedIn") != null) {
            try {
                EditPetKeepersTable petkeeperTable = new EditPetKeepersTable();
                PetKeeper updatedPetKeeper = petkeeperTable.jsonToPetKeeper(JSON);

                String JsonString = petkeeperTable.petKeeperToJSON(updatedPetKeeper);
                System.out.println("pk from ajax is : " + JsonString + updatedPetKeeper.getFirstname() + session.getAttribute("loggedIn").toString());
                response.setStatus(200);

                petkeeperTable.updatePetKeeperInfo(
                        session.getAttribute("loggedIn").toString(),
                        updatedPetKeeper.getFirstname(),
                        updatedPetKeeper.getLastname(),
                        updatedPetKeeper.getBirthdate(),
                        updatedPetKeeper.getGender(),
                        updatedPetKeeper.getCountry(),
                        updatedPetKeeper.getCity(),
                        updatedPetKeeper.getAddress(),
                        updatedPetKeeper.getPersonalPage(),
                        updatedPetKeeper.getJob(),
                        updatedPetKeeper.getTelephone(),
                        updatedPetKeeper.getProperty(),
                        updatedPetKeeper.getPropertydescription(),
                        updatedPetKeeper.getCatkeeper(),
                        updatedPetKeeper.getDogkeeper(),
                        updatedPetKeeper.getCatprice(),
                        updatedPetKeeper.getDogprice()
                );
                Gson gson = new Gson();

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(gson.toJson(updatedPetKeeper));
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500); // Internal Server Error
            }
        } else {
            response.setStatus(403); // Forbidden
        }
    }
}
