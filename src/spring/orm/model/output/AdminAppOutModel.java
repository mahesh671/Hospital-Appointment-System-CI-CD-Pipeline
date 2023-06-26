package spring.orm.model.output;

import java.time.ZoneId;
import java.util.Date;

public class AdminAppOutModel {

	private String docname;
	private String patientname;
	private String bookedDate;
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	public String getPatientname() {
		return patientname;
	}
	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}
	public String getBookedDate() {
		return bookedDate;
	}
	public void setBookedDate(String bookedDate) {
		this.bookedDate = bookedDate;
	}
	public AdminAppOutModel(String docname, String patientname, Date bookedDate) {
		super();
		this.docname = docname;
		this.patientname = patientname;
		this.bookedDate = bookedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
	}
	public AdminAppOutModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
