package dev.kerbow.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	

	//	class StoryInfo {
	//		public String title;
	//		public String genre;
	//		public String type;
	//		public String description;
	//		public String tagline;
	//		public Date date;
	//	}

	public FrontControllerServlet() {
		Utils.loadEntries();
	}

	private Gson gson = new Gson();
	public static HttpSession session;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Story.class, new Story.Deserializer());
		//		gsonBuilder.registerTypeAdapter(Author.class, new Author.Deserializer());
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		this.gson = gsonBuilder.create();

		String uri = request.getRequestURI();
		System.out.println(uri);
		String json;

		response.setHeader("Access-Control-Allow-Origin", "*");		// Needed to avoid CORS violations
		response.setHeader("Content-Type", "application/json");		// Needed to enable json data to be passed between front and back end

		session = request.getSession();

		uri = uri.substring("/Project1/FrontController/".length());
		switch (uri) {
		case "author_signup": {
			System.out.println("Received author sign up!");
			Author a = this.gson.fromJson(request.getReader(), Author.class);
			if (a != null) {
				a = new AuthorRepo().add(a);
				System.out.println("Created the new author " + a + " and logged in!");
				session.setAttribute("logged_in", a);
				// TODO: change this to "author_main.html" when it exits!!!
				response.getWriter().append("authors.html");
			} else {
				System.out.println("Failed to create new Author account!");
			}
			break;
		}
		case "editor_signup": {
			System.out.println("Received Editor Sign Up!");
			Editor e = this.gson.fromJson(request.getReader(), Editor.class);
			if(e != null) {
				e = new EditorRepo().add(e);
				System.out.println("Created the new editor " + e + "and logged in!");
				session.setAttribute("logged_in", e);
				response.getWriter().append("editors.html");
			} else {
				System.out.println("Failed to create new Editor account!");
			}
		}
		// TODO: can editor login and author login be combined into the same thing? would require that login info across the two tables be unique
		case "editor_login": {
			System.out.println("I got the editor login!");
			LoginInfo info = this.gson.fromJson(request.getReader(), LoginInfo.class);
			Editor e = new EditorRepo().getByUsernameAndPassword(info.username, info.password);
			if (e != null) {
				System.out.println("Editor " + e.getFirstName() + " has logged in!");
				session.setAttribute("logged_in", e);
				response.getWriter().append("editors.html");
			} else {
				System.out.println("Failed to login with provided credentials credentials: username=" + info.username + " password=" + info.password);
			}
			break;
		}
		case "author_login": {
			System.out.println("I got the author login!");
			LoginInfo info = this.gson.fromJson(request.getReader(), LoginInfo.class);
			Author a = new AuthorRepo().getByUsernameAndPassword(info.username, info.password);
			if (a == null) {
				System.out.println("An Author with those credentials was not found.");
			}
			if (a != null) {
				System.out.println("The Author " + a.getFirstName() + " has logged in!");
				session.setAttribute("logged_in", a);
				response.getWriter().append("authors.html");
			} else {
				System.out.println("Failed to login with provided credentials credentials: username=" + info.username + " password=" + info.password);
			}
			break;
		}
		case "get_story_types": {
			List<StoryType> types = new ArrayList<StoryType>(new StoryTypeRepo().getAll().values());
			List<Genre> genres = new ArrayList<Genre>(new GenreRepo().getAll().values());
			Author a = (Author) session.getAttribute("logged_in");
			String[] jsons = new String[] { this.gson.toJson(types), this.gson.toJson(genres), this.gson.toJson(a) };
			json = gson.toJson(jsons);
			response.getWriter().append(json);
			break;
		}
		case "get_author_stories":{
			System.out.println("grabbing author stories");
			List<Story> stories = new ArrayList<Story>(new StoryRepo().getAll().values());
			Author a = (Author) session.getAttribute("logged_in");
			String[] jsons = new String[] { this.gson.toJson(stories), this.gson.toJson(a)};
			json = gson.toJson(jsons);
			response.getWriter().append(json);
			System.out.print(jsons[0] + " " + jsons[1]);
			break;
		}
//		case "get_editor's_messages":{
//			System.out.println("grabbing editor messages");
//			List<Messages> editorMessage = new ArrayList<Messages>(new MessageRepo().getAll().values());
//		}
		default: System.out.println("Sorry, I didn't catch that GET flag."); break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Story.class, new Story.Deserializer());
		//		gsonBuilder.registerTypeAdapter(Author.class, new Author.Deserializer());
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		this.gson = gsonBuilder.create();

		String uri = request.getRequestURI();
		System.out.println(uri);
		String json;

		response.setHeader("Access-Control-Allow-Origin", "*");		// Needed to avoid CORS violations
		response.setHeader("Content-Type", "application/json");		// Needed to enable json data to be passed between front and back end

		session = request.getSession();

		uri = uri.substring("/Project1/FrontController/".length());
		switch (uri) {
		case "author_login": {
			System.out.println("I got the author login!");
			LoginInfo info = this.gson.fromJson(request.getReader(), LoginInfo.class);
			Author a = new AuthorRepo().getByUsernameAndPassword(info.username, info.password);
			if (a != null) {
				System.out.println("The Author " + a.getFirstName() + " has logged in!");
				session.setAttribute("logged_in", a);
				response.getWriter().append("authors.html");
				System.out.println("Author Logged In.");
			} else {
				System.out.println("Failed to login with provided credentials credentials: username=" + info.username + " password=" + info.password);
			}
			break;
		}
		case "editor_login": {
			System.out.println("I got the Editor login!");
			LoginInfo info = this.gson.fromJson(request.getReader(), LoginInfo.class);
			Editor e = new EditorRepo().getByUsernameAndPassword(info.username, info.password);
			if (e != null) {
				System.out.println("The Editor " + e.getFirstName() + " has logged in!");
				session.setAttribute("logged_in", e);
				response.getWriter().append("editors.html");
				System.out.println("Editor Logged In.");
			} else {
				System.out.println("Failed to login with provided credentials credentials: username=" + info.username + " password=" + info.password);
			}
			break;
		}
		case "logout": {
			String pageURL = "";
			Object loggedIn	= session.getAttribute("logged_in");
			if(loggedIn instanceof Author) pageURL = "index.html";
			if(loggedIn instanceof Editor) pageURL = "index.html";
			System.out.println("Logging you out!");
			response.getWriter().append(pageURL);
			session.invalidate();
			break;
		}
		case "story_submit":
		default: System.out.println("Sorry, I didn't get that POST flag."); break;

		}
	}
}