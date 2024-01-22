/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thead
 */
@WebServlet(name = "HandleGPT", urlPatterns = {"/HandleGPT"})
public class HandleGPT extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonContent.toString(), JsonObject.class);

        String query = jsonObject.get("query").getAsString();

        response.getWriter().write(chatGPT(query));
    }
    private String chatGPT(String prompt) {

        return ChatGPTAPI.chatGPT(prompt);
    }
}
