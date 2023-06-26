package spring.orm.model.output;

public class PatientNameOutputModel {
	String patn_name;
	int patn_id;

	public String getPatn_name() {
		return patn_name;
	}

	public void setPatn_name(String patn_name) {
		this.patn_name = patn_name;
	}

	public int getPatn_id() {
		return patn_id;
	}

	public void setPatn_id(int patn_id) {
		this.patn_id = patn_id;
	}

	public PatientNameOutputModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PatientNameOutputModel(int patn_id, String patn_name) {

		this.patn_name = patn_name;
		this.patn_id = patn_id;

	}

	@Override
	public String toString() {
		return "PatientNameOutputModel [patn_name=" + patn_name + ", patn_id=" + patn_id + "]";
	}
}