package spring.orm.model.input;

public class BookedAppForm {

	private String from;

	private String to;

	private String spec;

	private String status;

	private String doctor;

	public BookedAppForm() {

	}

	public BookedAppForm(String from, String to, String spec, String status, String doctor) {
		super();
		this.from = from;
		this.to = to;
		this.spec = spec;
		this.status = status;
		this.doctor = doctor;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	@Override
	public String toString() {
		return "BookedAppForm [from=" + from + ", to=" + to + ", spec=" + spec + ", status=" + status + ", doctor="
				+ doctor + "]";
	}

}