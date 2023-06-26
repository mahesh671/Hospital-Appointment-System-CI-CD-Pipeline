package spring.orm.model.input;

public class AdminFilter {
String from;
	
	String to;
	
	public AdminFilter() {
		
	}
	

	public AdminFilter(String from, String to) {
		super();
		this.from = from;
		this.to = to;
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

}
