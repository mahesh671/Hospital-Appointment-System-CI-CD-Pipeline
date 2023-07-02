package spring.orm.model.output;

public class DoctorList {
	private int doctId;
	private String doctName;
	
	
	public int getDoct_id() {
		return doctId;
	}


	public void setDoct_id(int doct_id) {
		this.doctId = doct_id;
	}


	public DoctorList() {//extra
		super();
	}


	public String getDocname() {
		return doctName;
	}


	public void setDocname(String docname) {
		this.doctName = docname;
	}


	public DoctorList(int doct_id, String docname) {
		super();
		this.doctId = doct_id;
		this.doctName = docname;
	}

}
