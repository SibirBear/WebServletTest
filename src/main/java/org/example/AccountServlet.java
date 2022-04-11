package org.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User inputAccount = getAccountFromRequestBody(req);
        User account = getAccountById(inputAccount.getId());
        updateAccount(account, inputAccount);
    }

    private User getAccountFromRequestBody(HttpServletRequest req) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(req.getReader(), User.class);
    }

    private User getAccountById(Integer id) {
        return getUsers().stream()
                .filter(acc -> Objects.equals(id, acc.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No account existed with id = " + id));
    }

    private void updateAccount(User account, User inputAccount) {
        if (inputAccount.getLogin() != null) {
            account.setLogin(inputAccount.getLogin());
        }

        if (inputAccount.getBirthday() != null ) {
            account.setBirthday(inputAccount.getBirthday());
        }

        if ( inputAccount.getPassword() != null) {
            account.setPassword(inputAccount.getPassword());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer accountId = getAccountIdFromPath(req);
        if (accountId != null) {
            deleteAccountById(accountId);
        }
    }

    private Integer getAccountIdFromPath(HttpServletRequest req) {
        String path = req.getPathInfo();
        String[] pathVaribles = path.split("/");
        return Arrays.stream(pathVaribles)
                .filter(Objects::nonNull)
                .filter(v -> !Objects.equals(v, ""))
                .map(Integer::parseInt)
                .findFirst()
                .orElse(null);
    }

    private void deleteAccountById(Integer accountId) {
        User account = getAccountById(accountId);
        getUsers().remove(account);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        Integer id = getAccountIdFromPath(req);

        if (id != null) {
            User user = getAccountById(id);
            req.setAttribute("user", user);
            requestDispatcher = req.getRequestDispatcher("/account.jsp");
        } else {
            req.setAttribute("users", ListUsers.getUsers());
            requestDispatcher = req.getRequestDispatcher("/accounts.jsp");
        }
        requestDispatcher.forward(req,resp);


//        PrintWriter pw = resp.getWriter();
//
//        if (user != null) {
//            pw.write("<html>"
//                    + "<body>"
//                    + "<h1>You choose user with name " + user.getLogin() + "</h1>"
//                    + "</body>"
//                    + "</html>");
//        } else {
//            pw.write("<html>"
//                    + "<body>"
//                    + "<h1>No user found with id: " + id + "</h1>"
//                    + "</body>"
//                    + "</html>");
//        }

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
