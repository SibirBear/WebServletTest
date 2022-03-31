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
import java.util.List;

import static org.example.Counter.getId;
import static org.example.ListUsers.getUsers;
import static org.example.ListUsers.addUser;

public class MainServlet extends HttpServlet {
    //private List<User> users;
    //private int idCounter;

    public MainServlet() {
        //this.users = users;
        //this.idCounter = idCounter;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        //this.users = new ArrayList<>();
        //this.idCounter = 0;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        List<User> listUser = getUsers();
        for (User user : listUser) {
            pw.write(user.toString());
        }
        pw.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User();
        user.setLogin(req.getParameter("name"));
        user.setId(getId());
        String date = req.getParameter("date");
        user.setBirthday(getDate(date));

        addUser(user);

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

    /*public Integer getId() {
        return ++this.idCounter;
    }*/

}
