package dev.kerbow.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="genre_editor_join", schema="Project1")
public class GEJoin {
	
	@Id
	@Column(name="id", insertable=false, updatable=false)
	private Integer id;
	private Genre genre;
	private Editor editor;
	private Boolean senior;
	private Boolean general;
	private Boolean assistant;
	
	public GEJoin() {}

	public GEJoin(Genre genre, Editor editor) {
		this.genre = genre;
		this.editor = editor;
		this.senior = false;
		this.general = false;
		this.assistant = false;
	}
	
	public GEJoin(Genre genre, Editor editor, boolean senior, boolean general, boolean assistant) {
		this.genre = genre;
		this.editor = editor;
		this.senior = senior;
		this.general = general;
		this.assistant = assistant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public Boolean getSenior() {
		return senior;
	}

	public void setSenior(Boolean senior) {
		this.senior = senior;
	}

	public Boolean getGeneral() {
		return general;
	}

	public void setGeneral(Boolean general) {
		this.general = general;
	}

	public Boolean getAssistant() {
		return assistant;
	}

	public void setAssistant(Boolean assistant) {
		this.assistant = assistant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assistant == null) ? 0 : assistant.hashCode());
		result = prime * result + ((editor == null) ? 0 : editor.hashCode());
		result = prime * result + ((general == null) ? 0 : general.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((senior == null) ? 0 : senior.hashCode());
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
		GEJoin other = (GEJoin) obj;
		if (assistant == null) {
			if (other.assistant != null)
				return false;
		} else if (!assistant.equals(other.assistant))
			return false;
		if (editor == null) {
			if (other.editor != null)
				return false;
		} else if (!editor.equals(other.editor))
			return false;
		if (general == null) {
			if (other.general != null)
				return false;
		} else if (!general.equals(other.general))
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
		if (senior == null) {
			if (other.senior != null)
				return false;
		} else if (!senior.equals(other.senior))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GEJoin [id=" + id + ", genre=" + genre + ", editor=" + editor + ", senior=" + senior + ", general="
				+ general + ", assistant=" + assistant + "]";
	}
	
	

}
