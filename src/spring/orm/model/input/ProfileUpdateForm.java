package spring.orm.model.input;

public class ProfileUpdateForm {

	private Integer appnId;

	private Integer patientId;

	private String parameter;

	private String patgroup;
	private String value;

	@Override
	public String toString() {
		return "ProfileUpdateForm [appnId=" + appnId + ", patientId=" + patientId + ", parameter=" + parameter
				+ ", patgroup=" + patgroup + ", value=" + value + "]";
	}

	public ProfileUpdateForm() {
		// Default constructor
	}

	public int getAppnid() {
		return appnId;
	}

	public void setAppnid(Integer appnId) {
		System.out.println("call");
		this.appnId = (appnId);
		System.out.println("call1");
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getPatgroup() {
		return patgroup;
	}

	public void setPatgroup(String patgroup) {
		this.patgroup = patgroup;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ProfileUpdateForm(Integer appnId, Integer patientId, String parameter, String patgroup, String value) {
		super();
		this.appnId = appnId;
		this.patientId = patientId;
		this.parameter = parameter;
		this.patgroup = patgroup;
		this.value = value;
	}

	// Getters and setters

}
