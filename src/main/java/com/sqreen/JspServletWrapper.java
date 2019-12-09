package com.sqreen;

import org.apache.jasper.servlet.JspServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JspServletWrapper extends JspServlet {

    private static final String AGENT_NAME = "Sqreen";

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
        response.addHeader("X-Instrumented-By", AGENT_NAME);
    }
}
