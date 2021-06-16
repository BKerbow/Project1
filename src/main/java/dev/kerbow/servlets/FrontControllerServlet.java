package dev.kerbow.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontControllerServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uri = request.getRequestURI();
		System.out.println(uri);

		uri = uri.substring("/Project1".length());
		switch(uri) {
		case "/add":
			int i = Integer.parseInt(request.getParameter("num1"));
			int j = Integer.parseInt(request.getParameter("num2"));

			int k = i + j;

			response.setContentType("text/plain");

			response.getWriter().println("The sum is: " + k);
			break;
		case "/index.html":
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			System.out.println("u: " + username + ", p: " + password);
			break;
		case "/author_login":
			System.out.println("Received author_login!");
			break;
		default: break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doGet(request, response);
	}
}
