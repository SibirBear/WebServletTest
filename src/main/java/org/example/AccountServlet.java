package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.example.MainServlet.*;

import static org.example.Counter.getId;
import static org.example.ListUsers.addUser;
import static org.example.ListUsers.getUsers;

public class AccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String bd = req.getParameter("date");

        User user = new User(getId(), login, password, getDate(bd));
        addUser(user);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        User user = getUsers().stream()
                .filter(u -> u.getId() == Integer.parseInt(id))
                .findAny()
                .orElse(null);

        PrintWriter pw = resp.getWriter();

        if (user != null) {
            pw.write("<html>"
                    + "<body>"
                    + "<h1>You choose user with name " + user.getLogin() + "</h1>"
                    + "</body>"
                    + "</html>");
        } else {
            pw.write("<html>"
                    + "<body>"
                    + "<h1>No user found with id: " + id + "</h1>"
                    + "</body>"
                    + "</html>");
        }

    }

    private Date getDate(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

}
