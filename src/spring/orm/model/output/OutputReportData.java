package spring.orm.model.output;

public class OutputReportData {

	private int patn_id;

	private int dgbl_id;

	private String patn_name;

	private String patn_gender;

	private int dgbl_amount;

	public OutputReportData() {

	}

	public OutputReportData(int patn_id, int dgbl_id, String patn_name, String patn_gender, int dgbl_amount) {
		super();
		this.patn_id = patn_id;
		this.dgbl_id = dgbl_id;
		this.patn_name = patn_name;
		this.patn_gender = patn_gender;
		this.dgbl_amount = dgbl_amount;
	}

	public int getPatn_id() {
		return patn_id;
	}

	public void setPatn_id(int patn_id) {
		this.patn_id = patn_id;
	}

	public int getDgbl_id() {
		return dgbl_id;
	}

	public void setDgbl_id(int dgbl_id) {
		this.dgbl_id = dgbl_id;
	}

	public String getPatn_name() {
		return patn_name;
	}

	public void setPatn_name(String patn_name) {
		this.patn_name = patn_name;
	}

	public String getPatn_gender() {
		return patn_gender;
	}

	public void setPatn_gender(String patn_gender) {
		this.patn_gender = patn_gender;
	}

	public long getDgbl_amount() {
		return dgbl_amount;
	}

	public void setDgbl_amount(int dgbl_amount) {
		this.dgbl_amount = dgbl_amount;
	}

	@Override
	public String toString() {
		return "OutputReportData [patn_id=" + patn_id + ", dgbl_id=" + dgbl_id + ", patn_name=" + patn_name
				+ ", patn_gender=" + patn_gender + ", dgbl_amount=" + dgbl_amount + "]";
	}

}