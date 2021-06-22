package dev.kerbow.models;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Messages {
	private Integer id;
	private String title;
	private Editor fromEditor;
	private Author fromAuthor;
	private String editorMessage;
	private String authorMessage;
	
	public Messages() {}
	
	public Messages(String title, Editor editor, Author author) {
		this.title = title;
		this.fromEditor = editor;
		this.fromAuthor = author;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Editor getFromEditor() {
		return fromEditor;
	}

	public void setFromEditor(Editor editor) {
		this.fromEditor = editor;
	}

	public Author getFromAuthor() {
		return fromAuthor;
	}

	public void setFromAuthor(Author fromAuthor) {
		this.fromAuthor = fromAuthor;
	}

	public String getEditorMessage() {
		return editorMessage;
	}

	public void setEditorMessage(String editorMessage) {
		this.editorMessage = editorMessage;
	}

	public String getAuthorMessage() {
		return authorMessage;
	}

	public void setAuthorMessage(String authorMessage) {
		this.authorMessage = authorMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorMessage == null) ? 0 : authorMessage.hashCode());
		result = prime * result + ((editorMessage == null) ? 0 : editorMessage.hashCode());
		result = prime * result + ((fromAuthor == null) ? 0 : fromAuthor.hashCode());
		result = prime * result + ((fromEditor == null) ? 0 : fromEditor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Messages other = (Messages) obj;
		if (authorMessage == null) {
			if (other.authorMessage != null)
				return false;
		} else if (!authorMessage.equals(other.authorMessage))
			return false;
		if (editorMessage == null) {
			if (other.editorMessage != null)
				return false;
		} else if (!editorMessage.equals(other.editorMessage))
			return false;
		if (fromAuthor == null) {
			if (other.fromAuthor != null)
				return false;
		} else if (!fromAuthor.equals(other.fromAuthor))
			return false;
		if (fromEditor == null) {
			if (other.fromEditor != null)
				return false;
		} else if (!fromEditor.equals(other.fromEditor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Messages [id=" + id + ", title=" + title + ", fromEditor=" + fromEditor + ", fromAuthor=" + fromAuthor
				+ ", editorMessage=" + editorMessage + ", authorMessage=" + authorMessage + "]";
	}
	
	public static class Deserializer implements JsonDeserializer<Messages>{

		@Override
		public Messages deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			System.out.println("Deserializing!!!");
			Messages m = new Messages();
			JsonObject jo = json.getAsJsonObject();
			m.setTitle(context.deserialize(jo.get("title"), String.class));
			m.setFromEditor(context.deserialize(jo.get("fromEditor"), String.class));
			m.setFromAuthor(context.deserialize(jo.get("fromAuthor"), String.class));
			m.setEditorMessage(context.deserialize(jo.get("editorMessage"), String.class));
			m.setAuthorMessage(context.deserialize(jo.get("authorMessage"), String.class));
			return m;
		}
	}
	
}
