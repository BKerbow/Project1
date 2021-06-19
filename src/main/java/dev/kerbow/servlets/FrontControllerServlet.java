package dev.kerbow.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import dev.kerbow.models.Author;
import dev.kerbow.models.Editor;
import dev.kerbow.models.Genre;
import dev.kerbow.models.Story;
import dev.kerbow.models.StoryType;

import dev.kerbow.repositories.AuthorRepo;
import dev.kerbow.repositories.EditorRepo;
import dev.kerbow.repositories.GenreRepo;
import dev.kerbow.repositories.StoryRepo;
import dev.kerbow.repositories.StoryTypeRepo;
import dev.kerbow.utils.Utils;

public class FrontControllerServlet extends HttpServlet {
	class LoginInfo {
		public String username;
		public String password;
	}

	public FrontControllerServlet() {
		Utils.loadEntries();
	}

	private Gson gson = new Gson();
	public static HttpSession session;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Story.class, new Story.Deserializer());
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		this.gson = gsonBuilder.create();

		String uri = request.getRequestURI();
		System.out.println(uri);	
		String json;

		response.setHeader("Access-Control-Allow-Origin","*");		// Needed to avoid CORS violations
		response.setHeader("Content-Type", "application/json");		// Needed to enable json data to be passed between front and back end

		session = request.getSession();

		uri = uri.substring("/Project1/FrontController/".length());

		switch (uri) {
			case "/editor_login": {
				System.out.println("I got the editor_login!");
				LoginInfo info = this.gson.fromJson(request.getReader(), LoginInfo.class);
				Editor e = new EditorRepo().getByUsernameAndPassword(info.username, info.password);
				if (e != null) {
					System.out.println("Editor " + e.getFirstName() + " has logged in!");
					session.setAttribute("logged_in", e);
					response.getWriter().append("editors.html");
				} else {
					System.out.println("Failed to login with credentials: username=" + info.username + " password=" + info.password);
				}
				break;
			}
			case "/author_login": {
				System.out.println("I got the author_login!");
				LoginInfo info = this.gson.fromJson(request.getReader(), LoginInfo.class);
				Author a = new AuthorRepo().getByUsernameAndPassword(info.username, info.password);
				if (a != null) {
					System.out.println("Author " + a.getFirstName() + " has logged in!");
					session.setAttribute("logged_in", a);
					response.getWriter().append("authors.html");
				} else {
					System.out.println("Failed to login with the provided credentials credentials: username=" + info.username + " password=" + info.password);
				}
				break;
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}