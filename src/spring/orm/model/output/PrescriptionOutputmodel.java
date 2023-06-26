package spring.orm.model.output;

import java.util.Base64;
import java.util.Date;

public class PrescriptionOutputmodel {

	// PatientMedicalProfile
	private String patn_parameter;

	private String patn_pargroup;

	private String patn_value;

	private String patn_prescription;

	// DiagnosticBillModel
	private int appn_id;

	private Date appn_sch_date;

	public int getAppn_id() {
		return appn_id;
	}

	public void setAppn_id(int appn_id) {
		this.appn_id = appn_id;
	}

	public Date getAppn_sch_date() {
		return appn_sch_date;
	}

	public void setAppn_sch_date(Date appn_sch_date) {
		this.appn_sch_date = appn_sch_date;
	}

	public void setPatn_prescription(String patn_prescription) {
		this.patn_prescription = patn_prescription;
	}

	@Override
	public String toString() {
		return "PrescriptionOutputmodel [patn_parameter=" + patn_parameter + ", patn_pargroup=" + patn_pargroup
				+ ", patn_value=" + patn_value + ", patn_prescription=" + patn_prescription + ", appn_id=" + appn_id
				+ ", appn_sch_date=" + appn_sch_date + "]";
	}

	public String getPatn_parameter() {
		return patn_parameter;
	}

	public void setPatn_parameter(String patn_parameter) {
		this.patn_parameter = patn_parameter;
	}

	public String getPatn_pargroup() {
		return patn_pargroup;
	}

	public void setPatn_pargroup(String patn_pargroup) {
		this.patn_pargroup = patn_pargroup;
	}

	public String getPatn_value() {
		return patn_value;
	}

	public void setPatn_value(String patn_value) {
		this.patn_value = patn_value;
	}

	public String getPatn_prescription() {
		return patn_prescription;
	}

	public void setPatn_prescription(byte[] patn_prescription) {
		this.patn_prescription = Base64.getEncoder().encodeToString(patn_prescription);
	}

	public PrescriptionOutputmodel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PrescriptionOutputmodel(int appn_id, Date appn_sch_date, String patn_parameter, String patn_pargroup,
			String patn_value, byte[] patn_prescription) {
		super();
		this.patn_parameter = patn_parameter;
		this.patn_pargroup = patn_pargroup;
		this.patn_value = patn_value;
		this.patn_prescription = Base64.getEncoder().encodeToString(patn_prescription);
		this.appn_id = appn_id;
		this.appn_sch_date = appn_sch_date;
	}

}
