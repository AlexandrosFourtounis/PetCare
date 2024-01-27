/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.tables.EditBookingsTable;
import java.io.BufferedReader;
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

/**
 *
 * @author thead
 */
@WebServlet(name = "DeclineBooking", urlPatterns = {"/DeclineBooking"})
public class DeclineBooking extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String json = stringBuilder.toString();
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        String id = jsonObject.get("booking_id").getAsString();

        System.out.println("booking id " + id);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println("id == " + id);

        EditBookingsTable b = new EditBookingsTable();

        try {
            b.updateBooking(parseInt(id), "declined");
        } catch (SQLException ex) {
            Logger.getLogger(AcceptBooking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AcceptBooking.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.setStatus(200);
    }
}
