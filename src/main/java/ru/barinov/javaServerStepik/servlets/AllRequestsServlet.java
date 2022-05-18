package ru.barinov.javaServerStepik.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.barinov.javaServerStepik.templater.PageGenerator;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AllRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { //Метод выполняет get запрос, возвращает мапу с параметрами get запроса
        Map<String, Object> pageVariables = createPageVariablesMap(request);
        pageVariables.put("message", "");

        response.getWriter().println(request.getParameter("key"));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);
        String message = request.getParameter("message");
        response.setContentType("text/html;charset=utf-8");

        if(message == null || message.isEmpty()){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("message", message == null ? "" : message);

        response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));

    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) { //Формируем ответ сервлета в виде мапы с набором параметров запроса
        Map<String, Object> pageVariables = new HashMap<String, Object>(); //Создаем мапу с ответом

        pageVariables.put("method", request.getMethod()); //Метод запроса get/post
        pageVariables.put("URL", request.getRequestURL().toString()); //URL of request
        pageVariables.put("pathInfo", request.getPathInfo()); //URL after http/localhost:8080
        pageVariables.put("sessionId", request.getSession().getId()); //Id of session
        pageVariables.put("parameters", request.getParameterMap().toString()); //Parameters of request

        return pageVariables;

    }
}
