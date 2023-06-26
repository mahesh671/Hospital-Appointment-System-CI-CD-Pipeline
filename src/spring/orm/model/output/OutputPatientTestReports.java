package spring.orm.model.output;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class OutputPatientTestReports {

	private int dgbl_id;

	private LocalDate dgbl_date;

	private String content;

	public OutputPatientTestReports() {

	}

	public OutputPatientTestReports(int dgbl_id, LocalDate dgbl_date, byte[] content) {
		super();
		this.dgbl_id = dgbl_id;
		this.dgbl_date = dgbl_date;
		this.content = Base64.getEncoder().encodeToString(content);
	}

	public int getDgbl_id() {
		return dgbl_id;
	}

	public void setDgbl_id(int dgbl_id) {
		this.dgbl_id = dgbl_id;
	}

	public String getDgbl_date() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = dgbl_date.format(formatter);

		return formattedDate;
	}

	public void setDgbl_date(LocalDate dgbl_date) {
		this.dgbl_date = dgbl_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = Base64.getEncoder().encodeToString(content);

	}

	@Override
	public String toString() {
		return "OutputPatientTestReports [dgbl_id=" + dgbl_id + ", dgbl_date=" + dgbl_date + ", content=" + content
				+ "]";
	}

}