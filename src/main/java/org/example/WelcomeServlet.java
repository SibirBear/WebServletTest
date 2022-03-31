package org.example;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WelcomeServlet extends HttpServlet {

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.write("<html>"
                + "<head>"
                + "<title>Hello page</title>"
                + "</head>"
                + "<body>"
                + "<h1>Main page</h1>"
                + "<a href='/users/'>my link</a>"
                + "</body>"
                + "</html>");
    }

}
