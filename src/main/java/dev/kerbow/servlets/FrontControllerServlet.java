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
import dev.kerbow.models.GEJoin;
import dev.kerbow.models.Genre;
import dev.kerbow.models.Messages;
import dev.kerbow.models.Story;
import dev.kerbow.models.StoryType;

import dev.kerbow.repositories.AuthorRepo;
import dev.kerbow.repositories.EditorRepo;
import dev.kerbow.repositories.GEJoinRepo;
import dev.kerbow.repositories.GenreRepo;
import dev.kerbow.repositories.MessagesRepo;
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
//		gsonBuilder.registerTypeAdapter(Author.class, new Author.Deserializer());
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		this.gson = gsonBuilder.create();

		String uri = request.getRequestURI();
		System.out.println(uri);
		String json = "";

		response.setHeader("Access-Control-Allow-Origin", "*");		// Needed to avoid CORS violations
		response.setHeader("Content-Type", "application/json");		// Needed to enable json data to be passed between front and back end

		session = request.getSession();

		uri = uri.substring("/Project1/FrontController/".length());
		switch (uri) {
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
			json = this.gson.toJson(a) + "|" + this.gson.toJson(stories);
			response.getWriter().append(json);
			System.out.print(jsons[0] + " " + jsons[1]);
			break;
		}
		case "get_editor_messages":{
			System.out.println("grabbing editor messages");
			List<Messages> messages = new ArrayList<Messages>(new MessagesRepo().getAll().values());
			Author a = (Author) session.getAttribute("logged_in");
			String[] mjson = new String[] { this.gson.toJson(messages), this.gson.toJson(a) };
			json = gson.toJson(messages) + "|" + this.gson.toJson(a);
			response.getWriter().append(json);
			break;
		}
		case "get_author_messages":{
			System.out.println("grabbing author messages");
			List<Messages> messages = new ArrayList<Messages>(new MessagesRepo().getAll().values());
			Editor e = (Editor) session.getAttribute("logged_in");
			String[] amjson = new String[] { this.gson.toJson(messages), this.gson.toJson(e) };
			json = gson.toJson(messages) + "|" + this.gson.toJson(e);
			System.out.println(json);
			response.getWriter().append(json);
			break;
		}
		case "get_other_editor_messages":{
			System.out.println("grabbing editor messages");
			List<Messages> messages = new ArrayList<Messages>(new MessagesRepo().getAll().values());
			Editor e = (Editor) session.getAttribute("logged_in");
			String[] mjson = new String[] { this.gson.toJson(messages), this.gson.toJson(e)};
			json = gson.toJson(e) + "|" + this.gson.toJson(messages);
			response.getWriter().append(json);
			break;
		}
		case "get_proposals":{
			Object logged_in = session.getAttribute("logged_in");
			if(logged_in instanceof Author) {
				Author a = (Author) logged_in;
				System.out.println(a);
				List<Story> stories = new StoryRepo().getAllByAuthor(a.getId());
				json = "author|" + this.gson.toJson(stories);
				response.getWriter().append(json);
			} else if(logged_in instanceof Editor) {
				Editor e = (Editor) session.getAttribute("logged_in");
				System.out.println(e);
				Set<Genre> genres = Utils.getGenres(e);
				List<Story> stories = new ArrayList<Story>();
				
				for (Genre g : genres) {
					System.out.println(g);
					if(e.getSenior()) {
						stories.addAll(new StoryRepo().getAllByGenreAndStatus(g, "approved_editor"));
					} else if(e.getAssistant()) {
						stories.addAll(new StoryRepo().getAllByGenreAndStatus(g, "submitted"));
					} else {
						String status = "approved_assistant";
						if (g.getName().equals("fantasy")) {
							Genre scienceFiction = new GenreRepo().getByName("science-fiction");
							stories.addAll(new StoryRepo().getAllByGenreAndStatus(scienceFiction, status));
						} else if (g.getName().equals("science-fiction")) {
							Genre mystery = new GenreRepo().getByName("mystery");
							stories.addAll(new StoryRepo().getAllByGenreAndStatus(mystery, status));
						} else if (g.getName().equals("mystery")) {
							Genre article = new GenreRepo().getByName("article");
							stories.addAll(new StoryRepo().getAllByGenreAndStatus(article, status));
						} else if (g.getName().equals("article")) {
							Genre nonfiction = new GenreRepo().getByName("nonfiction");
							stories.addAll(new StoryRepo().getAllByGenreAndStatus(nonfiction, status));
						} else if (g.getName().equals("nonfiction")) {
							Genre fantasy = new GenreRepo().getByName("fantasy");
							stories.addAll(new StoryRepo().getAllByGenreAndStatus(fantasy, status));
						}
					}
				}
				String flag = "general|";
				if (e.getAssistant()) flag = "assistant|";
				else if (e.getSenior()) flag = "senior|";
				json = flag + this.gson.toJson(stories);
				
				response.getWriter().append(json);
			}
			break;
		}
		case "save_story_to_session": {
			JsonElement root = JsonParser.parseReader(request.getReader());
			session.setAttribute("story", root.getAsJsonObject());
			response.getWriter().append("saved");
			break;
		}
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
			response.getWriter().append("index.html");
			session.invalidate();
			break;
		}
		case "author_signup": {
			System.out.println("Received author sign up!");
			Author a = this.gson.fromJson(request.getReader(), Author.class);
			if (a != null) {
				a = new AuthorRepo().add(a);
				System.out.println("Created the new author " + a + " and logged in!");
				session.setAttribute("logged_in", a);
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
			break;
		}
		case "submit_story_form": {
			Story story = this.gson.fromJson(request.getReader(), Story.class);
			Author a = (Author) session.getAttribute("logged_in");
			if (a.getPoints() < story.getType().getPoints()) {
				story.setApprovalStatus("waiting");
			} else {
				story.setApprovalStatus("submitted");
				a.setPoints(a.getPoints() - story.getType().getPoints());
				new AuthorRepo().update(a);
			}
			story.setAuthor(a);
			story.setModified(false);
			story = new StoryRepo().add(story);
			System.out.println(story);
			break;
		}
		case "submit_draft_update":{
			System.out.println("I got the new version!");
			Story s = gson.fromJson(request.getReader(), Story.class);
			Author a = (Author) session.getAttribute("logged_in");
			System.out.println(s);
			s.setAuthor(a);
			new StoryRepo().update(s);
			System.out.println("Updated the old version with the new version!");
			response.getWriter().append("authors.html");
			break;
		}
		case "approve_story":{
			System.out.println("I got the story approval!");
			Story s = gson.fromJson(request.getReader(), Story.class);
			Editor e = (Editor) session.getAttribute("logged_in");
			System.out.println(s);
			new StoryRepo().update(s);
			System.out.println("Approved the Selected Story!");
			break;
		}
		default: System.out.println("Sorry, I didn't get that POST flag."); break;

		}
	}
}