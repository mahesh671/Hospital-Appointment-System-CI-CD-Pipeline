package spring.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "specializations")
public class Specialization {
	@Id
	@Column(name = "spec_id")
	private String id;

	@Column(name = "spec_title")
	private String title;

	@Column(name = "spec_desc")
	private String description;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	// Constructors, getters, and setters

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Specialization() {
	}

	public Specialization(String id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Specialization [id=" + id + ", title=" + title + ", description=" + description + "]";
	}
}
