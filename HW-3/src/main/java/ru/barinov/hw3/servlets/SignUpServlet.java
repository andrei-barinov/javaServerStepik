package ru.barinov.hw2.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.barinov.hw2.accounts.AccountService;
import ru.barinov.hw2.accounts.UserProfile;

import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserProfile profile = new UserProfile(login, password);
        accountService.addNewUser(profile);
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
