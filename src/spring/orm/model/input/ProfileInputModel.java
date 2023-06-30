package spring.orm.model.input;

public class ProfileInputModel {

	private String patnName;

	private int patnAge;

	private String patnGender;

	private String patnBgroup;

	public ProfileInputModel() {

	}

	public ProfileInputModel(String patnName, int patnAge, String patnGender, String patnBgroup) {
		super();
		this.patnName = patnName;
		this.patnAge = patnAge;
		this.patnGender = patnGender;
		this.patnBgroup = patnBgroup;
	}

	public String getPatnName() {
		return patnName;
	}

	public void setPatnName(String patnName) {
		this.patnName = patnName;
	}

	public int getPatnAge() {
		return patnAge;
	}

	public void setPatnAge(int patnAge) {
		this.patnAge = patnAge;
	}

	public String getPatnGender() {
		return patnGender;
	}

	public void setPatnGender(String patnGender) {
		this.patnGender = patnGender;
	}

	public String getPatnBgroup() {
		return patnBgroup;
	}

	public void setPatnBgroup(String patnBgroup) {
		this.patnBgroup = patnBgroup;
	}

	@Override
	public String toString() {
		return "ProfileInputModel [patnName=" + patnName + ", patnAge=" + patnAge + ", patnGender=" + patnGender
				+ ", patnBgroup=" + patnBgroup + "]";
	}

}
