package spring.orm.model.entity;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import spring.orm.model.TestBookStatusComposite;

@Entity
@Table(name = "testBookStatus")
public class TestBookStatus {
	@EmbeddedId
	private TestBookStatusComposite id;

	@Column(name = "booked_date")
	private Date bookedDate;

	@Column(name = "status")
	private String status;
	@Column(name = "type")
	private String type;

	public TestBookStatus(TestBookStatusComposite id, Date bookedDate, String status, String type) {

		this.id = id;
		this.bookedDate = bookedDate;
		this.status = status;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TestBookStatus() {
		super();
	}

	// Getters and setters
	public TestBookStatusComposite getId() {
		return id;
	}

	public void setId(TestBookStatusComposite id) {
		this.id = id;
	}

	public Date getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(LocalDate currentDate) {
		this.bookedDate = Date.valueOf(currentDate);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
