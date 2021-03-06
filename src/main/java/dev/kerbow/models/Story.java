package dev.kerbow.models;

import java.lang.reflect.Type;
import java.sql.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.kerbow.repositories.AuthorRepo;
import dev.kerbow.repositories.GenreRepo;
import dev.kerbow.repositories.StoryTypeRepo;

public class Story {
	
	private Integer id;
	private String title;
	private Genre genre;
	private StoryType type;
	private Author author;
	private String description;
	private String tagLine;
	private Date completionDate;
	private String completionStatus;
	private String approvalStatus;
	private String reason;
	private Date submissionDate;
	private Boolean modified;
	
	

	public Story() {}

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

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public StoryType getType() {
		return type;
	}

	public void setType(StoryType type) {
		this.type = type;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	

	public String getCompletionStatus() {
		return completionStatus;
	}

	public void setCompletionStatus(String completionStatus) {
		this.completionStatus = completionStatus;
	}
	
	public Boolean getModified() {
		return modified;
	}

	public void setModified(Boolean modified) {
		this.modified = modified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalStatus == null) ? 0 : approvalStatus.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((completionDate == null) ? 0 : completionDate.hashCode());
		result = prime * result + ((completionStatus == null) ? 0 : completionStatus.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modified == null) ? 0 : modified.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((submissionDate == null) ? 0 : submissionDate.hashCode());
		result = prime * result + ((tagLine == null) ? 0 : tagLine.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Story other = (Story) obj;
		if (approvalStatus == null) {
			if (other.approvalStatus != null)
				return false;
		} else if (!approvalStatus.equals(other.approvalStatus))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (completionDate == null) {
			if (other.completionDate != null)
				return false;
		} else if (!completionDate.equals(other.completionDate))
			return false;
		if (completionStatus == null) {
			if (other.completionStatus != null)
				return false;
		} else if (!completionStatus.equals(other.completionStatus))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modified == null) {
			if (other.modified != null)
				return false;
		} else if (!modified.equals(other.modified))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (submissionDate == null) {
			if (other.submissionDate != null)
				return false;
		} else if (!submissionDate.equals(other.submissionDate))
			return false;
		if (tagLine == null) {
			if (other.tagLine != null)
				return false;
		} else if (!tagLine.equals(other.tagLine))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Story [id=" + id + ", title=" + title + ", genre=" + genre + ", type=" + type + ", author=" + author
				+ ", description=" + description + ", tagLine=" + tagLine + ", completionDate=" + completionDate
				+ ", completionStatus=" + completionStatus + ", approvalStatus=" + approvalStatus + ", reason=" + reason
				+ ", submissionDate=" + submissionDate + ", modified=" + modified + "]";
	}
	
	public static class Deserializer implements JsonDeserializer<Story> {
		@Override
		public Story deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			Story story = new Story();
			JsonObject jo = json.getAsJsonObject();
			if (jo.has("author")) {
				// TODO: move this to AuthorServices
//				AuthorRepo ar = new AuthorRepo();
				story.setAuthor(context.deserialize(jo.get("author"), Author.class));
			}
			if (jo.has("approvalStatus")) {
				story.setApprovalStatus(context.deserialize(jo.get("approvalStatus"), String.class));
			}
			if (jo.has("reason")) {
				story.setApprovalStatus(context.deserialize(jo.get("reason"), String.class));
			}
			if (jo.has("id")) {
				story.setId(context.deserialize(jo.get("id"), Integer.class));
			}
			story.setTitle(context.deserialize(jo.get("title"), String.class));
			// TODO: move this to GenreServices!!!
			String genreName = context.deserialize(jo.get("genre"), String.class);
			story.setGenre(new GenreRepo().getByName(genreName));
			// TODO: move this to StoryTypeServices!!!;
			String storyType = context.deserialize(jo.get("storyType"), String.class);
			story.setType(new StoryTypeRepo().getByName(storyType));
			story.setDescription(context.deserialize(jo.get("description"), String.class));
			story.setTagLine(context.deserialize(jo.get("tagLine"), String.class));
			story.setCompletionDate(context.deserialize(jo.get("date"), Date.class));
			story.setCompletionStatus(context.deserialize(jo.get("completionStatus"), String.class));
			story.setSubmissionDate(context.deserialize(jo.get("submissionDate"), Date.class));
			story.setModified(context.deserialize(jo.get("modified"), Boolean.class));
			System.out.println("printing out story: " + story);
			System.out.println(jo);
			return story;
		}
	}
}